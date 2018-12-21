package fwcd.whiteboard.protocol.request;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemsRequest extends Request {
	private List<WhiteboardItem> addedItems;
	
	// Gson constructor
	protected AddItemsRequest() {}
	
	public AddItemsRequest(long senderId, List<WhiteboardItem> addedItems) {
		super(senderId, RequestName.ADD_ITEMS);
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
