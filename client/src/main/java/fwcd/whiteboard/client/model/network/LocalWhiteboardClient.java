package fwcd.whiteboard.client.model.network;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.sketch.model.SketchBoardModel;
import fwcd.sketch.model.items.BoardItem;
import fwcd.sketch.model.items.SketchItem;
import fwcd.whiteboard.client.model.convert.FromProtocolItemConverter;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.dispatch.WhiteboardItemVisitor;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.event.UpdateDrawPositionEvent;
import fwcd.whiteboard.protocol.struct.WhiteboardItem;

/**
 * A local whiteboard endpoint that accepts
 * events from the server using a high-level interface.
 */
public class LocalWhiteboardClient implements WhiteboardClient {
	private static final Logger LOG = LoggerFactory.getLogger(LocalWhiteboardClient.class);
	private final ClientNetworkContext context;
	private final SketchBoardModel board;
	private final WhiteboardItemVisitor<SketchItem> converter = new FromProtocolItemConverter();
	
	public LocalWhiteboardClient(SketchBoardModel board, ClientNetworkContext context) {
		this.board = board;
		this.context = context;
	}
	
	@Override
	public void addItems(AddItemsEvent event) {
		withEvent(event, e -> {
			for (WhiteboardItem item : e.getAddedItems()) {
				board.addItem(new BoardItem(item.accept(converter)));
			}
		});
	}
	
	@Override
	public void updateAllItems(UpdateAllItemsEvent event) {
		withEvent(event, e -> {
			board.clear();
			for (WhiteboardItem item : e.getItems()) {
				board.addItem(new BoardItem(item.accept(converter)));
			}
		});
	}
	
	@Override
	public void updateDrawPosition(UpdateDrawPositionEvent event) {
		withEvent(event, e -> {
			// TODO: Display the draw position overlay
		});
	}
	
	@Override
	public void otherEvent(Event event) {
		LOG.info("Received unknown event: {}", event);
	}
	
	private <T extends Event> void withEvent(T event, Consumer<T> handler) {
		if (event.getRequester().getId() != context.getClientId()) {
			context.setSilent(true);
			handler.accept(event);
			context.setSilent(false);
		}
	}
}
