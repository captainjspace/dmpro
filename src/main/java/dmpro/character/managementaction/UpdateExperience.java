package dmpro.character.managementaction;

import dmpro.core.Server;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;

public class UpdateExperience implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output){
		
		return character;
	}
	
	public Character execute(Character character, Server application, int xp) {
		//application.getCharacterService().getXpProcessor();
		return character;
	}

}
