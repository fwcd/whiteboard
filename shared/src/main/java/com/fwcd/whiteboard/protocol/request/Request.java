package com.fwcd.whiteboard.protocol.request;

import com.fwcd.whiteboard.protocol.Message;
import com.fwcd.whiteboard.protocol.MessageCategory;
import com.fwcd.whiteboard.protocol.dispatch.MessageDispatcher;
import com.fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public abstract class Request extends Message {
	public Request(String type) {
		super(MessageCategory.REQUEST, type);
	}
	
	@Override
	public void dispatch(MessageDispatcher dispatcher) {
		dispatcher.receiveRequest(this);
	}
	
	public abstract void sendTo(WhiteboardServer server);
}
