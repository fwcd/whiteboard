package fwcd.whiteboard.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.whiteboard.endpoint.ProtocolReceiver;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.request.DisconnectRequest;
import fwcd.whiteboard.protocol.request.HelloRequest;

public class ClientConnectionHandler implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(ClientConnectionHandler.class);
	private final Set<ClientConnection> activeConnections;
	private final WhiteboardServer server;
	private final ClientConnection connection;
	private boolean receivedDisconnectRequest = false;
	
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
			LOG.info("Connected to client {}:{}", socket.getLocalAddress(), socket.getLocalPort());
			
			ProtocolReceiver receiver = ProtocolReceiver.ofServer(socket.getInputStream(), server);
			receiver.addExceptionListener(Exception.class, e -> LOG.error("Error while connected to client: ", e));
			receiver.addMessageListener(DisconnectRequest.class, e -> receivedDisconnectRequest = true);
			receiver.addMessageListener(HelloRequest.class, r -> {
				connection.setClientInfo(r.getInfo());
				LOG.info("Hello {}!", r.getInfo().getName());
			});
			receiver.runWhile(() -> !receivedDisconnectRequest && !socket.isClosed());
		} catch (IOException e) {
			LOG.error("Fatal IOException: ", e);
		}
		
		activeConnections.remove(connection);
	}
}
