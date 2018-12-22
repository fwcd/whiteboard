package fwcd.whiteboard.protocol.request;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemPartsRequest extends Request {
	private List<WhiteboardItem> addedParts;
	
	// Gson constructor
	protected AddItemPartsRequest() {}
	
	public AddItemPartsRequest(long senderId, List<WhiteboardItem> addedParts) {
		super(senderId, RequestName.ADD_ITEM_PARTS);
		this.addedParts = addedParts;
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.addParts(this);
	}
	
	public List<WhiteboardItem> getAddedParts() { return addedParts; }
	
	@Override
	public String toString() {
		return "AddItemPartsRequest [addedParts=" + addedParts + "]";
	}
}
