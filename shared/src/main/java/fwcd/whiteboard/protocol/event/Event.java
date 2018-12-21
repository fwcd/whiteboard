package fwcd.whiteboard.protocol.event;

import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.MessageCategory;
import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;

public abstract class Event extends Message {
	private ClientInfo requester;
	
	// Gson constructor
	protected Event() {}
	
	public Event(ClientInfo requester, String type) {
		super(MessageCategory.EVENT, type);
		this.requester = requester;
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveEvent(this);
	}
	
	public abstract void sendTo(WhiteboardClient client);
	
	public ClientInfo getRequester() { return requester; }
	
	@Override
	public String toString() {
		return "Event [name=" + getName() + "]";
	}
}
