package fwcd.whiteboard.protocol.struct;

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
	
	public String getName() { return name; }
}
