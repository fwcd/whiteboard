package com.fwcd.whiteboard.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

import com.fwcd.whiteboard.endpoint.ProtocolReceiver;
import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class ClientConnectionHandler implements Runnable {
	private final Set<ClientConnection> activeConnections;
	private final WhiteboardServer server;
	private final ClientConnection connection;
	
	public ClientConnectionHandler(
		Set<ClientConnection> activeConnections,
		WhiteboardServer server,
		Socket clientSocket
	) {
		this.activeConnections = activeConnections;
		this.server = server;
		
		connection = new ClientConnection(clientSocket);
		activeConnections.add(connection);
	}
	
	@Override
	public void run() {
		try {
			Socket socket = connection.getClientSocket();
			ProtocolReceiver receiver = ProtocolReceiver.ofServer(socket.getInputStream(), server);
			receiver.runWhile(() -> !socket.isClosed());
		} catch (IOException e) {
			System.out.println("IOException in ClientConnectionHandler #" + hashCode() + ": " + e.getMessage()); // TODO: Proper logging
		}
		
		activeConnections.remove(connection);
	}
}
