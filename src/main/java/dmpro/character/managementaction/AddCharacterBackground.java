package dmpro.character.managementaction;

import dmpro.core.Server;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;

public class AddCharacterBackground implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		// TODO Auto-generated method stub
		return character;
	}
	
	public Character execute(Character character, String jsonInput) {
		//Get Character History - set string 
		return character;
	}

}
