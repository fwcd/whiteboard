package fwcd.whiteboard.client.model.network;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Encapsulates client networking state that
 * conceptually neither belongs to the sender
 * nor the receiver.
 */
public class ClientNetworkContext {
	private final long clientId = ThreadLocalRandom.current().nextLong();
	private boolean silent = false;
	
	public long getClientId() { return clientId; }
	
	public boolean isSilent() { return silent; }
	
	public void setSilent(boolean silent) { this.silent = silent; }
}
