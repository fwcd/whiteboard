package fwcd.whiteboard.client.model;

import fwcd.sketch.model.SketchBoardModel;
import fwcd.whiteboard.client.model.network.ServerConnector;

/**
 * A model class encapsulating the state of the whiteboard application.
 */
public class WhiteboardModel {
	private final SketchBoardModel board = new SketchBoardModel();
	private final ServerConnector serverConnector = new ServerConnector(board);
	
	public SketchBoardModel getBoard() { return board; }
	
	public ServerConnector getServerConnector() { return serverConnector; }
}
