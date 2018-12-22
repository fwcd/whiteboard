package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class ComposePartsRequest extends Request {
	protected ComposePartsRequest() {}
	
	public ComposePartsRequest(long senderId) {
		super(senderId, RequestName.COMPOSE_PARTS);
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.composeParts(this);
	}
	
	@Override
	public String toString() {
		return "ComposePartsRequest";
	}
}
