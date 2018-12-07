package fwcd.whiteboard.protocol.dispatch;

import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;

public interface WhiteboardClient {
	default void addItems(AddItemsEvent event) { otherEvent(event); }
	
	default void updateAllItems(UpdateAllItemsEvent event) { otherEvent(event); }
	
	default void otherEvent(Event event) {}
}
