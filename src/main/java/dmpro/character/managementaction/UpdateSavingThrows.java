package dmpro.character.managementaction;

import dmpro.core.Server;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;


/**
 *  <ul>
 *   <li> get baseline saves by class</li>
 *   <li> check/process active modifiers </li>
 *   <li> select best save by class / merge </li>
 *   <li> attach attribute based, conditional save boosts </li>
 *   <li> check for direct magical items (ring o protection, magic armor, etc  </li>
 *   <li> check directly impactful spells (protection from evil, etc. </li>
 *  </ul>
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 10, 2016
 * 
 * 
 */
public class UpdateSavingThrows implements ManagementAction {
//TODO: Implementation 
	
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output){
		
		return character;
	}

	
	public Character execute(Character character) {
		return character;
	}
}
