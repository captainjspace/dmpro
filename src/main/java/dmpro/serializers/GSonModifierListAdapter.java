package dmpro.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.Modifier;
import dmpro.modifier.SavingThrowModifier;
import dmpro.modifier.SpellEffectModifier;

public class GSonModifierListAdapter implements JsonSerializer<List<Modifier>>
, JsonDeserializer<List<Modifier>>{

	/**
	 * helper class for serializing and deserializing subclassed collections of generics.
	 */
	@SuppressWarnings("rawtypes")
	private static Map<String, Class> map = new TreeMap<String, Class>();
//	static {
//		map.put("Modifier", Modifier.class);
//		map.put("AbilityModifier", AbilityModifier.class);
//		map.put("SavingThrowModifier", SavingThrowModifier.class);
//		map.put("AttributeModifier", AttributeModifier.class);
//		map.put("SpellEffectModifier", SpellEffectModifier.class);
//	}

	private static final String CLASSNAME = "CLASSNAME";
	private static final String INSTANCE="INSTANCE";

	@Override
	public JsonElement serialize(List<Modifier> src, Type typeOfSrc, JsonSerializationContext context) {
		//List<AbilityModifier> = new ArrayList<AbilityModifier>();
		//JsonObject retVal = new JsonObject();
		//JsonArray retVal = new JsonArray();
		//retVal.addProperty(CLASSNAME, "java.util.ArrayList");
		JsonArray jArray = new JsonArray();
		JsonObject listElement;
		for (Modifier a : src) {
			listElement = new JsonObject();
			String className = a.getClass().getName();
			//			System.out.println(className + ":" + a.toString());
			listElement.addProperty(CLASSNAME,  className);
			JsonElement elem = context.serialize(a);
			listElement.add(INSTANCE, elem);
			jArray.add(listElement);
		}
		//retVal.add(INSTANCE, jArray);
		//return retVal;
		return jArray;
	}

	@Override
	public List<Modifier> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		//		JsonObject jsonObject = json.getAsJsonObject();
		List<Modifier> abilityModifier = new ArrayList<Modifier>();
		JsonArray jArray = null;
		try {
			jArray = json.getAsJsonArray();
		} catch (NullPointerException | IllegalStateException ise) {
			return abilityModifier;
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
			abilityModifier.add(context.deserialize(jsonObject.get(INSTANCE), klass));
		}

		return abilityModifier;
	}
}
