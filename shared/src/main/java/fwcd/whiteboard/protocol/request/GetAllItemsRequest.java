package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;

public class GetAllItemsRequest extends Request {
	protected GetAllItemsRequest() {}
	
	public GetAllItemsRequest(long senderId) {
		super(senderId, RequestName.GET_ALL_ITEMS);
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.getAllItems(this);
	}
	
	@Override
	public String toString() {
		return "GetAllItemsRequest";
	}
}
