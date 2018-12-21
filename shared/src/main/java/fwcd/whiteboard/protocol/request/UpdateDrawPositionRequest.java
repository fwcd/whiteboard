package fwcd.whiteboard.protocol.request;

import fwcd.whiteboard.protocol.dispatch.WhiteboardServer;
import fwcd.whiteboard.protocol.struct.Vec2;

public class UpdateDrawPositionRequest extends Request {
	private Vec2 drawPos;
	
	// Gson constructor
	protected UpdateDrawPositionRequest() {}
	
	public UpdateDrawPositionRequest(long senderId, Vec2 drawPos) {
		super(senderId, RequestName.UPDATE_DRAW_POSITION);
		this.drawPos = drawPos;
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.updateDrawPosition(this);
	}
	
	public Vec2 getDrawPos() { return drawPos; }
	
	@Override
	public String toString() {
		return "UpdateDrawPositionRequest [drawPos=" + drawPos + "]";
	}
}
