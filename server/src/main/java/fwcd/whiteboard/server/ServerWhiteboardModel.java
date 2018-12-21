package fwcd.whiteboard.server;

import java.util.ArrayList;
import java.util.List;

import fwcd.fructose.EventListenerList;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class ServerWhiteboardModel {
	private final List<WhiteboardItem> items = new ArrayList<>();
	private final EventListenerList<AddItemsEvent> addListeners = new EventListenerList<>();
	private final EventListenerList<UpdateAllItemsEvent> updateAllListeners = new EventListenerList<>();
	
	public void addItems(long requesterId, List<WhiteboardItem> addedItems) {
		items.addAll(addedItems);
		addListeners.fire(new AddItemsEvent(requesterId, addedItems, items.size()));
	}
	
	public void setAllItems(long requesterId, List<WhiteboardItem> newItems) {
		items.clear();
		items.addAll(newItems);
		updateAllListeners.fire(new UpdateAllItemsEvent(requesterId, newItems));
	}
	
	public List<WhiteboardItem> getItems() { return items; }
	
	public EventListenerList<AddItemsEvent> getAddListeners() { return addListeners; }
	
	public EventListenerList<UpdateAllItemsEvent> getUpdateAllListeners() { return updateAllListeners; }
}
