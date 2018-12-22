package fwcd.whiteboard.client.model.network;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fwcd.fructose.StreamUtils;
import fwcd.fructose.geometry.Rectangle2D;
import fwcd.sketch.model.SketchBoardModel;
import fwcd.sketch.model.items.BoardItemStack;
import fwcd.sketch.model.items.ColoredRect;
import fwcd.sketch.model.items.ColoredText;
import fwcd.sketch.model.items.CompositeItem;
import fwcd.sketch.model.items.SketchItem;
import fwcd.whiteboard.client.model.convert.FromProtocolItemConverter;
import fwcd.whiteboard.client.model.overlay.BoardOverlayModel;
import fwcd.whiteboard.client.model.overlay.ClientOverlays;
import fwcd.whiteboard.protocol.dispatch.WhiteboardClient;
import fwcd.whiteboard.protocol.event.AddItemPartsEvent;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.DisposePartsEvent;
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
	private final BoardOverlayModel overlayModel;
	private final Map<Long, ClientOverlays> clientOverlays = new HashMap<>();
	private final FromProtocolItemConverter converter = new FromProtocolItemConverter();
	
	public LocalWhiteboardClient(SketchBoardModel board, BoardOverlayModel overlay, ClientNetworkContext context) {
		this.board = board;
		this.overlayModel = overlay;
		this.context = context;
	}
	
	@Override
	public void addItems(AddItemsEvent event) {
		withEvent(event, e -> {
			for (WhiteboardItem item : e.getAddedItems()) {
				board.addItem(new BoardItemStack(item.accept(converter)));
			}
		});
	}
	
	@Override
	public void updateAllItems(UpdateAllItemsEvent event) {
		withEvent(event, e -> {
			board.clear();
			for (WhiteboardItem item : e.getItems()) {
				board.addItem(new BoardItemStack(item.accept(converter)));
			}
		});
	}
	
	@Override
	public void updateDrawPosition(UpdateDrawPositionEvent event) {
		withEvent(event, e -> {
			SketchItem newItem = e.getDrawPos()
				.map(converter::vectorOf)
				.map(pos -> new CompositeItem(
					new ColoredRect(new Rectangle2D(pos, 4, 4), Color.BLUE, 2),
					new ColoredText(e.getRequester().getName(), Color.BLUE, 2, pos.add(10, 0))
				))
				.orElseNull();
			clientOverlaysFor(event.getRequester().getId())
				.getDrawPosition()
				.set(newItem);
		});
	}
	
	@Override
	public void addParts(AddItemPartsEvent event) {
		for (WhiteboardItem part : event.getAddedParts()) {
			clientOverlaysFor(event.getRequester().getId())
				.getUnfinishedItem()
				.push(part.accept(converter));
		}
	}
	
	@Override
	public void disposeParts(DisposePartsEvent event) {
		BoardItemStack stack = clientOverlaysFor(event.getRequester().getId()).getUnfinishedItem();
		SketchItem composite = new CompositeItem(StreamUtils.stream(stack.getStack()).collect(Collectors.toList()));
		board.addItem(new BoardItemStack(composite));
		stack.clear();
	}
	
	@Override
	public void otherEvent(Event event) {
		LOG.info("Received unknown event: {}", event);
	}
	
	private ClientOverlays clientOverlaysFor(long clientId) {
		ClientOverlays cs = clientOverlays.get(clientId);
		if (cs == null) {
			cs = new ClientOverlays();
			cs.addTo(overlayModel);
			clientOverlays.put(clientId, cs);
		}
		return cs;
	}
	
	private <T extends Event> void withEvent(T event, Consumer<T> handler) {
		if (event.getRequester().getId() != context.getClientId()) {
			context.setSilent(true);
			handler.accept(event);
			context.setSilent(false);
		}
	}
}
