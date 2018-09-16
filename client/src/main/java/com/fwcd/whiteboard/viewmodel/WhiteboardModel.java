package com.fwcd.whiteboard.viewmodel;

import com.fwcd.sketch.model.SketchBoardModel;

public class WhiteboardModel {
	private final SketchBoardModel board = new SketchBoardModel();
	
	public SketchBoardModel getBoard() {
		return board;
	}
}
