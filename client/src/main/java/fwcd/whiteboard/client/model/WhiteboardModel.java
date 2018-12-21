package fwcd.whiteboard.client.model;

import fwcd.sketch.model.SketchBoardModel;
import fwcd.whiteboard.client.model.network.ServerConnectionManager;

/**
 * A model class encapsulating the state of the whiteboard application.
 */
public class WhiteboardModel {
	private final SketchBoardModel board = new SketchBoardModel();
	private final ServerConnectionManager serverConnector = new ServerConnectionManager(board);
	
	public SketchBoardModel getBoard() { return board; }
	
	public ServerConnectionManager getServerConnector() { return serverConnector; }
}
