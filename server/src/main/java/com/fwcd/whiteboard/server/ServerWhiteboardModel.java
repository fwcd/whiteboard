package com.fwcd.whiteboard.server;

import java.util.ArrayList;
import java.util.List;

import com.fwcd.fructose.EventListenerList;
import com.fwcd.whiteboard.protocol.event.AddItemsEvent;
import com.fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import com.fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class ServerWhiteboardModel {
	private final List<WhiteboardItem> items = new ArrayList<>();
	private final EventListenerList<AddItemsEvent> addListeners = new EventListenerList<>();
	private final EventListenerList<UpdateAllItemsEvent> updateAllListeners = new EventListenerList<>();
	
	public void addItems(List<? extends WhiteboardItem> addedItems) {
		items.addAll(addedItems);
		addListeners.fire(new AddItemsEvent(addedItems, items.size()));
	}
	
	public void setAllItems(List<? extends WhiteboardItem> newItems) {
		items.clear();
		items.addAll(newItems);
		updateAllListeners.fire(new UpdateAllItemsEvent(newItems));
	}
	
	public List<? extends WhiteboardItem> getItems() { return items; }
	
	public EventListenerList<AddItemsEvent> getAddListeners() { return addListeners; }
	
	public EventListenerList<UpdateAllItemsEvent> getUpdateAllListeners() { return updateAllListeners; }
}
