package fwcd.whiteboard.client.model.network;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

import fwcd.whiteboard.endpoint.RemoteWhiteboard;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

/**
 * An active connection with a server.
 */
public class ServerConnection {
	private final Socket serverSocket;
	private final WhiteboardServer serverProxy;
	
	public ServerConnection(Socket serverSocket) {
		try {
			this.serverSocket = serverSocket;
			serverProxy = new RemoteWhiteboard(serverSocket.getOutputStream());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public Socket getServerSocket() { return serverSocket; }
	
	public WhiteboardServer getServerProxy() { return serverProxy; }
}
