package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.request.AddItemsRequest;
import com.fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import com.fwcd.whiteboard.protocol.request.Request;
import com.fwcd.whiteboard.protocol.request.SetAllItemsRequest;

public interface WhiteboardServer {
	default void addItems(AddItemsRequest request) { otherRequest(request); }
	
	default void setAllItems(SetAllItemsRequest request) { otherRequest(request); }
	
	default void getAllItems(GetAllItemsRequest request) { otherRequest(request); }
	
	default void otherRequest(Request request) {}
}
