package fwcd.whiteboard.protocol.dispatch;

import fwcd.whiteboard.protocol.struct.LineItem;
import fwcd.whiteboard.protocol.struct.PathItem;
import fwcd.whiteboard.protocol.struct.RectItem;
import fwcd.whiteboard.protocol.struct.TextItem;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

public interface WhiteboardItemVisitor<T> {
	T visitItem(WhiteboardItem item);
	
	default T visitLine(LineItem line) { return visitItem(line); }
	
	default T visitPath(PathItem path) { return visitItem(path); }
	
	default T visitRect(RectItem rect) { return visitItem(rect); }
	
	default T visitText(TextItem text) { return visitItem(text); }
}
