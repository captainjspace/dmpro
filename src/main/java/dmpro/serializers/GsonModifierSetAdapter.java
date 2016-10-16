/**
 * 
 */
package dmpro.serializers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dmpro.modifier.Modifier;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 11, 2016
 * 
 * - just reuse the list adapter.
 */
public class GsonModifierSetAdapter implements JsonSerializer<Set<Modifier>>, JsonDeserializer<Set<Modifier>> {

	/* (non-Javadoc)
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	@Override
	public Set<Modifier> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		GSonModifierListAdapter listAdapter = new GSonModifierListAdapter();
		List<Modifier> list = listAdapter.deserialize(json, typeOfT, context);
		return new HashSet<Modifier>(list);
	}

	/* (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	@Override
	public JsonElement serialize(Set<Modifier> src, Type typeOfSrc, JsonSerializationContext context) {
		// TODO Auto-generated method stub
		GSonModifierListAdapter listAdapter = new GSonModifierListAdapter();
		return listAdapter.serialize(new ArrayList<Modifier>(src), typeOfSrc, context);
	}
	
	
	

}
