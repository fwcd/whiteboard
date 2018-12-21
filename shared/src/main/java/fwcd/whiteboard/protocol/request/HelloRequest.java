package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.struct.ClientInfo;

public class HelloRequest extends Request {
	private ClientInfo info;
	
	// Gson constructor
	protected HelloRequest() {}
	
	public HelloRequest(long senderId, ClientInfo info) {
		super(senderId, RequestName.HELLO);
		this.info = info;
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.hello(this);
	}
	
	public ClientInfo getInfo() { return info; }
	
	@Override
	public String toString() {
		return "HelloRequest [info=" + info + "]";
	}
}
