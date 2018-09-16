package com.fwcd.whiteboard.protocol.request;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class GetAllItemsRequest extends Request {
	public GetAllItemsRequest() {
		super(RequestName.GET_ALL_ITEMS);
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.getAllItems(this);
	}
}
