package fwcd.whiteboard.protocol.event;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class UpdateAllItemsEvent extends Event {
	private List<WhiteboardItem> items;
	
	// Gson constructor
	protected UpdateAllItemsEvent() {}
	
	public UpdateAllItemsEvent(ClientInfo requester, List<WhiteboardItem> items) {
		super(requester, EventName.UPDATE_ALL_ITEMS);
		this.items = items;
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.updateAllItems(this);
	}
	
	public List<WhiteboardItem> getItems() { return items; }
	
	@Override
	public String toString() {
		return "UpdateAllItemsEvent [items=" + items + "]";
	}
}
