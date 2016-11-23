package dmpro.character.managementaction;

import dmpro.core.Server;

import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import dmpro.character.Character;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.Cleric;
import dmpro.character.classes.Paladin;
import dmpro.character.classes.CharacterClassType;
import dmpro.character.classes.UndeadControl;
import dmpro.character.classes.UndeadController;

public class UpdateTurnUndead implements ManagementAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	public Character execute(Character character, Server application) {
		return this.execute(character, application, null, null);
	}

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		int adjustedLevel = -1;
		
		CharacterClassType cct = CharacterClassType.FIGHTER; //default
	    CharacterClass lcc = new CharacterClass(); //empty
	    
	    /* double break for PALADIN and CLERIC */
		for (CharacterClass cc : character.getClasses().values()) {
			lcc = cc;
			cct=cc.getCharacterClassType();
			switch (cct) {
			case CLERIC :
				adjustedLevel = cc.getExperienceLevel();
				break;
			case PALADIN:
				adjustedLevel = cc.getExperienceLevel() -2;
				break;
			default:
				adjustedLevel = -1;
				continue;
			}
			break;
		}
		
		if (adjustedLevel<1) return character;
		logger.log(Level.INFO,cct.name() + ":" + adjustedLevel);
		UndeadControl undeadControl = new UndeadControl();
		undeadControl.setAlignment(character.getAlignment());
		undeadControl.setAdjustedLevel(adjustedLevel);
		undeadControl.setTurnUndeadMap(application.getReferenceDataSet()
				.getTurnUndeadLoader().getRecord(adjustedLevel));
		logger.log(Level.INFO,undeadControl.toString());
		lcc.setUndeadControl(undeadControl);
		character.getClasses().put(cct, lcc);
		//System.out.println(lcc.getUndeadControl().toString());
		logger.log(Level.INFO,"Updating undeadControl for class " + cct.name());
	

//		classRef = character.getClasses().get(CharacterClassType.CLERIC);
//		if (classRef == null) classRef = character.getClasses().get(CharacterClassType.PALADIN);
//		
//		logger.log(Level.INFO, "Updating Turn Undead for class: " + classRef.getCharacterClassType());
//
//		
//		if (adjustedLevel > 0) {
//			logger.log(Level.INFO, " Adjusted Level " + adjustedLevel);
//			buildUndeadControl( character,  classRef, application, adjustedLevel);
//		}
			
		return character;

	}
	
	/* move to constructor  */
	private void buildUndeadControl(Character character, CharacterClass classRef, Server application, int adjustedLevel) {
		
		character.getClasses().put(classRef.getCharacterClassType(), classRef);
	}


}
