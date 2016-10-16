package dmpro.character;
/**
 * ModifierEngine.java
 * 
 * This is the interface for Modifying a Character
 */
import dmpro.modifier.Modifier;

public interface ModifierEngine {

	Character processModifiers(Character character);

	/**
	 * Modifiers inherited from Game Environments
	 * specific Universe, Plane, Locations that the Character is in
	 */
	void processEnvironmentModifiers();

	/**
	 * Modifiers specific to all entities in an encounter
	 */
	void processEncounterModifiers();

	/**
	 * Modifiers specific to all members of the party
	 * for example - a prayer spell by a Cleric
	 */
	void processPartyModifiers();

	/** 
	 * Specific modifications to core attributes
	 * combines two methods from character -- but i hate the code...
	 * refactor me please!
	 * 
	 * @param modifier attribute modifier to process
	 */
	void processAttributeModifier(Modifier modifier);

	/**
	 * General modifications due to Spells, Magic Items, Race or Class
	 */
	void processAbilityModifiers();

	/** 
	 * Modifier specifically impacting saving throw objects
	 */
	void processSavingThrowModifiers();
	
	

}