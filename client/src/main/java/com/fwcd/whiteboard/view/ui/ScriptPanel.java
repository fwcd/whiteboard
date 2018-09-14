package com.fwcd.whiteboard.view.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import com.fwcd.fructose.Observable;
import com.fwcd.fructose.Updatable;
import com.fwcd.fructose.swing.View;
import com.fwcd.sketch.view.canvas.SketchBoardView;
import com.fwcd.whiteboard.view.tikz.TikzCanvas;
import com.fwcd.whiteboard.view.tikz.TikzOutputPanel;

public class ScriptPanel implements View, Updatable {
	private final JToolBar component;
	private final Observable<Boolean> visible = new Observable<Boolean>(false);
	
	private TikzOutputPanel tikz;
	
	public ScriptPanel(SketchBoardView drawBoard) {
		visible.listen(it -> update());
		
		component = new JToolBar(JToolBar.VERTICAL);
		component.setPreferredSize(new Dimension(300, 600));
		component.setLayout(new GridLayout(2, 1));
		
		tikz = new TikzOutputPanel();
		drawBoard.getModel().getItems().listen(items -> {
			if (component.isVisible()) {
				tikz.setText(getTikz(drawBoard));
			}
		});
		component.add(tikz.getComponent());
		
		component.setVisible(visible.get());
	}
	
	private String getTikz(SketchBoardView drawBoard) {
		TikzCanvas canvas = new TikzCanvas();
		drawBoard.renderTo(canvas.getGraphics());
		return canvas.getOutput();
	}

	@Override
	public JComponent getComponent() {
		return component;
	}
	
	@Override
	public void update() {
		component.setVisible(visible.get());
	}

	public Observable<Boolean> getVisibility() {
		return visible;
	}
}
