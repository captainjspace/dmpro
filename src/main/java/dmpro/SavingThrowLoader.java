package dmpro;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SavingThrowLoader {
	/**
	 * data container
	 */
	List<SavingThrowRecord> savingThrowTable=new ArrayList<SavingThrowRecord>();

	public SavingThrowLoader() { load(); }
	public SavingThrowLoader(String className) {
		load();
		this.savingThrowTable = savingThrowTable.stream()
				.filter( p -> p.getCharacterClassName().toLowerCase()
						.equals(className.toLowerCase()))
				.collect(Collectors.toList());
	}


	public boolean load() {
		String file = "data/tables/dmg/saving-throws.tsv";
		FileReader reader; 

		try {
			reader= new FileReader(file);
			Scanner scanner = new Scanner(reader);
			scanner.nextLine(); //skip header

			while (scanner.hasNext()) 
				savingThrowTable.add(new SavingThrowRecord(scanner.nextLine()));
			scanner.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public SavingThrowRecord getRecord(String characterClass, int experienceLevel) {
		return savingThrowTable.stream()
				.filter(p -> p.getCharacterClassExperienceLevel() == experienceLevel)
				.filter(p -> p.getCharacterClassName().toLowerCase().equals(characterClass.toLowerCase()))
				.findFirst().get();
	}
	
	public SavingThrowRecord getRecord(int experienceLevel) {
		return savingThrowTable.stream()
				.filter(p -> p.getCharacterClassExperienceLevel() == experienceLevel)
				.findFirst().get();
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		SavingThrowLoader stt;
		
		while (true) {
			System.out.println("Enter Class [Cleric, Fighter, Magic-User, Thief] > ");
			String input = scan.next();
			if (input.equals(".exit.")) break;
			stt = new SavingThrowLoader(input);
			System.out.println("Enter Level");
			System.out.println( stt.getRecord(scan.nextInt()).toString());
			
		}
		scan.close();
	}
}
