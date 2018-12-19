package fwcd.whiteboard.client.model;

import fwcd.sketch.model.SketchBoardModel;
import fwcd.whiteboard.client.model.network.LocalWhiteboardClient;
import fwcd.whiteboard.client.model.network.ServerConnector;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;

/**
 * A model class encapsulating the state of the
 * whiteboard application.
 */
public class WhiteboardModel {
	private final SketchBoardModel board = new SketchBoardModel();
	private final WhiteboardClient client = new LocalWhiteboardClient(board);
	private final ServerConnector serverConnector = new ServerConnector();
	
	public SketchBoardModel getBoard() { return board; }
	
	public WhiteboardClient getClient() { return client; }
	
	public ServerConnector getServerConnector() { return serverConnector; }
}
