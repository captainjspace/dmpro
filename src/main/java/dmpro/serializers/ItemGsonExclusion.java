package dmpro.serializers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ItemGsonExclusion implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> c) {
			return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes c) {
		if (c.getDeclaredClass() == java.util.regex.Matcher.class)
			return true;
		else if (c.getDeclaredClass() == java.util.Random.class)
			return true;
		// TODO Auto-generated method stub
		return false;
	}

}
