package com.fwcd.whiteboard.server;

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
		try (Socket socket = connection.getClientSocket()) {
			System.out.println("Connected to client " + socket.getLocalAddress() + ":" + socket.getLocalPort()); // TODO: Proper logging
			
			ProtocolReceiver receiver = ProtocolReceiver.ofServer(socket.getInputStream(), server);
			receiver.runWhile(() -> !socket.isClosed());
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName() + " in ClientConnectionHandler #" + hashCode() + ": " + e.getMessage()); // TODO: Proper logging
		}
		
		activeConnections.remove(connection);
	}
}
