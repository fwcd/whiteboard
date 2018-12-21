package fwcd.whiteboard.protocol.event;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemsEvent extends Event {
	private List<WhiteboardItem> addedItems;
	private int totalItemCount;
	
	// Gson constructor
	protected AddItemsEvent() {}
	
	public AddItemsEvent(long requesterId, List<WhiteboardItem> addedItems, int totalItemCount) {
		super(requesterId, EventName.ADD_ITEMS);
		this.addedItems = addedItems;
		this.totalItemCount = totalItemCount;
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.addItems(this);
	}
	
	public List<WhiteboardItem> getAddedItems() { return addedItems; }
	
	public int getTotalItemCount() { return totalItemCount; }
	
	@Override
	public String toString() {
		return "AddItemsEvent [addedItems=" + addedItems + ", totalItemCount=" + totalItemCount + "]";
	}
}
