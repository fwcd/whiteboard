package com.fwcd.whiteboard.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fwcd.sketch.canvas.SketchBoard;
import com.fwcd.sketch.model.BrushProperties;
import com.fwcd.sketch.model.SketchBoardProperty;
import com.fwcd.sketch.tools.SketchTool;
import com.fwcd.whiteboard.WhiteboardMain;
import com.fwcd.whiteboard.ui.ScriptPanel;
import com.fwcd.whiteboard.ui.SidePanel;
import com.fwcd.whiteboard.ui.WMenuBar;

public class WhiteboardApp {
	private JFrame view;
	private JFileChooser fileChooser = new JFileChooser();
	
	private SketchBoardProperty drawBoardProperty;
	private SketchBoard drawBoard;
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
		fileChooser.setFileFilter(new FileNameExtensionFilter("Whiteboard File (.wb)", "wb"));
		
		view = new JFrame("Whiteboard v" + WhiteboardMain.getVersion());
		view.getContentPane().setBackground(Color.WHITE);
		view.setSize(900, 600);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setLayout(new BorderLayout());
		
		drawBoard = new SketchBoard();
		drawBoardProperty = new SketchBoardProperty();
		drawBoardProperty.bind(drawBoard);
		view.add(drawBoard.getView(), BorderLayout.CENTER);
		
		toolBar = new SidePanel(this, false);
		view.add(toolBar.getView(), BorderLayout.WEST);
		
		scriptPanel = new ScriptPanel(this);
		view.add(scriptPanel.getView(), BorderLayout.EAST);
		
		menuBar = new WMenuBar(this);
		view.add(menuBar.getView(), BorderLayout.NORTH);
		
		view.setVisible(true);
	}
	
	public void openDrawBoard() {
		if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			
			if (file != null) {
				drawBoard.load(file);
			}
		}
	}
	
	public void saveDrawBoard() {
		if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			
			if (selectedFile != null) {
				String fileName = selectedFile.getAbsolutePath();
				
				if (!fileName.endsWith(".wb")) {
					fileName += ".wb";
				}
				
				File file = new File(fileName);
				drawBoard.save(file);
			}
		}
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
	
	public JFrame getView() {
		return view;
	}

	public BrushProperties getBrushProperties() {
		return drawBoard.getBrushProperties();
	}

	public boolean isLocal() {
		return isLocal;
	}

	public void setBackground(Color color) {
		drawBoard.setBackground(color);
		view.repaint();
	}

	public Color getBackground() {
		return drawBoard.getBackground();
	}

	public SketchBoard getDrawBoard() {
		return drawBoard;
	}

	public SketchBoardProperty getDrawBoardProperty() {
		return drawBoardProperty;
	}
}
