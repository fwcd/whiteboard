package fwcd.whiteboard.client.model.overlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import fwcd.fructose.EventListenerList;
import fwcd.sketch.model.items.BoardItemStack;
import fwcd.sketch.model.items.SketchItem;

public class BoardOverlayModel {
	private final List<BoardItemStack> items = new ArrayList<>();
	private final EventListenerList<List<BoardItemStack>> itemListeners = new EventListenerList<>();
	private final Map<BoardItemStack, Consumer<Iterable<SketchItem>>> innerItemListeners = new HashMap<>();
	
	public void addItem(BoardItemStack item) {
		items.add(item);
		
		Consumer<Iterable<SketchItem>> innerListener = it -> fireChange();
		item.listen(innerListener);
		innerItemListeners.put(item, innerListener);
		
		fireChange();
	}
	
	public void removeItem(BoardItemStack item) {
		items.remove(item);
		item.unlisten(innerItemListeners.get(item));
		
		fireChange();
	}
	
	public List<? extends BoardItemStack> getItems() { return items; }
	
	public EventListenerList<List<BoardItemStack>> getItemListeners() { return itemListeners; }
	
	private void fireChange() { itemListeners.fire(items); }
	
	public void dispose() {
		for (BoardItemStack item : items) {
			if (innerItemListeners.containsKey(item)) {
				item.unlisten(innerItemListeners.get(item));
			}
		}
	}
}
