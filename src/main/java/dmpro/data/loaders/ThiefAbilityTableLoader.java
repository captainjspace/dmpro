package dmpro.data.loaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ThiefAbilityTableLoader extends TSVLoader implements ResourceLoader {
	/**
	 * data container
	 */
	
	public ThiefAbilityTableLoader() {
		super(ThiefAbilityRecord.class,"thief-abilities.tsv");
	}

	public ThiefAbilityRecord getRecord(int experienceLevel) {
		return tsvTable.stream()
				.map(p -> (ThiefAbilityRecord) p )
				.filter(p -> p.getExperienceLevel() == experienceLevel)
				.findFirst()
				.get();
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

}
