package fwcd.whiteboard.protocol.dispatch;

import fwcd.whiteboard.protocol.event.AddItemPartsEvent;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.DisposePartsEvent;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.event.UpdateDrawPositionEvent;

public interface WhiteboardClient {
	default void addItems(AddItemsEvent event) { otherEvent(event); }
	
	default void addParts(AddItemPartsEvent event) { otherEvent(event); }
	
	default void disposeParts(DisposePartsEvent event) { otherEvent(event); }
	
	default void updateAllItems(UpdateAllItemsEvent event) { otherEvent(event); }
	
	default void updateDrawPosition(UpdateDrawPositionEvent event) { otherEvent(event); }
	
	default void otherEvent(Event event) {}
}
