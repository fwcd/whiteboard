package com.fwcd.whiteboard.endpoint.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.fwcd.whiteboard.protocol.Message;
import com.fwcd.whiteboard.protocol.MessageCategory;
import com.fwcd.whiteboard.protocol.event.Event;
import com.fwcd.whiteboard.protocol.event.EventName;
import com.fwcd.whiteboard.protocol.event.UpdateAllItemsEvent;
import com.fwcd.whiteboard.protocol.event.UpdateItemsEvent;
import com.fwcd.whiteboard.protocol.request.Request;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MessageDeserializer implements JsonDeserializer<Message> {
	private final Map<String, Class<? extends Event>> eventClasses = new HashMap<>();
	private final Map<String, Class<? extends Request>> requestClasses = new HashMap<>();
	
	public MessageDeserializer() {
		eventClasses.put(EventName.UPDATE_ALL_ITEMS, UpdateAllItemsEvent.class);
		eventClasses.put(EventName.UPDATE_ITEMS, UpdateItemsEvent.class);
	}
	
	@Override
	public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		try {
			JsonObject message = json.getAsJsonObject();
			String category = message.get("category").getAsString();
			String name = message.get("name").getAsString();
			
			switch (category) {
				case MessageCategory.REQUEST: return context.deserialize(message, requestClasses.get(name));
				case MessageCategory.EVENT: return context.deserialize(message, eventClasses.get(name));
				default: throw new JsonParseException("Invalid message category: " + category);
			}
		} catch (Exception e) {
			throw new JsonParseException("Could not parse " + json + " to a Message", e);
		}
	}
}
