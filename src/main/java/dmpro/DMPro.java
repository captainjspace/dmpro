package dmpro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import dmpro.spells.Spell;
import dmpro.spells.SpellLibrary;

public class DMPro {

	
	private static File log;
	private static FileWriter writer;
	private static Scanner scanner;



	public static void main(String[] args)  {
		// TODO Auto-generated method stub	
		SpellLibrary sl = new SpellLibrary();
		
		log = new File ("log/dm.log");
		try {
			writer = new FileWriter(log);
			scanner = new Scanner(System.in);
			Spell spell;
			while (true) {
				System.out.println("Enter Spell Name");
				String input = scanner.nextLine();
				spell = sl.getSpell(input);
				if (spell != null) {
					String spellDetails = spell.getFullSpellText() + '\n';
					System.out.printf("%s\n",spellDetails);
				} else { System.out.printf("Can't find %s in the library\n", input);}
				writer.write(input + "\n");
				if (input.equals(".exit.")) break;				
				//System.out.println("Goodbye DungeonMaster");
			} 
			writer.close();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
