package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.data.loaders.SavingThrowRecord;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;


import dmpro.character.Character;
import dmpro.character.classes.CharacterClass;
import dmpro.character.CharacterService;
import dmpro.character.SavingThrow;
import dmpro.character.SavingThrowType;



/**
 *  <ul>
 *   <li> get baseline saves by class - 11/17</li>
 *   <li> select best save by class / merge - 11/17 </li>
 *   <li> check/process active modifiers </li>
 *   <li> attach attribute based, conditional save boosts - situational (save v poison) </li>
 *   <li> check for direct magical items (ring of protection, magic armor, etc) </li>
 *   <li> check directly impactful spells (protection from evil, etc. </li>
 *  </ul>
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 10, 2016
 * 
 * 
 */
public class UpdateSavingThrows implements ManagementAction {
//TODO: Implementation 
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output){
		
		return character;
	}

	
	public Character execute(Character character,Server application) {
		
		CharacterService characterService = application.getCharacterService();
		logger.log(Level.INFO, "Updating Character Saving Throws");
		
		Map <SavingThrowType, SavingThrow> savingThrowMap = new HashMap<SavingThrowType, SavingThrow>();
		//if (!character.getRequiredActions().isEmpty()) {
			//int i = character.getRequiredActions().indexOf(CharacterManagementActions.UPDATESAVINGTHROWS);
			//if (i != -1) { //make sure this is required - double check since we checked in API
			
				/*loop through classes */
				for (CharacterClass characterClass : character.getClasses().values()) {
					//compare saving throws for each class and choose lower.
					
					SavingThrowRecord savingThrowRecord = characterClass.getSavingThrow();
					SavingThrow st;

					/* Loop through all saving throws use SavingThrowRecord helper function */
					for (SavingThrowType stt : SavingThrowType.values()) {
						st = new SavingThrow();
						st.setSavingThrowType(stt);// = stt;
						//TODO: Null Pointer 
						logger.log(Level.INFO, "value of stt: "+ stt.name());
						
						try {
						st.setSavingThrowRoll(savingThrowRecord.getBySavingThrowType(stt));
						} catch (NullPointerException e) {
							logger.log(Level.INFO, "");
						}
						if (savingThrowMap.containsKey(stt)) {
							SavingThrow existing = savingThrowMap.get(stt);
							int currentSave = existing.getSavingThrowRoll();
							/* make sure we didn't load an empty save map with zeroes */
							if ( currentSave > 0 && currentSave < st.getSavingThrowRoll() ) continue; //skip we already have a lower value;
							else savingThrowMap.put(stt, st);
						} else savingThrowMap.put(stt, st);
					}
										
				} //foreach class
				
//				//Proceed to SavingThrow Modifiers .
//				try { 
//					character.getRequiredActions().remove(i); //remove action
//				} catch (Exception e) {
//					logger.log(Level.INFO, "did not find update saving throw action -- probably initializong a new character");
//				}
				character.setSavingThrowMap(savingThrowMap); //add to character
				character = characterService.saveCharacter(character); //save
			//} //end of management action
		//}
		return character;
	}
}
