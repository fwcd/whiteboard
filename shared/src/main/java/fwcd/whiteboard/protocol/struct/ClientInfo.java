package fwcd.whiteboard.protocol.struct;

import fwcd.fructose.Option;

public class ClientInfo {
	private long id;
	private String name;
	
	// Gson constructor
	protected ClientInfo() {}
	
	public ClientInfo(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public long getId() { return id; }
	
	public Option<String> getName() { return Option.ofNullable(name); }
}
