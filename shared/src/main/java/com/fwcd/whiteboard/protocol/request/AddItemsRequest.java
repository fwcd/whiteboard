package com.fwcd.whiteboard.protocol.request;

import java.util.List;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import com.fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemsRequest extends Request {
	private final List<? extends WhiteboardItem> items;
	
	public AddItemsRequest(List<? extends WhiteboardItem> items) {
		super(RequestName.ADD_ITEMS);
		this.items = items;
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.addItems(this);
	}
	
	public List<? extends WhiteboardItem> getItems() { return items; }
}
