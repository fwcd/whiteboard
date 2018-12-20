package fwcd.whiteboard.server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhiteboardServerMain {
	private static final Logger LOG = LoggerFactory.getLogger(WhiteboardServerMain.class);
	
	public static void main(String[] args) {
		try (
			ServerSocket serverSocket = new ServerSocket(0);
		) {
			LOG.info("Starting TCP server on port {}", serverSocket.getLocalPort());
			new WhiteboardServerRunner().run(serverSocket);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
