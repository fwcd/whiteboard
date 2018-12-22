package fwcd.whiteboard.protocol.dispatch;

import fwcd.whiteboard.protocol.request.AddItemPartsRequest;
import fwcd.whiteboard.protocol.request.AddItemsRequest;
import fwcd.whiteboard.protocol.request.DisposePartsRequest;
import fwcd.whiteboard.protocol.request.DisconnectRequest;
import fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import fwcd.whiteboard.protocol.request.HelloRequest;
import fwcd.whiteboard.protocol.request.Request;
import fwcd.whiteboard.protocol.request.SetAllItemsRequest;
import fwcd.whiteboard.protocol.request.UpdateDrawPositionRequest;

public interface WhiteboardServer {
	default void addItems(AddItemsRequest request) { otherRequest(request); }
	
	default void addParts(AddItemPartsRequest request) { otherRequest(request); }
	
	default void disposeParts(DisposePartsRequest request) { otherRequest(request); }
	
	default void setAllItems(SetAllItemsRequest request) { otherRequest(request); }
	
	default void getAllItems(GetAllItemsRequest request) { otherRequest(request); }
	
	default void updateDrawPosition(UpdateDrawPositionRequest request) { otherRequest(request); }
	
	default void disconnect(DisconnectRequest request) { otherRequest(request); }
	
	default void hello(HelloRequest request) { otherRequest(request); }
	
	default void otherRequest(Request request) {}
}
