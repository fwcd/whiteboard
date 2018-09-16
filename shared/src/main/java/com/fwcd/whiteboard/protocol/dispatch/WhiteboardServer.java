package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.request.Request;

public interface WhiteboardServer {
	default void request(Request request) {}
}
