/**
 * 
 */
package dmpro.character.classes;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dmpro.core.ReferenceDataSet;
import dmpro.serializers.CharacterGsonService;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 21, 2016
 */
public class CharacterClassTests {
	
	
	public static void main (String[] args) {
		ReferenceDataSet rds = new ReferenceDataSet();
		rds.run();
		
		Map<CharacterClassType, CharacterClass> classMap = new HashMap<CharacterClassType, CharacterClass>();
		
		classMap = rds.getClasses();
		
		Gson gson = CharacterGsonService.getCharacterGson();
		
		
		for (CharacterClass c : classMap.values()) {
			System.out.println(c.characterClassType.name());
			try {
			System.out.println(gson.toJson(c));
			} catch (Exception e) {
				System.err.printf("Problem with class: %s", c.characterClassType.name());
			}
		}
		
		
	}

}
