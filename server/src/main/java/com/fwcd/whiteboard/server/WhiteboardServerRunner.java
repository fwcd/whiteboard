package com.fwcd.whiteboard.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class WhiteboardServerRunner {
	private final Set<ClientConnection> activeConnections = Collections.synchronizedSet(new HashSet<>());
	private final WhiteboardServer server = new LocalWhiteboardServer(activeConnections);
	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	public void run(ServerSocket serverSocket) throws IOException {
		while (true) {
			Socket clientSocket = serverSocket.accept();
			executor.execute(new ClientConnectionHandler(activeConnections, server, clientSocket));
		}
	}
}
