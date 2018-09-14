package com.fwcd.whiteboard.model.networking.packets;

import com.fwcd.sketch.model.SketchBoardModel;

public class ClearPacket implements Packet<SketchBoardModel> {
	private static final long serialVersionUID = 43979394875L;
	
	public ClearPacket() {}
	
	@Override
	public void apply(SketchBoardModel model) {
		model.getItems().clear();
	}
}
