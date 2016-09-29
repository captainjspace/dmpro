package dmpro;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CombatTableLoader {
	/**
	 * data container for combat
	 * note that all combat records can be resolved to a fine grain fighter table
	 * entire concept could be replaced with THAC0
	 */
	boolean isLoaded = false;
	List<CombatRecord> combatTable = new ArrayList<CombatRecord>();
	
	public CombatTableLoader() {
		isLoaded = load();
	}
	
	public CombatTableLoader(String characterClass) {
		//if (!isLoaded) load();  fix me so i have a sub list for queries
		load();
		this.combatTable = combatTable.stream()
				.filter( p -> p.getCharacterClassName().toLowerCase()
				.equals(characterClass.toLowerCase()))
				.collect(Collectors.toList());
	}
	public boolean load() {
		String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
		String spFile = "combat-table.tsv";
		String spDir = "/resources/data/tables/" + spFile;
		//reader = new BufferedReader(getClass().getResourceAsStream(spDir));
		Scanner scanner = new Scanner(getClass().getResourceAsStream(spFile));
		CombatRecord combatRecord;
		scanner.nextLine(); //skip header
		while (scanner.hasNext()) {
			String lineInput = scanner.nextLine();
			combatRecord = new CombatRecord(lineInput);
			combatTable.add(combatRecord);
		}
		scanner.close();
		return true;

	}
	public CombatRecord getRecord(String characterClass, int level) {
		return combatTable.stream()
				.filter (p -> p.getCharacterClassName().toLowerCase().equals(characterClass.toLowerCase()))
				.filter (p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	
	public CombatRecord getRecord(int level) {
		//deal with max level for combat
		return combatTable.stream()
				.filter (p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("------------Welcome to the Combat Tables Utility-------------");
		CombatTableLoader combatTableLoader;
		Scanner scanner = new Scanner(System.in);
		String input="";
		while (true) {
			System.out.println("Please Enter Class <class> :== [magic-user, cleric, thief, monster, fighter] or .exit.");
			input = scanner.next();
			if (input.equals(".exit.")) break;
			combatTableLoader = new CombatTableLoader(input);
			System.out.println("Please Enter Level <level> :== ");
			CombatRecord cr = combatTableLoader.getRecord(scanner.nextInt());
			System.out.printf("Hit Table for: %s : Level %d:  Fighter Equivalent: %s\n",cr.getCharacterClassName(), cr.getExperienceLevel(), cr.getFighterEquivalent());
			cr.armorClass.entrySet().stream().sorted(Map.Entry.comparingByKey())
			.forEach(a -> System.out.printf("AC %d : To Hit %d\n",a.getKey(),a.getValue()));
			//		.forEach(k,v -> System.out.printf("AC %d : To Hit %d\n",k,v));
		}
		scanner.close();
	}

}
