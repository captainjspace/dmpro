/**
 * 
 */
package dmpro.character;

import java.util.List;
import java.util.Map;

import dmpro.attributes.Attribute;
import dmpro.character.classes.CharacterClass;
import dmpro.character.race.Race;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 24, 2016
 */
public class RestCharacterBuilder implements CharacterBuilder {

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterInitialize()
	 */
	@Override
	public void buildCharacterInitialize() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterPersonalInformation(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void buildCharacterPersonalInformation(String sex, String name, String prefix, String firstName,
			String lastName, String title, List<String> suffixes) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterAttributes(java.util.Map)
	 */
	@Override
	public void buildCharacterAttributes(Map<String, Attribute> attributes) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterRace(dmpro.character.race.Race)
	 */
	@Override
	public void buildCharacterRace(Race race) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterRacePersonalInformation()
	 */
	@Override
	public void buildCharacterRacePersonalInformation() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterClass(java.util.List)
	 */
	@Override
	public void buildCharacterClass(List<CharacterClass> characterClass) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterClassInformation(int, int, int)
	 */
	@Override
	public void buildCharacterClassInformation(int age, int height, int weight) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#buildCharacterHistory()
	 */
	@Override
	public void buildCharacterHistory() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see dmpro.character.CharacterBuilder#getCharacter()
	 */
	@Override
	public Character getCharacter() {
		// TODO Auto-generated method stub
		return null;
	}

}
