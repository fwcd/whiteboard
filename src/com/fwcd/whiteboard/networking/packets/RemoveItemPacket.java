package com.fwcd.whiteboard.networking.packets;

import com.fwcd.sketch.model.SketchBoardModel;
import com.fwcd.sketch.model.SketchItem;

public class RemoveItemPacket implements Packet<SketchBoardModel> {
	private static final long serialVersionUID = 43979394875L;
	private SketchItem item;
	
	/**
	 * Serialization only.
	 */
	public RemoveItemPacket() {}
	
	public RemoveItemPacket(SketchItem item) {
		this.item = item;
	}
	
	@Override
	public void apply(SketchBoardModel model) {
		model.remove(item);
	}
}
