package fwcd.whiteboard.server;

import java.net.Socket;
import java.util.Set;

import fwcd.whiteboard.endpoint.ProtocolReceiver;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

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
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName() + " in ClientConnectionHandler #" + hashCode() + ": " + e.getMessage()); // TODO: Proper logging
			Throwable cause = e.getCause();
			
			while (cause != null) {
				System.out.println("Cause: " + cause.getClass().getSimpleName() + ": " + cause.getMessage());
				cause = cause.getCause();
			}
		}
		
		activeConnections.remove(connection);
	}
}
