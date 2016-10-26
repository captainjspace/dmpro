package dmpro.data.loaders;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dmpro.character.classes.CharacterClassType;
import dmpro.character.race.RaceType;

public class ClassRaceLoader extends TSVLoader implements ResourceLoader{
	
	//private List<ClassRaceRecord> classRaceTable = new ArrayList<ClassRaceRecord>();
	//private final String classRaceFile = dataDirectory + "tables/class-race-limits.tsv";
	//private final int fieldCount = 7;
	
	
	
	public ClassRaceLoader() {
		super(ClassRaceRecord.class, "class-race-limits.tsv");
	}
	
	public class ListPossibleClassRaceResults {
		public List<CharacterClassType> possibleClasses= new ArrayList<CharacterClassType>();
		public List<ClassRaceRecord> limitRecords = new ArrayList<ClassRaceRecord>();
	}

	public ListPossibleClassRaceResults listPossibleClasses (RaceType race) {
		
		ListPossibleClassRaceResults possibleClassResults = new ListPossibleClassRaceResults();
		
		possibleClassResults.possibleClasses.addAll(
				tsvTable.stream()
				.map(p -> (ClassRaceRecord) p)
				.filter(p -> p.raceType == race && p.able)
				.map(p -> p.characterClassType)
				.collect(Collectors.toList())
				);
		
		possibleClassResults.limitRecords.addAll(tsvTable.stream()
				.map(p -> (ClassRaceRecord) p)
				.filter(p -> p.raceType == race && !p.able)
				.collect(Collectors.toList())
				);
		return possibleClassResults;
		
	}
	
	public static void main (String[] args) {
		
		ClassRaceLoader crl = new ClassRaceLoader();
		
	}
}










