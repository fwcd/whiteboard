package fwcd.whiteboard.client.view.utils;

import java.awt.Color;

public class ColorUtils {
	private ColorUtils() {}
	
	public static Color invert(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}
	
	public static Color softer(Color color) {
		return new Color(softer(color.getRed()), softer(color.getGreen()), softer(color.getBlue()), color.getAlpha());
	}
	
	public static int softer(int colorComponent) {
		return colorComponent + ((128 - colorComponent) / 2);
	}
}
