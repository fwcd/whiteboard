package fwcd.whiteboard.protocol.request;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class SetAllItemsRequest extends Request {
	private List<WhiteboardItem> items;
	
	// Gson constructor
	protected SetAllItemsRequest() {}
	
	public SetAllItemsRequest(long senderId, List<WhiteboardItem> items) {
		super(senderId, RequestName.SET_ALL_ITEMS);
		this.items = items;
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.setAllItems(this);
	}
	
	public List<WhiteboardItem> getItems() { return items; }
	
	@Override
	public String toString() {
		return "SetAllItemsRequest [items=" + items + "]";
	}
}
