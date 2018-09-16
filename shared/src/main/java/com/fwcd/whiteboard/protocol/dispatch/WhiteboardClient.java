package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import com.fwcd.whiteboard.protocol.event.UpdateItemsEvent;

public interface WhiteboardClient {
	default void updateItems(UpdateItemsEvent event) { event(event); }
	
	default void updateAllItems(UpdateAllItemsEvent event) { event(event); }
	
	default void event(Event event) {}
}
