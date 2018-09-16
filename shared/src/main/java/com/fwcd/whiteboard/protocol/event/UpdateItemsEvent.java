package com.fwcd.whiteboard.protocol.event;

import java.util.List;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import com.fwcd.whiteboard.protocol.struct.Range;
import com.fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class UpdateItemsEvent extends Event {
	private final List<? extends WhiteboardItem> items;
	private final Range range;
	private final int totalItemCount;
	
	public UpdateItemsEvent(List<? extends WhiteboardItem> items, Range range, int totalItemCount) {
		super(EventName.UPDATE_ITEMS);
		this.items = items;
		this.range = range;
		this.totalItemCount = totalItemCount;
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.updateItems(this);
	}
	
	public List<? extends WhiteboardItem> getItems() { return items; }
	
	public Range getRange() { return range; }
	
	public int getTotalItemCount() { return totalItemCount; }
}
