package dmpro.world;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.google.gson.reflect.TypeToken;

import dmpro.modifier.AbilityModifier;
import dmpro.modifier.Modifier;
import dmpro.serializers.GSonModifierListAdapter;
import dmpro.world.Location;

//TODO - this does not work - need to get it sorted - also need to add deserialize

public class GsonLocationAdapter implements JsonSerializer<Map<Integer,Location>>, JsonDeserializer<Map<Integer,Location>> {
	private static final String CLASSNAME = "CLASSNAME";
	private static final String INSTANCE="INSTANCE";
	
	@Override
	public JsonElement serialize(Map<Integer,Location> src, Type typeOfSrc, JsonSerializationContext context) {
		//List<JsonObject> retValueArray = new ArrayList<JsonObject>();
		//String className = src.getClass().getName();
		//JsonObject retValue = new JsonObject();
		JsonObject locations = new JsonObject();
		//locations.add(property, value);
		JsonArray locationArray = new JsonArray();
		for (Entry<Integer,Location> e: src.entrySet()) {
			JsonObject location = new JsonObject();
			
			location.addProperty("uniqueId", e.getValue().getUniqueId());
			location.addProperty("locationId", e.getValue().getLocationId());
			location.addProperty("locationName", e.getValue().getLocationName());
			location.addProperty("description", e.getValue().getDescription());
			location.addProperty("latitude", e.getValue().getLatitude());
			location.addProperty("longitude", e.getValue().getLongitude());
			location.addProperty("altitude", e.getValue().getAltitude());
			location.addProperty("area", e.getValue().getArea());
			
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
			locationArray.add(location);
			//JsonElement elem = context.serialize(e.getValue().getLocations(), typeOfSrc);
			//retValue.add("locations", elem);
			//.getLocationName());
			//
			//retValueArray.add(retValue);
		}
		locations.add("locations", locationArray);
//		for (int j = retValueArray.size() - 1 ; j>1; j--) {
//			retValueArray.get(j-1).add("location", retValueArray.get(j).getAsJsonObject());
//			System.out.format("added %d to %d\n", j, j-1 );
//		}
		//return retValueArray.get(1).getAsJsonObject();
		
		return locations;
	}


	@Override
	public Map<Integer,Location> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Map<Integer,Location> locationMap = new HashMap<Integer,Location>();
		JsonObject jsonObject = json.getAsJsonObject();
		Location location = new Location();
		System.out.println( jsonObject.toString());
		JsonArray locationsArray = new JsonArray();
		locationsArray = jsonObject.getAsJsonArray("locations");
		System.out.format("Array Size : %d\n", locationsArray.size());
		for (JsonElement element : locationsArray) {
			Location child = new Location();
			JsonObject childLocation = element.getAsJsonObject();
		
			child.setUniqueId(childLocation.get("uniqueId").getAsString());
			child.setLocationId(childLocation.get("locationId").getAsInt());
			child.setLocationName(childLocation.get("locationName").getAsString());
			child.setLatitude(childLocation.get("latitude").getAsDouble());
			child.setLongitude(childLocation.get("longitude").getAsDouble());
			child.setAltitude(childLocation.get("altitude").getAsDouble());
			child.setArea(childLocation.get("area").getAsDouble());
			if ( childLocation.get("description") != null )
				child.setDescription(childLocation.get("description").getAsString());

			
			Type modifierType = new TypeToken<List<Modifier>>(){}.getType();
			GSonModifierListAdapter gsonModifier = new GSonModifierListAdapter();
			List<Modifier> modifiers = gsonModifier.deserialize(childLocation.get("enviromentModifiers"), modifierType, context);
			//List<Modifier> modifiers = context.deserialize(childLocation.get("enviromentModifiers"), modifierType);
			child.setEnvironmentModifier(modifiers);
			locationMap.put(child.getLocationId(), child);
		}
		//locationMap.put(location.getLocationId(), location);
		return locationMap;
	}
	
		
	

}
