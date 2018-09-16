package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.request.AddItemsRequest;
import com.fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import com.fwcd.whiteboard.protocol.request.Request;
import com.fwcd.whiteboard.protocol.request.SetAllItemsRequest;

public interface WhiteboardServer {
	default void addItems(AddItemsRequest request) { request(request); }
	
	default void setAllItems(SetAllItemsRequest request) { request(request); }
	
	default void getAllItems(GetAllItemsRequest request) { request(request); }
	
	default void request(Request request) {}
}
