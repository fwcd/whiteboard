package fwcd.whiteboard.protocol.event;

import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.MessageCategory;
import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;

public abstract class Event extends Message {
	public Event(String type) {
		super(MessageCategory.EVENT, type);
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveEvent(this);
	}
	
	public abstract void sendTo(WhiteboardClient client);
	
	@Override
	public String toString() {
		return "Event [name=" + getName() + "]";
	}
}
