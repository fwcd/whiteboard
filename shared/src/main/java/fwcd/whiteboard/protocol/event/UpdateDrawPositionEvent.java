package fwcd.whiteboard.protocol.event;

import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.struct.ClientInfo;
import fwcd.whiteboard.protocol.struct.Vec2;

public class UpdateDrawPositionEvent extends Event {
	private Vec2 drawPos;
	
	// Gson constructor
	protected UpdateDrawPositionEvent() {}
	
	public UpdateDrawPositionEvent(ClientInfo requester, Vec2 drawPos) {
		super(requester, EventName.UPDATE_DRAW_POSITION);
		this.drawPos = drawPos;
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.updateDrawPosition(this);
	}
	
	public Vec2 getDrawPos() { return drawPos; }
	
	@Override
	public String toString() {
		return "UpdateDrawPositionEvent [drawPos=" + drawPos + "]";
	}
}
