package fwcd.whiteboard.server;

import fwcd.fructose.Option;
import fwcd.whiteboard.protocol.struct.ClientInfo;
import fwcd.whiteboard.protocol.struct.Vec2;

public class ClientStateModel {
	private final long id;
	private String name = "";
	private Option<Vec2> drawPos = Option.empty();
	
	public ClientStateModel(long id) {
		this.id = id;
	}
	
	public ClientInfo getInfo() { return new ClientInfo(id, name); }
	
	public long getId() { return id; }
	
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public Option<Vec2> getDrawPos() { return drawPos; }
	
	public void setDrawPos(Option<Vec2> drawPos) { this.drawPos = drawPos; }
	
	public void setDrawPos(Vec2 drawPos) { setDrawPos(Option.of(drawPos)); }
}
