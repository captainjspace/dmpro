package dmpro.data.loaders;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import dmpro.character.classes.CharacterClassType;


/**
 * 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 4, 2016
 */
public class CombatTableLoader extends TSVLoader implements ResourceLoader {
	/**
	 * data container for combat
	 * note that all combat records can be resolved to a fine grain fighter table
	 * entire concept could be replaced with THAC0
	 */
	boolean isLoaded = false;
	//List<CombatRecord> tsvTable = new ArrayList<CombatRecord>();
	String webCombatTables="";
	
	public CombatTableLoader() {
		super(CombatRecord.class, "combat-table.tsv");
		webCombatTables = gson.toJson(tsvTable.stream().map(p -> (CombatRecord)p).collect(Collectors.toList()));
		isLoaded = true;
	}
	
	public String getWebCombatTables() {
		return webCombatTables;
	}
	
	
	/**
	 * Leverage the CharacterClassType to map type name to data value
	 * @param characterClassType (combat class type)
	 * @param level
	 * @return CombatRecord
	 */
	public CombatRecord getRecord(CharacterClassType characterClassType, int level) {
		return tsvTable.stream()
				.map(p -> (CombatRecord)p)
				.filter (p -> p.getCharacterClassName().toLowerCase().equals(characterClassType.className.toLowerCase()))
				.filter (p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	
	public CombatRecord getRecord(String characterClass, int level) {
		//System.out.printf("%s - %d", characterClass, level);
		//tsvTable.stream().forEach(t -> System.out.println(t.toString()));
		return tsvTable.stream()
				.map(p -> (CombatRecord)p)
				.filter (p -> p.getCharacterClassName().toLowerCase().equals(characterClass.toLowerCase()))
				.filter (p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	
//	public CombatRecord getRecord(int level) {
//		//deal with max level for combat
//		return tsvTable.stream()
//				.map(p -> (CombatRecord)p)
//				.filter (p -> p.getExperienceLevel() == level)
//				.findFirst().get();
//	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("------------Welcome to the Combat Tables Utility-------------");
		CombatTableLoader combatTableLoader = new CombatTableLoader();
		Scanner scanner = new Scanner(System.in);
		String input="";
		while (true) {
			System.out.println("Please Enter Class <class> :== [magic-user, cleric, thief, monster, fighter] or .exit.");
			input = scanner.next();
			if (input.equals(".exit.")) break;
			System.out.println("Please Enter Level <level> :== ");
			CombatRecord cr = combatTableLoader.getRecord(input.trim(), scanner.nextInt());
			System.out.printf("Hit Table for: %s : Level %d:  Fighter Equivalent: %s\n",cr.getCharacterClassName(), cr.getExperienceLevel(), cr.getFighterEquivalent());
			cr.armorClass.entrySet().stream().sorted(Map.Entry.comparingByKey())
			.forEach(a -> System.out.printf("AC %d : To Hit %d\n",a.getKey(),a.getValue()));
			//		.forEach(k,v -> System.out.printf("AC %d : To Hit %d\n",k,v));
		}
		scanner.close();
	}

}
