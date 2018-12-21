package fwcd.whiteboard.client.model;

import fwcd.sketch.model.SketchBoardModel;
import fwcd.whiteboard.client.model.network.ServerConnectionManager;
import fwcd.whiteboard.client.model.overlay.BoardOverlayModel;

/**
 * A model class encapsulating the state of the whiteboard application.
 */
public class WhiteboardModel {
	private final SketchBoardModel board = new SketchBoardModel();
	private final BoardOverlayModel overlay = new BoardOverlayModel();
	private final ServerConnectionManager connectionManager = new ServerConnectionManager(board, overlay);
	
	public SketchBoardModel getBoard() { return board; }
	
	public BoardOverlayModel getOverlay() { return overlay; }
	
	public ServerConnectionManager getConnectionManager() { return connectionManager; }
}
