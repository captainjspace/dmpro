package dmpro.serializers;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dmpro.modifier.Modifier;

public class GSonModifierAdapter  implements JsonSerializer<Modifier>, JsonDeserializer<Modifier> {
	private static final String CLASSNAME = "CLASSNAME";
	private static final String INSTANCE="INSTANCE";
	@Override
	public JsonElement serialize(Modifier src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add(CLASSNAME, new JsonPrimitive(src.getClass().getName()));
		result.add(INSTANCE, context.serialize(src, src.getClass())); 
		return result;
	}


	@Override
	public Modifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
		
		String className = prim.getAsString();
		JsonElement element = jsonObject.get(INSTANCE);

		Class<?> klass = null;
		try {
			klass = Class.forName(className);
		} catch ( ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException (e.getMessage());
		}
		return context.deserialize(element,klass);
	}
}