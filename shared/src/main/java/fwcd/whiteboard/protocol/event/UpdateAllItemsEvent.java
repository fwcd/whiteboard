package fwcd.whiteboard.protocol.event;

import java.util.List;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class UpdateAllItemsEvent extends Event {
	private final List<? extends WhiteboardItem> items;
	
	public UpdateAllItemsEvent(List<? extends WhiteboardItem> items) {
		super(EventName.UPDATE_ALL_ITEMS);
		this.items = items;
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.updateAllItems(this);
	}
	
	public List<? extends WhiteboardItem> getItems() { return items; }
	
	@Override
	public String toString() {
		return "UpdateAllItemsEvent [items=" + items + "]";
	}
}
