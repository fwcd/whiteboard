package com.fwcd.whiteboard.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import com.fwcd.fructose.Observable;
import com.fwcd.fructose.Updatable;
import com.fwcd.fructose.swing.View;
import com.fwcd.sketch.view.canvas.SketchBoardView;
import com.fwcd.whiteboard.tikz.TikzCanvas;
import com.fwcd.whiteboard.tikz.TikzOutputPanel;

public class ScriptPanel implements View, Updatable {
	private final JToolBar view;
	private final Observable<Boolean> visible = new Observable<Boolean>(false);
	
	private TikzOutputPanel tikz;
	
	public ScriptPanel(SketchBoardView drawBoard) {
		visible.listen(it -> update());
		
		view = new JToolBar(JToolBar.VERTICAL);
		view.setPreferredSize(new Dimension(300, 600));
		view.setLayout(new GridLayout(2, 1));
		
		tikz = new TikzOutputPanel();
		drawBoard.getModel().getItems().listen(items -> {
			if (view.isVisible()) {
				tikz.setText(getTikz(drawBoard));
			}
		});
		view.add(tikz.getComponent());
		
		view.setVisible(visible.get());
	}
	
	private String getTikz(SketchBoardView drawBoard) {
		TikzCanvas canvas = new TikzCanvas();
		drawBoard.renderTo(canvas.getGraphics());
		return canvas.getOutput();
	}

	@Override
	public JComponent getComponent() {
		return view;
	}
	
	@Override
	public void update() {
		view.setVisible(visible.get());
	}

	public Observable<Boolean> getVisibility() {
		return visible;
	}
}
