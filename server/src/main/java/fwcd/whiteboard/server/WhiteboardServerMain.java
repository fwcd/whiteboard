package fwcd.whiteboard.server;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

public class WhiteboardServerMain {
	public static void main(String[] args) {
		try (
			ServerSocket serverSocket = new ServerSocket(0);
		) {
			System.out.println("Starting TCP server on port " + serverSocket.getLocalPort()); // TODO: Logging
			new WhiteboardServerRunner().run(serverSocket);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
