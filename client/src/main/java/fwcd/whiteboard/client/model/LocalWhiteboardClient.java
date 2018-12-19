package fwcd.whiteboard.client.model;

import fwcd.sketch.model.SketchBoardModel;
import fwcd.sketch.model.items.BoardItem;
import fwcd.sketch.model.items.SketchItem;
import fwcd.whiteboard.client.model.convert.FromProtocolItemConverter;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * A local whiteboard endpoint that interfaces
 * with the client's whiteboard.
 */
public class LocalWhiteboardClient implements WhiteboardClient {
	private final SketchBoardModel board;
	private final WhiteboardItemVisitor<SketchItem> converter = new FromProtocolItemConverter();
	
	public LocalWhiteboardClient(SketchBoardModel board) {
		this.board = board;
	}
	
	@Override
	public void addItems(AddItemsEvent event) {
		for (WhiteboardItem item : event.getAddedItems()) {
			board.addItem(new BoardItem(item.accept(converter)));
		}
	}
	
	@Override
	public void updateAllItems(UpdateAllItemsEvent event) {
		board.clear();
		for (WhiteboardItem item : event.getItems()) {
			board.addItem(new BoardItem(item.accept(converter)));
		}
	}
	
	@Override
	public void otherEvent(Event event) {
		System.out.println("Unknown event: " + event); // TODO: Proper logging
	}
}
