package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class DisconnectRequest extends Request {
	protected DisconnectRequest() {}
	
	public DisconnectRequest(long senderId) {
		super(senderId, RequestName.DISCONNECT);
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.disconnect(this);
	}
	
	@Override
	public String toString() {
		return "DisconnectRequest";
	}
}
