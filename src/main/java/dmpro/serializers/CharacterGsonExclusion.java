package dmpro.serializers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class CharacterGsonExclusion implements ExclusionStrategy {

	public boolean shouldSkipClass(Class<?> c) {
		return false;
	}

	public boolean shouldSkipField(FieldAttributes f) {
//		System.out.println("MATCH: " + f.getDeclaredClass().getName() + 
//				":" + f.getDeclaringClass().getName() +
//				":" + f.getName());
    	
        if (f.getDeclaringClass() == dmpro.modifier.AttributeModifier.class && 
				f.getName().equals("attributeToModify")) {
        	//System.out.println("MATCH");
        	return true;
        }
        else 
        	return false;
	}
}
