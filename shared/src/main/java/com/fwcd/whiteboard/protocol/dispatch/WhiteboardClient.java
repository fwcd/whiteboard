package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.event.AddItemsEvent;
import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;

public interface WhiteboardClient {
	default void addItems(AddItemsEvent event) { event(event); }
	
	default void updateAllItems(UpdateAllItemsEvent event) { event(event); }
	
	default void event(Event event) {}
}
