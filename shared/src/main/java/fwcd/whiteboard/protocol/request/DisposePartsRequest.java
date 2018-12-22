package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class DisposePartsRequest extends Request {
	protected DisposePartsRequest() {}
	
	public DisposePartsRequest(long senderId) {
		super(senderId, RequestName.DISPOSE_PARTS);
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.disposeParts(this);
	}
	
	@Override
	public String toString() {
		return "DisposePartsRequest";
	}
}
