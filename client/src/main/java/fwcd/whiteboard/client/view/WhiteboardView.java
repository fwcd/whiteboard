package fwcd.whiteboard.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fwcd.fructose.Option;
import fwcd.fructose.function.Subscription;
import fwcd.fructose.swing.MouseHandler;
import fwcd.fructose.swing.Viewable;
import fwcd.sketch.model.BrushProperties;
import fwcd.sketch.model.items.SketchItem;
import fwcd.sketch.view.canvas.SketchBoardView;
import fwcd.whiteboard.client.model.WhiteboardModel;
import fwcd.whiteboard.client.model.network.ServerConnectionManager;
import fwcd.whiteboard.client.view.core.SideBarView;
import fwcd.whiteboard.client.view.overlay.BoardOverlayView;
import fwcd.whiteboard.protocol.request.AddItemPartsRequest;
import fwcd.whiteboard.protocol.request.DisposePartsRequest;
import fwcd.whiteboard.protocol.request.UpdateDrawPositionRequest;
import fwcd.whiteboard.protocol.struct.Vec2;

public class WhiteboardView implements Viewable {
	private final WhiteboardModel model = new WhiteboardModel();
	private final JComponent component;
	 
	private final SketchBoardView drawBoard;
	private final SideBarView sideBar;
	
	private Option<Subscription> partToolSubscription = Option.empty();
	private Option<Subscription> completionToolSubscription = Option.empty();
	
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
		
		BoardOverlayView overlay = new BoardOverlayView(model.getOverlay());
		drawBoard.pushOverlay(overlay);
		overlay.listenForChanges(drawBoard.getComponent()::repaint);
		component.add(drawBoard.getComponent(), BorderLayout.CENTER);
		
		sideBar = new SideBarView(drawBoard, /* horizontal */ false, model);
		component.add(sideBar.getComponent(), BorderLayout.WEST);
		
		registerMouseListeners();
		registerToolListeners();
		
		component.setVisible(true);
	}
	
	private void registerMouseListeners() {
		new MouseHandler() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				ServerConnectionManager manager = model.getConnectionManager();
				manager.ifConnected(wb -> wb.updateDrawPosition(new UpdateDrawPositionRequest(manager.getClientId(), new Vec2(e.getX(), e.getY()))));
			}
		}.connect(drawBoard.getComponent());
	}
	
	private void registerToolListeners() {
		drawBoard.getSelectedTool().listenAndFire(tool -> {
			ServerConnectionManager manager = model.getConnectionManager();
			Consumer<? super SketchItem> partListener = part -> manager
				.ifConnected(wb -> wb.addParts(new AddItemPartsRequest(manager.getClientId(), manager.toProtocolItem(part).stream().collect(Collectors.toList()))));
			Consumer<? super SketchItem> completionListener = part -> manager
				.ifConnected(wb -> wb.disposeParts(new DisposePartsRequest(manager.getClientId())));
			
			completionToolSubscription.ifPresent(Subscription::unsubscribe);
			completionToolSubscription = tool.subscribeToCompletions(completionListener);
			
			partToolSubscription.ifPresent(Subscription::unsubscribe);
			partToolSubscription = tool.subscribeToAddedParts(partListener);
		});
	}
	
	public SketchBoardView getDrawBoard() {
		return drawBoard;
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
