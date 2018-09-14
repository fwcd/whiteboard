package com.fwcd.whiteboard.core;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import com.fwcd.sketch.model.BrushProperties;
import com.fwcd.sketch.model.SketchBoardModel;
import com.fwcd.sketch.view.canvas.SketchBoardView;
import com.fwcd.sketch.view.tools.SketchTool;
import com.fwcd.whiteboard.WhiteboardMain;
import com.fwcd.whiteboard.ui.ScriptPanel;
import com.fwcd.whiteboard.ui.SidePanel;
import com.fwcd.whiteboard.ui.WMenuBar;

public class WhiteboardApp {
	private JFrame view;
	
	private SketchBoardView drawBoard;
	private SidePanel toolBar;
	private WMenuBar menuBar;
	private ScriptPanel scriptPanel;
	
	private boolean isLocal;
	
	/**
	 * Creates a new local Whiteboard instance.
	 * 
	 * @param version - The version of Whiteboard
	 */
	public WhiteboardApp() {
		isLocal = true;
		
		view = new JFrame("Whiteboard v" + WhiteboardMain.getVersion());
		view.getContentPane().setBackground(Color.WHITE);
		view.setSize(900, 600);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setLayout(new BorderLayout());
		
		drawBoard = new SketchBoardView(new SketchBoardModel());
		view.add(drawBoard.getComponent(), BorderLayout.CENTER);
		
		toolBar = new SidePanel(this, false);
		view.add(toolBar.getComponent(), BorderLayout.WEST);
		
		scriptPanel = new ScriptPanel(drawBoard);
		view.add(scriptPanel.getComponent(), BorderLayout.EAST);
		
		menuBar = new WMenuBar(this);
		view.add(menuBar.getComponent(), BorderLayout.NORTH);
		
		view.setVisible(true);
	}
	
	/**
	 * Creates a new remote Whiteboard instance.
	 * 
	 * @param version - The version of Whiteboard
	 * @param host - The IP of the Whiteboard server
	 * @param port - The port of the Whiteboard server
	 */
	public WhiteboardApp(String host, int port) {
		this();
		isLocal = false;
		view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// TODO: Implement connection
	}
	
	public SketchBoardView getDrawBoard() {
		return drawBoard;
	}
	
	public ScriptPanel getScriptPanel() {
		return scriptPanel;
	}
	
	public void setSelectedTool(SketchTool tool) {
		drawBoard.selectTool(tool);
		view.repaint();
	}
	
	public SketchTool getSelectedTool() {
		return drawBoard.getSelectedTool();
	}
	
	public JFrame getComponent() {
		return view;
	}

	public BrushProperties getBrushProperties() {
		return drawBoard.getBrushProperties();
	}

	public boolean isLocal() {
		return isLocal;
	}

	public void setBackground(Color color) {
		drawBoard.getComponent().setBackground(color);
		view.repaint();
	}

	public Color getBackground() {
		return drawBoard.getComponent().getBackground();
	}
}
