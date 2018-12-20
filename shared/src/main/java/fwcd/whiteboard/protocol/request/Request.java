package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.MessageCategory;
import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public abstract class Request extends Message {
	public Request(String type) {
		super(MessageCategory.REQUEST, type);
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveRequest(this);
	}
	
	public abstract void sendTo(WhiteboardServer server);
	
	@Override
	public String toString() {
		return "Request [name=" + getName() + "]";
	}
}
