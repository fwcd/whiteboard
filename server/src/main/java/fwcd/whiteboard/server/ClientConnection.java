package fwcd.whiteboard.server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

import fwcd.whiteboard.endpoint.RemoteWhiteboard;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;

/**
 * An active connection with a client.
 */
public class ClientConnection {
	private final Socket clientSocket;
	private final WhiteboardClient clientProxy;
	
	public ClientConnection(Socket clientSocket) {
		try {
			this.clientSocket = clientSocket;
			clientProxy = new RemoteWhiteboard(clientSocket.getOutputStream());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public Socket getClientSocket() { return clientSocket; }
	
	public WhiteboardClient getClientProxy() { return clientProxy; }
}
