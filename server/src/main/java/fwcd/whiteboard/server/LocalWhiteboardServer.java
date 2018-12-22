package fwcd.whiteboard.server;

import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.event.AddItemPartsEvent;
import fwcd.whiteboard.protocol.event.ComposePartsEvent;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.request.AddItemPartsRequest;
import fwcd.whiteboard.protocol.request.AddItemsRequest;
import fwcd.whiteboard.protocol.request.ComposePartsRequest;
import fwcd.whiteboard.protocol.request.DisconnectRequest;
import fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import fwcd.whiteboard.protocol.request.HelloRequest;
import fwcd.whiteboard.protocol.request.Request;
import fwcd.whiteboard.protocol.request.SetAllItemsRequest;
import fwcd.whiteboard.protocol.request.UpdateDrawPositionRequest;

public class LocalWhiteboardServer implements WhiteboardServer {
	private static final Logger LOG = LoggerFactory.getLogger(LocalWhiteboardServer.class);
	private final Set<ClientConnection> activeConnections;
	private final ServerWhiteboardModel model = new ServerWhiteboardModel();
	
	public LocalWhiteboardServer(Set<ClientConnection> activeConnections) {
		this.activeConnections = activeConnections;
		
		model.getAddListeners().add(event -> forEachClient(c -> c.addItems(event)));
		model.getUpdateAllListeners().add(event -> forEachClient(c -> c.updateAllItems(event)));
		model.getUpdateDrawPosListeners().add(event -> forEachClient(c -> c.updateDrawPosition(event)));
	}
	
	@Override
	public void addItems(AddItemsRequest request) {
		synchronized (model) {
			model.addItems(request.getSenderId(), request.getAddedItems());
		}
	}
	
	@Override
	public void getAllItems(GetAllItemsRequest request) {
		synchronized (model) {
			forEachClient(c -> c.updateAllItems(new UpdateAllItemsEvent(model.clientInfoOf(request.getSenderId()), model.getItems())));
		}
	}
	
	@Override
	public void setAllItems(SetAllItemsRequest request) {
		synchronized (model) {
			model.setAllItems(request.getSenderId(), request.getItems());
		}
	}
	
	@Override
	public void updateDrawPosition(UpdateDrawPositionRequest request) {
		synchronized (model) {
			model.updateClientDrawPosition(request.getSenderId(), request.getDrawPos());
		}
	}
	
	private void forEachClient(Consumer<WhiteboardClient> consumer) {
		synchronized (activeConnections) {
			for (ClientConnection connection : activeConnections) {
				consumer.accept(connection.getClientProxy());
			}
		}
	}
	
	@Override
	public void disconnect(DisconnectRequest request) {
		long removedId = request.getSenderId();
		model.removeClientInfo(removedId);
		activeConnections.removeIf(conn -> conn.getClientInfo()
			.filter(info -> info.getId() == removedId)
			.isPresent());
		LOG.info("Disconnected client {}", request.getSenderId());
	}
	
	@Override
	public void hello(HelloRequest request) {
		model.addClientInfo(request.getInfo());
	}
	
	@Override
	public void addParts(AddItemPartsRequest request) {
		AddItemPartsEvent event = new AddItemPartsEvent(model.clientInfoOf(request.getSenderId()), request.getAddedParts());
		forEachClient(c -> c.addParts(event));
	}
	
	@Override
	public void composeParts(ComposePartsRequest request) {
		ComposePartsEvent event = new ComposePartsEvent(model.clientInfoOf(request.getSenderId()));
		forEachClient(c -> c.composeParts(event));
	}
	
	@Override
	public void otherRequest(Request request) {
		LOG.info("Received unknown request: {}", request);
	}
}
