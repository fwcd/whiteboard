package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.MessageCategory;
import fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public abstract class Request extends Message {
	private long senderId;
	
	// Gson constructor
	protected Request() {}
	
	public Request(long senderId, String type) {
		super(MessageCategory.REQUEST, type);
		this.senderId = senderId;
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveRequest(this);
	}
	
	public abstract void sendTo(WhiteboardServer server);
	
	public long getSenderId() { return senderId; }
	
	@Override
	public String toString() {
		return "Request [name=" + getName() + "]";
	}
}
