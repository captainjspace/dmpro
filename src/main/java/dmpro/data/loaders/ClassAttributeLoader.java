package dmpro.data.loaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dmpro.attributes.Attribute;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.CharacterClass.CharacterClassType;

public class ClassAttributeLoader {

	private List<ClassAttributeRecord> classAttributeTable = new ArrayList<ClassAttributeRecord>();
	private final String classAttributeFile = "./data/tables/class-attribute-limits";
	private final int fieldCount = 3;
	
	public class ClassAttributeRecord {
		public CharacterClassType characterClassType;
		public String attribute;
		public int min;
		
		public ClassAttributeRecord (String[] fields){
			this.characterClassType = CharacterClassType.valueOf(fields[0]);
			this.attribute = fields[1];
			this.min = Integer.parseInt(fields[2]);
		}
		
		public String toString() {
			Formatter formatter = new Formatter();
			String retVal =
					formatter.format("ClassAttributeLimit: Class %s, Attribute: %s, Min Value: %d",
							characterClassType, attribute, min).toString();
			formatter.close();
			return retVal;
		}
		
	}
	
	public ClassAttributeLoader() {
		load();
	}
	
	public void load() {
		FileReader reader=null;
		Scanner scanner;
		try {
			reader = new FileReader(classAttributeFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.printf("classAttributeLoader: File Not found %s\n", classAttributeFile);
		}
		scanner = new Scanner(reader);
		scanner.nextLine();
		
		while (scanner.hasNextLine())
			classAttributeTable.add( new ClassAttributeRecord(scanner.nextLine().split("\t",fieldCount)));
		
		scanner.close();
	}
	
	public class IsInRangeResults {
		public boolean isInRange;
		public ClassAttributeRecord classAttributeRecord;
		public IsInRangeResults (boolean isInRange, ClassAttributeRecord classAttributeRecord) {
			this.isInRange = isInRange;
			this.classAttributeRecord = classAttributeRecord;
		}
	}
	public IsInRangeResults isInRange (String attributeName, int abilityScore, CharacterClassType characterClassType) {
		
		IsInRangeResults isInRangeResults = null;
//		System.out.format("Attribute Name: %s, Ability Score: %d, CharacterClassType: %s\n",
//				attributeName, abilityScore, characterClassType);
		try {
		ClassAttributeRecord classAttributeRecord = classAttributeTable.stream()
				.filter(p -> p.attribute.equals(attributeName))
				.filter(p -> p.characterClassType == characterClassType)
				.findFirst().get();
		
		return ( classAttributeRecord.min <= abilityScore ) ?
				new IsInRangeResults (true, classAttributeRecord) 
				: new IsInRangeResults (false, classAttributeRecord );
		} catch (NoSuchElementException e) {
//			e.printStackTrace();
			System.out.format("WARN: No Record found for:\tAttribute Name: %s, Ability Score: %d, CharacterClassType: %s\n",
					attributeName, abilityScore, characterClassType);
		}
		return isInRangeResults;
	}
	
	public class ListPossibleClassResults {
		public List<CharacterClassType> possibleClasses = new ArrayList<CharacterClassType>();
		public List<ClassAttributeRecord> limitRecords = new ArrayList<ClassAttributeRecord>();
	}
	
	public ListPossibleClassResults listPossibleClasses(Map<String, Attribute> attributes) {
		
		ListPossibleClassResults listPossibleClassResults = new ListPossibleClassResults();
		IsInRangeResults isInRangeResults;
		
		for (CharacterClassType characterClassType : CharacterClassType.values()) {
			boolean classAble = true;
			for (Attribute a : attributes.values()) {
				isInRangeResults =  isInRange(a.attributeName, a.abilityScore, characterClassType);
				if (isInRangeResults != null && !isInRangeResults.isInRange) {
					listPossibleClassResults.limitRecords.add(isInRangeResults.classAttributeRecord);
					classAble = false;
				}
			}
			if (classAble) listPossibleClassResults.possibleClasses.add(characterClassType);
		}
		return listPossibleClassResults;
	}
	
	
	
	
	
	
	
}
