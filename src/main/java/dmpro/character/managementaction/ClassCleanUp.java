/**
 * 
 */
package dmpro.character.managementaction;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;
import dmpro.core.Server;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 18, 2016
 */
public class ClassCleanUp implements ManagementAction {

	/* (non-Javadoc)
	 * @see dmpro.character.managementaction.ManagementAction#execute(dmpro.character.Character, dmpro.core.Server, java.util.Scanner, java.util.Formatter)
	 */
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		// TODO Auto-generated method stub
		return character;
	}

}
