package fwcd.whiteboard.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fwcd.fructose.EventListenerList;
import fwcd.fructose.Option;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.event.UpdateDrawPositionEvent;
import fwcd.whiteboard.protocol.struct.ClientInfo;
import fwcd.whiteboard.protocol.struct.Vec2;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * The server-side representation of the current
 * whiteboard including "live" information about
 * clients, such as their current draw position.
 */
public class ServerWhiteboardModel {
	private final List<WhiteboardItem> items = new ArrayList<>();
	private final Map<Long, ClientStateModel> clients = new HashMap<>();
	
	private final EventListenerList<AddItemsEvent> addListeners = new EventListenerList<>();
	private final EventListenerList<UpdateAllItemsEvent> updateAllListeners = new EventListenerList<>();
	private final EventListenerList<UpdateDrawPositionEvent> updateDrawPosListeners = new EventListenerList<>();
	
	public void updateClientDrawPosition(long requesterId, Option<Vec2> position) {
		stateOf(requesterId).setDrawPos(position);
		updateDrawPosListeners.fire(new UpdateDrawPositionEvent(clientInfoOf(requesterId), position));
	}
	
	public void addItems(long requesterId, List<WhiteboardItem> addedItems) {
		items.addAll(addedItems);
		addListeners.fire(new AddItemsEvent(clientInfoOf(requesterId), addedItems, items.size()));
	}
	
	public void setAllItems(long requesterId, List<WhiteboardItem> newItems) {
		items.clear();
		items.addAll(newItems);
		updateAllListeners.fire(new UpdateAllItemsEvent(clientInfoOf(requesterId), newItems));
	}
	
	private ClientStateModel stateOf(long clientId) {
		ClientStateModel state = clients.get(clientId);
		if (state == null) {
			state = new ClientStateModel(clientId);
			clients.put(clientId, state);
		}
		return state;
	}
	
	public void addClientInfo(ClientInfo info) {
		clients.put(info.getId(), new ClientStateModel(info));
	}
	
	public void removeClientInfo(long clientId) {
		clients.remove(clientId);
	}
	
	public ClientInfo clientInfoOf(long clientId) {
		if (clients.containsKey(clientId)) {
			return clients.get(clientId).getInfo();
		} else {
			return new ClientInfo(clientId, "");
		}
	}
	
	public List<WhiteboardItem> getItems() { return items; }
	
	public EventListenerList<AddItemsEvent> getAddListeners() { return addListeners; }
	
	public EventListenerList<UpdateAllItemsEvent> getUpdateAllListeners() { return updateAllListeners; }
	
	public EventListenerList<UpdateDrawPositionEvent> getUpdateDrawPosListeners() { return updateDrawPosListeners; }
}
