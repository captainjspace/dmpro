package dmpro.character.classes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.character.race.RaceType;

public class ClassRaceLoader {
	
	private List<ClassRaceRecord> classRaceTable = new ArrayList<ClassRaceRecord>();
	private final String classRaceFile = "./data/tables/class-race-limits.tsv";
	private final int fieldCount = 7;
	
	public class ClassRaceRecord {
		RaceType raceType;
		CharacterClassType characterClassType;
		boolean able;
		int maxLevel;
		String attribute;
		int attributeFloorScore;
		int levelIncreasePerAboveAttributeFloor;
		
		public ClassRaceRecord(String fields[]) {
			raceType = RaceType.valueOf(fields[0]);
			characterClassType = CharacterClassType.valueOf(fields[1]);
			able = Boolean.valueOf(fields[2]);
			if (!fields[3].isEmpty()) maxLevel = Integer.parseInt(fields[3]);
			if (!fields[4].isEmpty()) attribute = fields[4];
			if (!fields[5].isEmpty()) attributeFloorScore = Integer.parseInt(fields[5]);
			if (!fields[6].isEmpty()) levelIncreasePerAboveAttributeFloor = Integer.parseInt(fields[6]);
					
		}
		
		public String toString() {
			Formatter formatter = new Formatter();
			String retVal =
					formatter.format("ClassRaceLimit: Race: %s, Class %s, Permitted: %s",
							raceType, characterClassType, able).toString();
			formatter.close();
			return retVal;
		}
	}
	
	public ClassRaceLoader() {
		load();
	}
	
	public void load() {
		FileReader reader=null;
		Scanner scanner;
		try {
			reader = new FileReader(classRaceFile);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.printf("classRaceLoader: File Not found %s\n", classRaceFile);
		}
		scanner = new Scanner(reader);
		scanner.nextLine();
		
		while (scanner.hasNextLine())
			classRaceTable.add( new ClassRaceRecord(scanner.nextLine().split("\t",fieldCount)));
		scanner.close();
	}
	
	public class ListPossibleClassRaceResults {
		public List<CharacterClassType> possibleClasses= new ArrayList<CharacterClassType>();
		public List<ClassRaceRecord> limitRecords = new ArrayList<ClassRaceRecord>();
	}

	public ListPossibleClassRaceResults listPossibleClasses (RaceType race) {
		ListPossibleClassRaceResults possibleClassResults = new ListPossibleClassRaceResults();
		possibleClassResults.possibleClasses.addAll(
				classRaceTable.stream().filter(p -> p.raceType == race && p.able)
				.map(p -> p.characterClassType)
				.collect(Collectors.toList())
				);
		possibleClassResults.limitRecords.addAll(classRaceTable.stream()
				.filter(p -> p.raceType == race && !p.able)
				.collect(Collectors.toList())
				);
		return possibleClassResults;
		
	}
}










