package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.event.AddItemsEvent;
import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;

public interface WhiteboardClient {
	default void addItems(AddItemsEvent event) { otherEvent(event); }
	
	default void updateAllItems(UpdateAllItemsEvent event) { otherEvent(event); }
	
	default void otherEvent(Event event) {}
}
