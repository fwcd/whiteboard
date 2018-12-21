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
import fwcd.whiteboard.endpoint.ProtocolReceiver;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.request.AddItemsRequest;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * A facility that establishes and holds an
 * active connection to a server once present.
 */
public class ServerConnectionManager {
	private final ClientNetworkContext context = new ClientNetworkContext();
	private final LocalWhiteboardClient client;
	private final Observable<Option<ServerConnection>> activeConnection = new Observable<>(Option.empty());
	
	public ServerConnectionManager(SketchBoardModel board) {
		client = new LocalWhiteboardClient(board, context);
		registerListeners(board);
	}
	
	private void registerListeners(SketchBoardModel board) {
		BoardItemEventBus eventBus = board.getItemEventBus();
		
		eventBus.getAddListeners().add(item -> {
			if (!context.isSilent()) {
				toProtocolItem(item.get()).ifPresent(protocolItem ->
					ifConnected(wb -> {
						wb.addItems(new AddItemsRequest(Collections.singletonList(protocolItem)));
					})
				);
			}
		});
	}
	
	private Option<WhiteboardItem> toProtocolItem(SketchItem item) {
		ToProtocolItemConverter converter = new ToProtocolItemConverter();
		item.accept(converter);
		return converter.getResult();
	}
	
	public void connect(String host, int port) throws IOException {
		Socket socket = new Socket(host, port);
		activeConnection.set(Option.of(new ServerConnection(socket)));
		
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
	
	public Observable<Option<ServerConnection>> getActiveConnection() { return activeConnection; }
}
