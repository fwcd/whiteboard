package fwcd.whiteboard.client.model.network;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.Collections;
import java.util.function.Consumer;

import fwcd.fructose.Observable;
import fwcd.fructose.Option;
import fwcd.sketch.model.SketchBoardModel;
import fwcd.sketch.model.event.BoardItemEventBus;
import fwcd.sketch.model.items.SketchItem;
import fwcd.whiteboard.client.model.convert.ToProtocolItemConverter;
import fwcd.whiteboard.client.model.overlay.BoardOverlayModel;
import fwcd.whiteboard.endpoint.ProtocolReceiver;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.request.AddItemsRequest;
import fwcd.whiteboard.protocol.request.DisconnectRequest;
import fwcd.whiteboard.protocol.request.HelloRequest;
import fwcd.whiteboard.protocol.struct.ClientInfo;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * A facility that establishes and holds an
 * active connection to a server once present.
 */
public class ServerConnectionManager {
	private final ClientNetworkContext context = new ClientNetworkContext();
	private final LocalWhiteboardClient client;
	private final Observable<Option<ServerConnection>> activeConnection = new Observable<>(Option.empty());
	
	public ServerConnectionManager(SketchBoardModel board, BoardOverlayModel overlay) {
		client = new LocalWhiteboardClient(board, overlay, context);
		registerListeners(board);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			ifConnected(wb -> wb.disconnect(new DisconnectRequest(context.getClientId())));
			activeConnection.get().ifPresent(ServerConnection::close);
		}));
	}
	
	private void registerListeners(SketchBoardModel board) {
		BoardItemEventBus eventBus = board.getItemEventBus();
		
		eventBus.getAddListeners().add(item -> {
			if (!context.isSilent()) {
				toProtocolItem(item.get()).ifPresent(protocolItem ->
					ifConnected(wb -> {
						wb.addItems(new AddItemsRequest(context.getClientId(), Collections.singletonList(protocolItem)));
					})
				);
			}
		});
	}
	
	public Option<WhiteboardItem> toProtocolItem(SketchItem item) {
		ToProtocolItemConverter converter = new ToProtocolItemConverter();
		item.accept(converter);
		return converter.getResult();
	}
	
	public void connect(String host, int port, String displayName) throws IOException {
		Socket socket = new Socket(host, port);
		ServerConnection connection = new ServerConnection(socket);
		connection.getServerProxy().hello(new HelloRequest(context.getClientId(), new ClientInfo(context.getClientId(), displayName)));
		activeConnection.set(Option.of(connection));
		
		ProtocolReceiver receiver = ProtocolReceiver.ofClient(socket.getInputStream(), client);
		receiver.setOnClose(() -> activeConnection.set(Option.empty()));
		new Thread(() -> {
			try {
				receiver.runWhile(() -> !socket.isClosed());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}, "Server connection").start();
	}
	
	public void ifConnected(Consumer<? super WhiteboardServer> action) {
		activeConnection.get().map(ServerConnection::getServerProxy).ifPresent(action);
	}
	
	public long getClientId() { return context.getClientId(); }
	
	public Observable<Option<ServerConnection>> getActiveConnection() { return activeConnection; }
}
