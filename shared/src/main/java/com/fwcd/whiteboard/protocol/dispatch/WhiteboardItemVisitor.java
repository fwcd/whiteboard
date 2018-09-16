package com.fwcd.whiteboard.protocol.dispatch;

import com.fwcd.whiteboard.protocol.struct.LineItem;
import com.fwcd.whiteboard.protocol.struct.PathItem;
import com.fwcd.whiteboard.protocol.struct.RectItem;
import com.fwcd.whiteboard.protocol.struct.TextItem;

public interface WhiteboardItemVisitor {
	default void visitLine(LineItem line) {}
	
	default void visitPath(PathItem path) {}
	
	default void visitRect(RectItem rect) {}
	
	default void visitText(TextItem rect) {}
}
