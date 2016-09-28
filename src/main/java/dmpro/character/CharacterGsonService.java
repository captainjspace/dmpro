package dmpro.character;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dmpro.GSonModifierListAdapter;
import dmpro.items.GSonItemAdapter;
import dmpro.items.GSonModifierAdapter;
import dmpro.items.Item;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.Modifier;

public class CharacterGsonService {

	
	
	public static Gson getCharacterGson() {
		GsonBuilder builder = new GsonBuilder();
		
		//consider downloading from https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java
//		RuntimeTypeAdapterFactory<AbilityModifier> adapter = 
//                RuntimeTypeAdapterFactory
//               .of(AbilityModifier.class)
//               .registerSubtype(SavingThrowModifier.class);
//		
		Type abilityModifierType = new TypeToken<List<AbilityModifier>>(){}.getType();
		builder.setPrettyPrinting();
		//builder.registerTypeAdapter(CharacterClass.class, new ICharacterClassAdapter());
		builder.registerTypeAdapter(abilityModifierType, new GSonModifierListAdapter());
		builder.registerTypeAdapter(Item.class, new GSonItemAdapter());
		builder.registerTypeAdapter(Modifier.class, new GSonModifierAdapter());
		//builder.registerTypeAdapter(arg0, arg1)
		builder.setExclusionStrategies(new CharacterGsonExclusion());
		return builder.create();
	}
}
