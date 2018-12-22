package fwcd.whiteboard.client.model.overlay;

import fwcd.sketch.model.items.BoardItemStack;
import fwcd.whiteboard.client.model.utils.EmptyItem;

public class ClientOverlays {
	private final BoardItemStack drawPosition = new BoardItemStack(EmptyItem.INSTANCE);
	private final BoardItemStack unfinishedItem = new BoardItemStack(EmptyItem.INSTANCE);
	
	public BoardItemStack getDrawPosition() { return drawPosition; }
	
	public BoardItemStack getUnfinishedItem() { return unfinishedItem; }
	
	public void addTo(BoardOverlayModel model) {
		model.addItem(drawPosition);
		model.addItem(unfinishedItem);
	}
}
