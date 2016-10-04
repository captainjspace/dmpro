package dmpro.character;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import dmpro.attributes.Attribute;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.character.classes.ICharacterClass;
import dmpro.character.managementaction.CharacterManagementActions;
import dmpro.character.race.Race;


/** CharacterBuilder
 * This class will implement the step to generate a character through gathering input
 * or auto-generate the character.
 * 
 * the Builder remains unaware of how the data required to instance a valid character was assembled
 * 
 * 
 * @author joshualandman
 * @version 0.1
 */

public class PCCharacterBuilder implements CharacterBuilder {

	private Logger logger = Logger.getLogger(this.getClass().toString());
	Character character;
	
	@Override
	public void buildCharacterInitialize() {
		
		character = new Character();
		long time = System.currentTimeMillis();
		int rand = (int) Math.random() * 9;
		int playerBirthDate = 19861205;
		character.created = time;
		character.setCharacterId(
				(new StringBuilder().append(time).append(rand).append(playerBirthDate)).toString()
				);
		logger.log(Level.INFO, "Initialized Character Id: " + character.getCharacterId());
		character.addRequiredAction(CharacterManagementActions.INITIALIZECHARACTER);
	}
	
	@Override
	public void buildCharacterPersonalInformation(String sex, String name, String prefix, String firstName,
			String lastName, String title, List<String> suffixes) {
		character.setSex(sex);
		character.setName(name);
		character.setPrefix(prefix);
		character.setFirstName(firstName);
		character.setLastName(lastName);
		character.setTitle(title);
		character.setSuffixes(suffixes);
		logger.log(Level.INFO, "Set Personal Information Character Id: " + character.getCharacterId());
	}

	@Override
	public void buildCharacterAttributes(Map<String,Attribute> attributes) {
		// TODO Add re-ordering, or option to enter hand rolls.
		
		character.setStrength(attributes.get("Strength"));
		character.setIntelligence(attributes.get("Intelligence"));
		character.setWisdom(attributes.get("Wisdom"));
		character.setDexterity(attributes.get("Dexterity"));
		character.setConstitution(attributes.get("Constitution"));
		character.setCharisma(attributes.get("Charisma"));
		logger.log(Level.INFO, "Build Attributes Complete Character Id: " + character.getCharacterId());
	}
	
	@Override
	public void buildCharacterRace(Race race) {
		// TODO enter options to pick race
		// ensure min/max are followed
		character.setRace(race);
		logger.log(Level.INFO, "Build Race Complete CharacterId: " + character.getCharacterId());
	}

	@Override
	public void buildCharacterRacePersonalInformation() {
		// TODO 
		//age, height, weight
		//get from DMG after class 
		
	}

	@Override
	public void buildCharacterClass(List<CharacterClass> characterClasses) {
		// TODO - allow multiclass
		
		characterClasses.stream()
		.forEach(characterClass -> character.getClasses()
				.put(characterClass.getCharacterClassType(), characterClass));
		
		character.addRequiredAction(CharacterManagementActions.INITIALIZEPROFICIENCIES);
		character.addRequiredAction(CharacterManagementActions.INITIALIZEEQUIPMENT);
		if  ( 
				(character.getClasses().containsKey(CharacterClassType.MAGICUSER)) ||
				(character.getClasses().containsKey(CharacterClassType.ILLUSIONIST))
			) {
				character.addRequiredAction(CharacterManagementActions.INITIALIZESPELLBOOK);
		}
		logger.log(Level.INFO, "Build Added Classes Character Id: " + character.getCharacterId());
		
	}

	@Override
	public void buildCharacterClassInformation(int age, int height, int weight) {
		// TODO
		//initial Items - treasure, equipment, spells
		character.setAge(age);
		character.setHeight(height);
		character.setWeight(weight);
		logger.log(Level.INFO, "Build Class/Race Information for CharacterId: " + character.getCharacterId());
	}

	@Override
	public void buildCharacterHistory() {
		// TODO text input

	}
	
	public Character getCharacter() {
		return character;
	}

}
