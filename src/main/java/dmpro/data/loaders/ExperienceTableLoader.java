package dmpro.data.loaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExperienceTableLoader implements ResourceLoader {
	/**
	 * data container
	 * needs to interact with individual classes to extend base data table as characters progress to levels/
	 * experience beyond standard table
	 */
	
	List<ExperienceTableRecord> experienceTable = new ArrayList<ExperienceTableRecord>();

	public ExperienceTableLoader() {
		load();
	}

	public ExperienceTableLoader(String characterClassName) {
		load();
		this.experienceTable= experienceTable.stream()
				.filter(p -> p.characterClass.toLowerCase().equals(characterClassName.toLowerCase()))
				.collect(Collectors.toList());	
//		this.experienceTable.stream().forEach(a -> System.out.printf("Class: %s : Level: %d Min: %d :Max %d", a.characterClass,a.experienceLevel,a.minExperiencePoints,a.maxExperiencePoint));
	}
	
	public boolean load() {
		String xpFile = "xptable.tsv";
		String xpDir = dataDirectory + "tables/" + xpFile;
		FileReader reader;
		try {
			reader = new FileReader(xpDir);
			Scanner scanner = new Scanner(reader);
			ExperienceTableRecord experienceTableRecord;
			scanner.nextLine(); //skip header
			while (scanner.hasNext()) {
				String lineInput = scanner.nextLine();
				experienceTableRecord = new ExperienceTableRecord(lineInput);
				experienceTable.add(experienceTableRecord);
			}
			scanner.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public ExperienceTableLoader getExperienceTable(String characterClassName) {
		return new ExperienceTableLoader(characterClassName);
	}
	public ExperienceTableRecord getRecordByXP(long experiencePoints) {
		ExperienceTableRecord experienceTableRecord=new ExperienceTableRecord();
		try {
			//deal with off the charts
			experienceTableRecord = this.experienceTable.stream()
					.filter(p -> p.getMinExperiencePoints() <= experiencePoints && p.getMaxExperiencePoint() > experiencePoints)
					.findFirst().get();
		} catch (NoSuchElementException e) {
			System.out.printf("Could not find record for %d experience Points\n", experiencePoints);
			
		}
		return experienceTableRecord;
	}
	public ExperienceTableRecord  getRecord(String characterClass, int level) {
		//should this be null?
		ExperienceTableRecord experienceTableRecord=new ExperienceTableRecord();
		try {
			experienceTableRecord = this.experienceTable.stream()
					.filter(p -> p.getCharacterClass().equalsIgnoreCase(characterClass.toLowerCase()))
					.filter(p -> p.getExperienceLevel() == level)
					.findFirst().get();
		} catch (NoSuchElementException e) {
			System.out.println("Could not find record for level " + level);
			//printTable;
		}
		return experienceTableRecord;
		
	}
	public ExperienceTableRecord  getRecord(int level) {
		//should this be null?
		ExperienceTableRecord experienceTableRecord=new ExperienceTableRecord();
		try {
			experienceTableRecord = this.experienceTable.stream().filter(p -> p.experienceLevel == level).findFirst().get();
		} catch (NoSuchElementException e) {
			System.out.println("Could not find record for level " + level);
			//printTable;
		}
		return experienceTableRecord;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("------------Welcome to the Experience Table Utility-------------");
		System.out.println("Please Enter Class <class> :== [magic-user, thief, fighter, cleric]");
		ExperienceTableLoader xt = new ExperienceTableLoader(scanner.next());
//		System.out.println("Please Enter Level <level> :== [1-29]");
//		System.out.println( xt.getRecord(scanner.nextInt()).toString());
		System.out.println("Please Enter XP <long> :== ");
		System.out.println( xt.getRecordByXP(scanner.nextLong()));
			
		scanner.close();
		

	}

}
