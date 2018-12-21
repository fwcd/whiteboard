package fwcd.whiteboard.protocol.struct;

public class Color {
	// Each value is between 0 and 255 (inclusive)
	private double r;
	private double g;
	private double b;
	private double a;
	
	// Gson constructor
	protected Color() {}
	
	public Color(double r, double g, double b, double a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public double getR() { return r; }
	
	public double getG() { return g; }
	
	public double getB() { return b; }
	
	public double getA() { return a; }
	
	@Override
	public String toString() {
		return "RGBA (" + r + ", " + g + ", " + b + ", " + a + ")";
	}
}
