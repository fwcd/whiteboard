package com.fwcd.whiteboard.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import com.fwcd.fructose.Updatable;
import com.fwcd.fructose.swing.Viewable;
import com.fwcd.fructose.swing.properties.BoolProperty;
import com.fwcd.sketch.canvas.SketchBoard;
import com.fwcd.whiteboard.core.WhiteboardApp;
import com.fwcd.whiteboard.tikz.TikzCanvas;
import com.fwcd.whiteboard.tikz.TikzOutputPanel;

public class ScriptPanel implements Viewable, Updatable {
	private final JToolBar view;
	private BoolProperty visible = new BoolProperty(false);
	
	private TikzOutputPanel tikz;
	
	public ScriptPanel(WhiteboardApp parent) {
		visible.addChangeListener(() -> update());
		
		view = new JToolBar(JToolBar.VERTICAL);
		view.setPreferredSize(new Dimension(300, 600));
		view.setLayout(new GridLayout(2, 1));
		
		tikz = new TikzOutputPanel();
		parent.getDrawBoardProperty().bindObserver(board -> {
			if (view.isVisible()) {
				tikz.setText(getTikz(board));
			}
		});
		view.add(tikz.getView());
		
		view.setVisible(visible.get());
	}
	
	private String getTikz(SketchBoard board) {
		TikzCanvas canvas = new TikzCanvas();
		board.renderTo(canvas.getGraphics());
		return canvas.getOutput();
	}

	@Override
	public JComponent getView() {
		return view;
	}
	
	@Override
	public void update() {
		view.setVisible(visible.get());
	}

	public BoolProperty getVisibility() {
		return visible;
	}
}
