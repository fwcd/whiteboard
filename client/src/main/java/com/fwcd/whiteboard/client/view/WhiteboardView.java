package com.fwcd.whiteboard.client.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.fwcd.fructose.swing.View;
import com.fwcd.sketch.model.BrushProperties;
import com.fwcd.sketch.view.canvas.SketchBoardView;
import com.fwcd.sketch.view.tools.SketchTool;
import com.fwcd.whiteboard.client.model.WhiteboardModel;
import com.fwcd.whiteboard.client.view.core.SideBarView;

public class WhiteboardView implements View {
	private final WhiteboardModel model = new WhiteboardModel();
	private final JComponent component;
	 
	private final SketchBoardView drawBoard;
	private final SideBarView sideBar;
	
	/**
	 * Creates a new local Whiteboard instance.
	 * 
	 * @param version - The version of Whiteboard
	 */
	public WhiteboardView() {
		component = new JPanel();
		component.setBackground(Color.WHITE);
		component.setLayout(new BorderLayout());
		
		drawBoard = new SketchBoardView(model.getBoard());
		component.add(drawBoard.getComponent(), BorderLayout.CENTER);
		
		sideBar = new SideBarView(drawBoard, /* horizontal */ false);
		component.add(sideBar.getComponent(), BorderLayout.WEST);
		
		component.setVisible(true);
	}
	
	public SketchBoardView getDrawBoard() {
		return drawBoard;
	}
	
	public void setSelectedTool(SketchTool tool) {
		drawBoard.selectTool(tool);
		component.repaint();
	}
	
	public SketchTool getSelectedTool() {
		return drawBoard.getSelectedTool();
	}
	
	@Override
	public JComponent getComponent() {
		return component;
	}

	public BrushProperties getBrushProperties() {
		return drawBoard.getBrushProperties();
	}

	public void setBackground(Color color) {
		drawBoard.getComponent().setBackground(color);
		component.repaint();
	}

	public Color getBackground() {
		return drawBoard.getComponent().getBackground();
	}
}
