package dmpro.data.loaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class SavingThrowLoader extends TSVLoader implements ResourceLoader{
	/**
	 * data container
	 */
	List<SavingThrowRecord> savingThrowTable=new ArrayList<SavingThrowRecord>();

	String webSavingThrowTables="";
	
	public SavingThrowLoader() { 
		super(SavingThrowRecord.class, "saving-throws.tsv");
		webSavingThrowTables = gson.toJson(tsvTable.stream().map(p -> (SavingThrowRecord)p).collect(Collectors.toList()));
		
	}
	public String getWebSavingThrowTables() {
		return webSavingThrowTables;
	}
	
	public SavingThrowRecord getRecord(String characterClass, int experienceLevel) {
		return tsvTable.stream()
				.map(p -> (SavingThrowRecord)p)
				.filter(p -> p.getCharacterClassExperienceLevel() == experienceLevel)
				.filter(p -> p.getCharacterClassName().toLowerCase().equals(characterClass.toLowerCase()))
				.findFirst().get();
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		SavingThrowLoader stt = new SavingThrowLoader();
		
		while (true) {
			System.out.println("Enter Class [Cleric, Fighter, Magic-User, Thief] > ");
			String characterClass = scan.next();
			if (characterClass.equals(".exit.")) break;
			System.out.println("Enter Level");
			System.out.println( stt.getRecord(characterClass, scan.nextInt()).toString());
			
		}
		scan.close();
	}
}
