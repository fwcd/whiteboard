package com.fwcd.whiteboard.client.view.utils;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class BlankCursor {
	private static Cursor cursor;
	
	static {
		Image emptyImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		cursor = Toolkit.getDefaultToolkit().createCustomCursor(emptyImage, new Point(0, 0), "Blank Cursor");
	}
	
	public static Cursor get() {
		return cursor;
	}
}
