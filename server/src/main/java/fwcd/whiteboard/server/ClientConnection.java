package fwcd.whiteboard.server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Socket;

import fwcd.fructose.Option;
import fwcd.whiteboard.endpoint.RemoteWhiteboard;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;

/**
 * An active connection with a client.
 */
public class ClientConnection implements AutoCloseable {
	private final Socket clientSocket;
	private final WhiteboardClient clientProxy;
	private Option<ClientInfo> clientInfo = Option.empty();
	
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
	
	public void setClientInfo(ClientInfo clientInfo) { this.clientInfo = Option.of(clientInfo); }
	
	public Option<ClientInfo> getClientInfo() { return clientInfo; }
	
	@Override
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
