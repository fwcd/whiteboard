package fwcd.whiteboard.protocol.request;

import fwcd.fructose.Option;
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
	
	public UpdateDrawPositionRequest(long senderId, Option<Vec2> drawPos) {
		this(senderId, drawPos.orElseNull());
	}
	
	@Override
	public void sendTo(WhiteboardServer server) {
		server.updateDrawPosition(this);
	}
	
	public Option<Vec2> getDrawPos() { return Option.ofNullable(drawPos); }
	
	@Override
	public String toString() {
		return "UpdateDrawPositionRequest [drawPos=" + drawPos + "]";
	}
}
