package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.data.loaders.ThiefAbilityTableLoader;

import java.util.Formatter;
import java.util.Scanner;
import dmpro.character.Character;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.CharacterClassType;
import dmpro.character.classes.Thief;

/**
 * Update Thief Skills 
 * <p>  Updating thief skills steps:
 * <ul>
 *   <li> Look up baseline skills by level in ThiefSkillsLoader </li>
 *   <li> check/process active modifiers </li>
 *   <li> +/- Lookup Dexterity Adjustment to thief skills </li>
 *   <li> +/- look up race adjustments to thief skills </li>
 *   <li> check for direct magical items (gauntlets of thieving, etc)  </li>
 *   <li> check directly impactful spells (invisibility, spider climb, etc)  </li>
 *  </ul>
 *   
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 10, 2016
 */
public class UpdateThiefSkills implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		
		return character;
	}
	
	

	public Character execute(Character character, Server application, String jsonInput) {
		/* base record */
		ThiefAbilityTableLoader tal = application.getReferenceDataSet().getThiefAbilityTableLoader();
		Thief thief = (Thief) character.getClasses().get(CharacterClassType.THIEF);
		thief.setThiefAbilityRecord(tal.getRecord(thief.getExperienceLevel()));
		
		
		
		return character;
	}

}
