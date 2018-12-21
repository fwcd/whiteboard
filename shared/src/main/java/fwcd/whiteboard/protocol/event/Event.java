package fwcd.whiteboard.protocol.event;

import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.MessageCategory;
import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;

public abstract class Event extends Message {
	private long requesterId;
	
	// Gson constructor
	protected Event() {}
	
	public Event(long requesterId, String type) {
		super(MessageCategory.EVENT, type);
		this.requesterId = requesterId;
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveEvent(this);
	}
	
	public abstract void sendTo(WhiteboardClient client);
	
	public long getRequesterId() { return requesterId; }
	
	@Override
	public String toString() {
		return "Event [name=" + getName() + "]";
	}
}
