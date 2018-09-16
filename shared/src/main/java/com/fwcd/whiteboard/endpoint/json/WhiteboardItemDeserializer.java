package com.fwcd.whiteboard.endpoint.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.fwcd.whiteboard.protocol.struct.WhiteboardItem;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class WhiteboardItemDeserializer implements JsonDeserializer<WhiteboardItem> {
	private final Map<String, Class<? extends WhiteboardItem>> itemClasses = new HashMap<>();
	
	public WhiteboardItemDeserializer() {
		
	}
	
	@Override
	public WhiteboardItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
		try {
			JsonObject item = json.getAsJsonObject();
			String name = item.get("name").getAsString();
			return context.deserialize(item, itemClasses.get(name));
		} catch (Exception e) {
			throw new JsonParseException("Could not parse " + json + " to a WhiteboardItem", e);
		}
	}
}
