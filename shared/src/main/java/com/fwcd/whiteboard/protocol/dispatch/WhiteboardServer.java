package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import com.fwcd.whiteboard.protocol.request.Request;

public interface WhiteboardServer {
	default void getAllItems(GetAllItemsRequest request) { request(request); }
	
	default void request(Request request) {}
}
