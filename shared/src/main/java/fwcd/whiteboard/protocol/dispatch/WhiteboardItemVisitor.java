package fwcd.whiteboard.protocol.dispatch;

import fwcd.whiteboard.protocol.struct.LineItem;
import fwcd.whiteboard.protocol.struct.PathItem;
import fwcd.whiteboard.protocol.struct.RectItem;
import fwcd.whiteboard.protocol.struct.TextItem;

public interface WhiteboardItemVisitor {
	default void visitLine(LineItem line) {}
	
	default void visitPath(PathItem path) {}
	
	default void visitRect(RectItem rect) {}
	
	default void visitText(TextItem rect) {}
}
