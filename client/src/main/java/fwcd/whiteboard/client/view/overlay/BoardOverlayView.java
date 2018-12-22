package fwcd.whiteboard.client.view.overlay;

import java.awt.Dimension;
import java.awt.Graphics2D;

import fwcd.fructose.ListenerList;
import fwcd.sketch.model.items.BoardItemStack;
import fwcd.sketch.view.canvas.ItemRenderer;
import fwcd.sketch.view.utils.ListenableRenderable;
import fwcd.whiteboard.client.model.overlay.BoardOverlayModel;

public class BoardOverlayView implements ListenableRenderable {
	private final BoardOverlayModel model;
	private final ListenerList listeners = new ListenerList();
	
	public BoardOverlayView(BoardOverlayModel model) {
		this.model = model;
		model.getItemListeners().add(items -> listeners.fire());
	}
	
	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		synchronized (model) {
			for (BoardItemStack item : model.getItems()) {
				item.accept(new ItemRenderer(g2d));
			}
		}
	}
	
	@Override
	public void listenForChanges(Runnable listener) {
		listeners.add(listener);
	}
	
	@Override
	public void unlistenForChanges(Runnable listener) {
		listeners.remove(listener);
	}
	
	public ListenerList getListeners() {
		return listeners;
	}
}
