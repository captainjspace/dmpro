/**
 * 
 */
package dmpro.character.managementaction;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;
import dmpro.character.CharacterModifierEngine;
import dmpro.core.Server;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 6, 2016
 */
public class AccumulateModifiers implements ManagementAction {

	/* (non-Javadoc)
	 * @see dmpro.character.managementaction.ManagementAction#execute(dmpro.character.Character, dmpro.core.Server, java.util.Scanner, java.util.Formatter)
	 */
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		// TODO Consider integrating these
		character = (new CharacterModifierEngine(application)).processModifiers(character);
		return character;
	}

}
