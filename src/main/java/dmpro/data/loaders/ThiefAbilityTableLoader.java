package dmpro.data.loaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ThiefAbilityTableLoader implements ResourceLoader {
	/**
	 * data container
	 */
	
	Map<Integer, ThiefAbilityRecord> thiefAbilityMap = new HashMap<Integer, ThiefAbilityRecord>();
	
	public ThiefAbilityTableLoader() {
		load();
	}

	public void load() {
		String file = dataDirectory + "tables/thief-abilities.tsv";
		FileReader reader;
		Scanner scanner;
		
		
		try {
			reader = new FileReader(file);
			scanner = new Scanner(reader);
			scanner.nextLine(); //skip header
			while (scanner.hasNextLine()) {
				ThiefAbilityRecord tar = new ThiefAbilityRecord(scanner.nextLine());
				thiefAbilityMap.put(tar.getExperienceLevel(), tar);
			}
			scanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ThiefAbilityRecord getRecord(int experienceLevel) {
		return this.thiefAbilityMap.get(experienceLevel);
	}
	
	public static void main (String[] args) {
		ThiefAbilityTableLoader tat = new ThiefAbilityTableLoader();
		Scanner s = new Scanner(System.in);
		System.out.println("---------------Thief Ability Table----------------");
		while (true) {
			System.out.println("Enter Thief Level or .exit.");
			String str = s.next();
			if (str.equals(".exit.")) break;
			try {
				System.out.println( tat.getRecord(Integer.parseInt(str)));
			} catch (NumberFormatException e) {
				System.out.println("That doesn't look like a number!");
			}
		}
		s.close();
	}

	/**
	 * @return the thiefAbilityMap
	 */
	public Map<Integer, ThiefAbilityRecord> getThiefAbilityMap() {
		return thiefAbilityMap;
	}

	/**
	 * @param thiefAbilityMap the thiefAbilityMap to set
	 */
	public void setThiefAbilityMap(Map<Integer, ThiefAbilityRecord> thiefAbilityMap) {
		this.thiefAbilityMap = thiefAbilityMap;
	}
}
