package fwcd.whiteboard.protocol.request;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemsRequest extends Request {
	private final List<WhiteboardItem> addedItems;
	
	public AddItemsRequest(List<WhiteboardItem> addedItems) {
		super(RequestName.ADD_ITEMS);
		this.addedItems = addedItems;
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.addItems(this);
	}
	
	public List<WhiteboardItem> getAddedItems() { return addedItems; }
	
	@Override
	public String toString() {
		return "AddItemsRequest [addedItems=" + addedItems + "]";
	}
}
