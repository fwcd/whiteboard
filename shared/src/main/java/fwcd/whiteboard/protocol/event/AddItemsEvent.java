package fwcd.whiteboard.protocol.event;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class AddItemsEvent extends Event {
	private final List<WhiteboardItem> addedItems;
	private final int totalItemCount;
	
	public AddItemsEvent(List<WhiteboardItem> addedItems, int totalItemCount) {
		super(EventName.ADD_ITEMS);
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
