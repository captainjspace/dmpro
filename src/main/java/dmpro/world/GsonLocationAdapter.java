package dmpro.world;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dmpro.modifier.Modifier;
import dmpro.world.Location;

//TODO - this does not work - need to get it sorted - also need to add deserialize

public class GsonLocationAdapter implements JsonSerializer<Map<Integer,Location>>, JsonDeserializer<Location> {
	private static final String CLASSNAME = "CLASSNAME";
	private static final String INSTANCE="INSTANCE";
	
	@Override
	public JsonElement serialize(Map<Integer,Location> src, Type typeOfSrc, JsonSerializationContext context) {
		List<JsonObject> retValueArray = new ArrayList<JsonObject>();
		String className = src.getClass().getName();
		JsonObject retValue = new JsonObject();
		for (Entry<Integer,Location> e: src.entrySet()) {
			JsonObject location = new JsonObject();
			location.addProperty("locationId", e.getValue().getLocationId());
			location.addProperty("locationName", e.getValue().getLocationName());
			JsonArray jsonArray = new JsonArray();
			for (Modifier a : e.getValue().getEnvironmentModifiers()) {
				JsonObject listElement = new JsonObject();
				String modClassName = a.getClass().getName();
				//			System.out.println(className + ":" + a.toString());
				listElement.addProperty(CLASSNAME,  modClassName);
				JsonElement elem = context.serialize(a);
				listElement.add(INSTANCE, elem);
				jsonArray.add(listElement);
			}
			location.add("environmentModifiers", jsonArray);
			retValue.add("location" + e.getKey().toString(), location);
			//JsonElement elem = context.serialize(e.getValue().getLocations(), typeOfSrc);
			//retValue.add("locations", elem);
			//.getLocationName());
			//
			//retValueArray.add(retValue);
		}
//		for (int j = retValueArray.size() - 1 ; j>1; j--) {
//			retValueArray.get(j-1).add("location", retValueArray.get(j).getAsJsonObject());
//			System.out.format("added %d to %d\n", j, j-1 );
//		}
		//return retValueArray.get(1).getAsJsonObject();
		return retValue;
	}
	
	@Override
	public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		String className = prim.getAsString();
		
		Class<?> klass = null;
		try {
			klass = Class.forName(className);
		} catch ( ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException (e.getMessage());
		}
		return context.deserialize(jsonObject.get(INSTANCE), klass);
	}
	
		
	

}
