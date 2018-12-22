package fwcd.whiteboard.protocol.event;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;

public class ComposePartsEvent extends Event {
	protected ComposePartsEvent() {}
	
	public ComposePartsEvent(ClientInfo requester) {
		super(requester, EventName.COMPOSE_PARTS);
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.composeParts(this);
	}
	
	@Override
	public String toString() {
		return "ComposePartsEvent";
	}
}
