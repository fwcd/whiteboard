package fwcd.whiteboard.protocol.struct;

public class Range {
	private int start;
	private int end;
	
	// Gson constructor
	protected Range() {}
	
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int getStart() { return start; }
	
	public int getEnd() { return end; }
	
	@Override
	public String toString() {
		return "Range (" + start + ", " + end + ")";
	}
}
