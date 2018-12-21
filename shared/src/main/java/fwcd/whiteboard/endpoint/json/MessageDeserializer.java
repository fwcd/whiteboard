package fwcd.whiteboard.endpoint.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import fwcd.whiteboard.protocol.Message;
import fwcd.whiteboard.protocol.MessageCategory;
import fwcd.whiteboard.protocol.event.AddItemsEvent;
import fwcd.whiteboard.protocol.event.Event;
import fwcd.whiteboard.protocol.event.EventName;
import fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import fwcd.whiteboard.protocol.request.AddItemsRequest;
import fwcd.whiteboard.protocol.request.GetAllItemsRequest;
import fwcd.whiteboard.protocol.request.Request;
import fwcd.whiteboard.protocol.request.RequestName;
import fwcd.whiteboard.protocol.request.SetAllItemsRequest;

public class MessageDeserializer implements JsonDeserializer<Message> {
	private final Map<String, Class<? extends Event>> eventClasses = new HashMap<>();
	private final Map<String, Class<? extends Request>> requestClasses = new HashMap<>();

	public MessageDeserializer() {
		eventClasses.put(EventName.UPDATE_ALL_ITEMS, UpdateAllItemsEvent.class);
		eventClasses.put(EventName.ADD_ITEMS, AddItemsEvent.class);

		requestClasses.put(RequestName.GET_ALL_ITEMS, GetAllItemsRequest.class);
		requestClasses.put(RequestName.SET_ALL_ITEMS, SetAllItemsRequest.class);
		requestClasses.put(RequestName.ADD_ITEMS, AddItemsRequest.class);
	}
	
	@Override
	public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		try {
			JsonObject message = json.getAsJsonObject();
			String category = message.get("category").getAsString();
			String name = message.get("name").getAsString();
			Class<? extends Message> resultType;
			
			switch (category) {
				case MessageCategory.REQUEST:
					if (!requestClasses.containsKey(name)) {
						throw new JsonParseException("Unknown message request: " + name);
					}
					resultType = requestClasses.get(name);
					break;
				case MessageCategory.EVENT:
					if (!eventClasses.containsKey(name)) {
						throw new JsonParseException("Unknown message event: " + name);
					}
					resultType = eventClasses.get(name);
					break;
				default:
					throw new JsonParseException("Invalid message category: " + category);
			}
			
			return context.deserialize(message, resultType);
		} catch (Exception e) {
			throw new JsonParseException("Could not parse " + json + " to a Message", e);
		}
	}
}
