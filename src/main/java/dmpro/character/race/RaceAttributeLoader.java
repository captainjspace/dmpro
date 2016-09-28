package dmpro.character.race;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dmpro.attributes.Attribute;
/**
 * RaceAttributeLoader.java
 *  Standard to be refactored
 *  
 *  RaceAttributeLoader combines
 *  The tab separated value loader, the pojo and the list table container.
 *  
 *  this table container has some search intelligence methods and return objects specific to the character creation
 *  build process.
 *  these are used by the CharacterBuildDirector to determine race exclusions after attributes have been entered.
 *  
 * @author joshualandman
 *
 */

public class RaceAttributeLoader {
	
	private List<RaceAttributeRecord> raceAttributeTable = new ArrayList<RaceAttributeRecord>();
	private final String raceAttributeFile = "data/tables/race-attribute-limits.tsv";
	private final int fieldCount = 5;
	
	public class RaceAttributeRecord {

		RaceType raceType;
		String sex;
		String attribute;
		int min;
		int max;
		
		public RaceAttributeRecord(String[] fields) {
			raceType = RaceType.valueOf(fields[0]);
			sex = fields[1];
			attribute = fields[2];
			min = Integer.parseInt(fields[3]);
			max = Integer.parseInt(fields[4]);
		}
		public String toString() {
			Formatter formatter = new Formatter();
			String record = 
				formatter.format("Attribute Limit: Attribute: %s, Race:%s, Sex:%s, Min:%d, Max:%d",
						attribute, raceType, sex, min, max).toString();
			formatter.close();
			return record;

		}

	}


	public RaceAttributeLoader () {
		load();
	}
	
	public void load() {
		FileReader reader=null;
		Scanner scanner;
		
		try {
			reader = new FileReader(raceAttributeFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.printf("RaceAttributeLoader: File Not found %s\n", raceAttributeFile);
		}
		scanner = new Scanner(reader);
		scanner.nextLine();
		
		while (scanner.hasNextLine())
			raceAttributeTable.add( new RaceAttributeRecord(scanner.nextLine().split("\t",fieldCount)));
		
		scanner.close();
		//raceAttributeTable.stream().forEach(p -> System.out.println(p.toString()));
	}

	public class IsInRangeResults {
		boolean isInRange;
		RaceAttributeRecord raceAttributeRecord;
		public IsInRangeResults(boolean isInRange, RaceAttributeRecord raceAttributeRecord) {
			this.isInRange = isInRange;
			this.raceAttributeRecord = raceAttributeRecord;
		}
	}
	public IsInRangeResults isInRange(String attributeName, int attributeValue, String gender, RaceType raceType) {
		IsInRangeResults isInRangeResults = null;
//		System.out.format("Attribute Name; %s\n, Attribute Value: %d\n, Gender: ,%s\n, RaceType: %s\n",
//				attributeName, attributeValue, sex, raceType);
		
		RaceAttributeRecord raceAttributeRecord;
		try {
			raceAttributeRecord = raceAttributeTable.stream()
		
				.filter(p -> p.raceType == raceType)
				.filter(p -> p.sex.equals(gender))
				.filter(p -> p.attribute.equals(attributeName))
				.findFirst().get();
			//System.out.println("Success");
			return ( (attributeValue >= raceAttributeRecord.min) && (attributeValue <= raceAttributeRecord.max) ) ? 
					new IsInRangeResults(true, raceAttributeRecord) //true
					: new IsInRangeResults(false, raceAttributeRecord); //false
		}
		catch (NoSuchElementException e) {
			System.out.format("Attribute Name; %s\n, Attribute Value: %d\n, Gender: ,%s\n, RaceType: %s\n",
					attributeName, attributeValue, gender, raceType);
			e.printStackTrace();
		}

		return isInRangeResults;
	}
	
	public class ListPossibleRaceResults {
		public List<RaceType> possibleRaces = new ArrayList<RaceType>();
		public List<RaceAttributeRecord> limitRecords = new ArrayList<RaceAttributeRecord>();
	}
	
	public ListPossibleRaceResults listPossibleRaces(Map<String,Attribute> attributes, String sex) {
		ListPossibleRaceResults listPossibleRaceResults = new ListPossibleRaceResults();
		IsInRangeResults isInRangeResults;
		for (RaceType raceType : RaceType.values()) {
			boolean raceAble = true;
			for (Attribute a : attributes.values()) {
				isInRangeResults = isInRange( a.attributeName, a.abilityScore, sex, raceType);
				if (isInRangeResults.isInRange == false ) {
					listPossibleRaceResults.limitRecords.add(isInRangeResults.raceAttributeRecord);
					raceAble = false;
				} 
			}
			if (raceAble) listPossibleRaceResults.possibleRaces.add(raceType);
		}
		return listPossibleRaceResults;
	}	
}
