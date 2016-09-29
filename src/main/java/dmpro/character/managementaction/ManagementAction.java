package dmpro.character.managementaction;
import dmpro.core.Server;


import dmpro.character.Character;

public interface ManagementAction {
	
	public Character execute(Character character, Server application);

}
