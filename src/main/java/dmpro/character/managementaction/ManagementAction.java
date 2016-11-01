package dmpro.character.managementaction;
import dmpro.core.Server;


import dmpro.character.Character;
import java.util.Scanner;
import java.util.Formatter;

public interface ManagementAction {
	
	public Character execute(Character character, Server application, Scanner input, Formatter output);

	/**
	 * @param c
	 * @param application
	 * @return
	 */
	default public  Character execute(Character c, Server application) {
		return c;
	};

}
