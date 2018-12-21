package fwcd.whiteboard.protocol.event;

import fwcd.fructose.Option;
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
	
	public UpdateDrawPositionEvent(ClientInfo requester, Option<Vec2> drawPos) {
		this(requester, drawPos.orElseNull());
	}
	
	@Override
	public void sendTo(WhiteboardClient client) {
		client.updateDrawPosition(this);
	}
	
	public Option<Vec2> getDrawPos() { return Option.ofNullable(drawPos); }
	
	@Override
	public String toString() {
		return "UpdateDrawPositionEvent [drawPos=" + drawPos + "]";
	}
}
