package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.request.Request;

public interface MessageDispatcher {
	default void receiveEvent(Event event) {}
	
	default void receiveRequest(Request request) {}
}
