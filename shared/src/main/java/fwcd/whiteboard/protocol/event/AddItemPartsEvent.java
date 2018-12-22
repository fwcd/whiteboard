package fwcd.whiteboard.protocol.event;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemPartsEvent extends Event {
	private List<WhiteboardItem> addedParts;
	
	// Gson constructor
	protected AddItemPartsEvent() {}
	
	public AddItemPartsEvent(ClientInfo requester, List<WhiteboardItem> addedParts) {
		super(requester, EventName.ADD_ITEM_PARTS);
		this.addedParts = addedParts;
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.addParts(this);
	}
	
	public List<WhiteboardItem> getAddedParts() { return addedParts; }
	
	@Override
	public String toString() {
		return "AddItemPartsEvent [addedParts=" + addedParts + "]";
	}
}
