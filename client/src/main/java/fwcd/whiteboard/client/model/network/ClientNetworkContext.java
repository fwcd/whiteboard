package fwcd.whiteboard.client.model.network;

/**
 * Encapsulates client networking state that
 * conceptually neither belongs to the sender
 * nor the receiver.
 */
public class ClientNetworkContext {
	private boolean silent = false;
	
	public boolean isSilent() { return silent; }
	
	public void setSilent(boolean silent) { this.silent = silent; }
}
