package fwcd.whiteboard.client.model.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fwcd.fructose.Option;
import fwcd.fructose.geometry.LineSeg2D;
import fwcd.fructose.geometry.Rectangle2D;
import fwcd.fructose.geometry.Vector2D;
import fwcd.sketch.model.items.ColoredLine;
import fwcd.sketch.model.items.ColoredPath;
import fwcd.sketch.model.items.ColoredRect;
import fwcd.sketch.model.items.ColoredText;
import fwcd.sketch.model.items.ImageItem;
import fwcd.sketch.model.items.SketchItemVisitor;
import fwcd.whiteboard.protocol.struct.LineItem;
import fwcd.whiteboard.protocol.struct.PathItem;
import fwcd.whiteboard.protocol.struct.RectItem;
import fwcd.whiteboard.protocol.struct.TextItem;
import fwcd.whiteboard.protocol.struct.Vec2;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public class ToProtocolItemConverter implements SketchItemVisitor {
	private Option<WhiteboardItem> result = Option.empty();
	
	@Override
	public void visitImage(ImageItem image) {
		// TODO: Support images
	}
	
	@Override
	public void visitLine(ColoredLine line) {
		LineSeg2D seg = line.getLine();
		result = Option.of(new LineItem(vecOf(seg.getStart()), vecOf(seg.getEnd()), wbColorOf(line.getColor()), line.getThickness()));
	}
	
	@Override
	public void visitPath(ColoredPath path) {
		List<LineSeg2D> lines = path.getLines();
		List<Vec2> vertices = new ArrayList<>(lines.size() + 1);
		if (lines.size() > 0) {
			vertices.add(vecOf(lines.get(0).getStart()));
		}
		for (LineSeg2D line : lines) {
			vertices.add(vecOf(line.getEnd()));
		}
		result = Option.of(new PathItem(vertices, wbColorOf(path.getColor()), path.getThickness()));
	}
	
	@Override
	public void visitRect(ColoredRect rect) {
		Rectangle2D r2d = rect.getRect();
		result = Option.of(new RectItem(vecOf(r2d.getTopLeft()), r2d.width(), r2d.height(), wbColorOf(rect.getColor()), rect.getThickness()));
	}
	
	@Override
	public void visitText(ColoredText text) {
		result = Option.of(new TextItem(Arrays.asList(text.getLines()), vecOf(text.getPos()), wbColorOf(text.getColor()), text.getSize()));
	}
	
	public Option<WhiteboardItem> getResult() {
		return result;
	}
	
	private Vec2 vecOf(Vector2D vector) {
		return new Vec2(vector.getX(), vector.getY());
	}
	
	private fwcd.whiteboard.protocol.struct.Color wbColorOf(java.awt.Color awtColor) {
		double red = (double) awtColor.getRed() / 255.0;
		double green = (double) awtColor.getGreen() / 255.0;
		double blue = (double) awtColor.getBlue() / 255.0;
		double alpha = (double) awtColor.getAlpha() / 255.0;
		return new fwcd.whiteboard.protocol.struct.Color(red, green, blue, alpha);
	}
}
