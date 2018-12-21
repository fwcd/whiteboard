package fwcd.whiteboard.client.model.overlay;

import java.util.ArrayList;
import java.util.List;

import fwcd.fructose.EventListenerList;
import fwcd.sketch.model.items.SketchItem;

public class BoardOverlayModel {
	private final List<SketchItem> items = new ArrayList<>();
	private final EventListenerList<List<SketchItem>> itemListeners = new EventListenerList<>();
	
	public void addItem(SketchItem item) {
		items.add(item);
		fireChange();
	}
	
	public void removeItem(SketchItem item) {
		items.remove(item);
		fireChange();
	}
	
	public void replaceItem(SketchItem oldItem, SketchItem newItem) {
		int index = items.indexOf(oldItem);
		if (index >= 0) {
			items.set(index, newItem);
		} else {
			items.add(newItem);
		}
		fireChange();
	}
	
	public List<? extends SketchItem> getItems() { return items; }
	
	public EventListenerList<List<SketchItem>> getItemListeners() { return itemListeners; }
	
	private void fireChange() { itemListeners.fire(items); }
}
