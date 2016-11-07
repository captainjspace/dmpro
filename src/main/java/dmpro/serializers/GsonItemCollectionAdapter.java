/**
 * 
 */
package dmpro.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dmpro.items.Item;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 7, 2016
 */
public class GsonItemCollectionAdapter implements JsonDeserializer<Collection<Item>>, 
JsonSerializer<Collection<Item>> {
	private static final String CLASSNAME = "CLASSNAME";
	private static final String INSTANCE="INSTANCE";

	@Override
	public JsonElement serialize(Collection<Item> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonArray jArray = new JsonArray();
		JsonObject listElement;
		for (Item a : src) {
			listElement = new JsonObject();
			String className = a.getClass().getName();
			listElement.addProperty(CLASSNAME,  className);
			JsonElement elem = context.serialize(a);
			listElement.add(INSTANCE, elem);
			jArray.add(listElement);
		}

		return jArray;
	}

	@Override
	public Collection<Item> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		List<Item> items = new ArrayList<Item>();
		JsonArray jArray = null;
		try {
			jArray = json.getAsJsonArray();
		} catch (NullPointerException | IllegalStateException ise) {
			ise.printStackTrace();
		}

		for ( JsonElement listElement : jArray ) {
			JsonObject jsonObject = listElement.getAsJsonObject();
			JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
			String className = prim.getAsString();

			Class<?> klass = null;
			try {
				klass = Class.forName(className);
			} catch ( ClassNotFoundException e) {
				e.printStackTrace();
				throw new JsonParseException (e.getMessage());
			}
			items.add(context.deserialize(jsonObject.get(INSTANCE), klass));
		}

		return items;
	}
}


