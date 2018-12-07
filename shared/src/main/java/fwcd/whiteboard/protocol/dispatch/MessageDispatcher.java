package fwcd.whiteboard.protocol.dispatch;

import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.request.Request;

public interface MessageDispatcher {
	default void receiveEvent(Event event) {}
	
	default void receiveRequest(Request request) {}
}
