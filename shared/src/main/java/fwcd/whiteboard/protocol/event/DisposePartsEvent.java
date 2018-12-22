package fwcd.whiteboard.protocol.event;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;

public class DisposePartsEvent extends Event {
	protected DisposePartsEvent() {}
	
	public DisposePartsEvent(ClientInfo requester) {
		super(requester, EventName.DISPOSE_PARTS);
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.disposeParts(this);
	}
	
	@Override
	public String toString() {
		return "DisposePartsEvent";
	}
}
