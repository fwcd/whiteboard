package fwcd.whiteboard.client.model.network;

import java.io.IOException;
import java.net.Socket;

import fwcd.fructose.Observable;
import fwcd.fructose.Option;

/**
 * A facility that establishes and holds an
 * active connection to a server once present.
 */
public class ServerConnector {
	private Observable<Option<ServerConnection>> activeConnection = new Observable<>(Option.empty());
	
	public void connect(String host, int port) throws IOException {
		activeConnection.set(Option.of(new ServerConnection(new Socket(host, port))));
	}
	
	public Observable<Option<ServerConnection>> getActiveConnection() { return activeConnection; }
}
