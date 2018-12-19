package fwcd.whiteboard.client.model.convert;

import java.util.ArrayList;
import java.util.List;

import fwcd.fructose.geometry.LineSeg2D;
import fwcd.fructose.geometry.Rectangle2D;
import fwcd.fructose.geometry.Vector2D;
import fwcd.sketch.model.items.ColoredLine;
import fwcd.sketch.model.items.ColoredPath;
import fwcd.sketch.model.items.ColoredRect;
import fwcd.sketch.model.items.ColoredText;
import fwcd.sketch.model.items.SketchItem;
import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;
import fwcd.whiteboard.protocol.struct.LineItem;
import fwcd.whiteboard.protocol.struct.PathItem;
import fwcd.whiteboard.protocol.struct.RectItem;
import fwcd.whiteboard.protocol.struct.TextItem;
import fwcd.whiteboard.protocol.struct.Vec2;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * Converts protocol items to Sketch items.
 */
public class FromProtocolItemConverter implements WhiteboardItemVisitor<SketchItem> {
	@Override
	public SketchItem visitItem(WhiteboardItem item) {
		throw new IllegalArgumentException(
			"Did not recognize WhiteboardItem: " + item.getName() + ", perhaps it needs to be added to ProtocolItemAdapter?"
		);
	}
	
	@Override
	public SketchItem visitLine(LineItem line) {
		LineSeg2D line2d = new LineSeg2D(vectorOf(line.getStart()), vectorOf(line.getEnd()));
		return new ColoredLine(line2d, awtColorOf(line.getColor()), (float) line.getThickness());
	}
	
	@Override
	public SketchItem visitPath(PathItem path) {
		List<Vec2> vertices = path.getVertices();
		List<LineSeg2D> lines = new ArrayList<>(vertices.size());
		Vec2 last = null;
		for (Vec2 vertex : vertices) {
			if (last != null) {
				lines.add(new LineSeg2D(vectorOf(last), vectorOf(vertex)));
			}
			last = vertex;
		}
		return new ColoredPath(lines, awtColorOf(path.getColor()), (float) path.getThickness());
	}
	
	@Override
	public SketchItem visitRect(RectItem rect) {
		Rectangle2D rect2d = new Rectangle2D(vectorOf(rect.getTopLeft()), rect.getWidth(), rect.getHeight());
		return new ColoredRect(rect2d, awtColorOf(rect.getColor()), (float) rect.getThickness());
	}
	
	@Override
	public SketchItem visitText(TextItem text) {
		String[] lines = text.getText().stream().toArray(String[]::new);
		return new ColoredText(lines, awtColorOf(text.getColor()), (float) text.getSize(), vectorOf(text.getPos()));
	}
	
	private Vector2D vectorOf(Vec2 vec) {
		return new Vector2D(vec.getX(), vec.getY());
	}
	
	private java.awt.Color awtColorOf(fwcd.whiteboard.protocol.struct.Color color) {
		return new java.awt.Color((float) color.getR(), (float) color.getG(), (float) color.getB(), (float) color.getA());
	}
}
