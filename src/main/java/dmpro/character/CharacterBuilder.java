package dmpro.character;

import java.util.List;
import java.util.Map;

import dmpro.attributes.Attribute;
import dmpro.character.classes.CharacterClass;
import dmpro.character.race.Race;

/**
 * Interface for CharacterBuilder.
 * Building a character requires a step by step execution 
 * <li>
 *   <li> Generate or Acquire Basic Character Personal Information </li>
 *   <li> Roll or Enter Character Attributes </li>
 *   <li> Generate or Select Character Race 
 *        <p>Requires review of race / attribute limitations
 *   </li>
 *   <li> Generate or Acquire additional personal characteristics only acquirable after race selection</li>
 *   <li> Generate or Select Character Class / Classes
 *        <p> Requires review of both attributes and race limitations
 *   </li>
 *   <li> Generate or acquire character history - optional </li>
 *   <li> ? Modification - changing of character class (i.e., Bard construction )</li>
 *  </li>
 *  <p>
 *  
 * @author joshualandman
 * @version 0.1;
 *
 */
public interface CharacterBuilder {
	void buildCharacterInitialize();
	void buildCharacterPersonalInformation(String sex, String name, String prefix, String firstName,
			String lastName, String title, List<String> suffixes);
	void buildCharacterAttributes(Map<String,Attribute> attributes);
	void buildCharacterRace(Race race);
	void buildCharacterRacePersonalInformation(int age, int height, int weight);
	void buildCharacterClass(List<CharacterClass> characterClass);
	void buildCharacterClassInformation();
	void buildCharacterHistory();
	Character getCharacter();
	
}
