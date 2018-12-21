package fwcd.whiteboard.server;

import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.request.AddItemsRequest;
import fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import fwcd.whiteboard.protocol.request.Request;
import fwcd.whiteboard.protocol.request.SetAllItemsRequest;

public class LocalWhiteboardServer implements WhiteboardServer {
	private static final Logger LOG = LoggerFactory.getLogger(LocalWhiteboardServer.class);
	private final Set<ClientConnection> activeConnections;
	private final ServerWhiteboardModel model = new ServerWhiteboardModel();
	
	public LocalWhiteboardServer(Set<ClientConnection> activeConnections) {
		this.activeConnections = activeConnections;
		
		model.getAddListeners().add(event -> forEachClient(c -> c.addItems(event)));
		model.getUpdateAllListeners().add(event -> forEachClient(c -> c.updateAllItems(event)));
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
			forEachClient(c -> c.updateAllItems(new UpdateAllItemsEvent(request.getSenderId(), model.getItems())));
		}
	}
	
	@Override
	public void setAllItems(SetAllItemsRequest request) {
		synchronized (model) {
			model.setAllItems(request.getSenderId(), request.getItems());
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
	public void otherRequest(Request request) {
		LOG.info("Received unknown request: {}", request);
	}
}
