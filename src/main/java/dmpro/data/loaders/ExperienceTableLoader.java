package dmpro.data.loaders;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class ExperienceTableLoader extends TSVLoader implements ResourceLoader {
	/**
	 * data container
	 * needs to interact with individual classes to extend base data table as characters progress to levels/
	 * experience beyond standard table
	 */
	
	//List<ExperienceTableRecord> experienceTable = new ArrayList<ExperienceTableRecord>();

	public ExperienceTableLoader() {
		super(ExperienceTableRecord.class, "xptable.tsv");
	}
	
	public ExperienceTableRecord getRecordByXP(String characterClass, long experiencePoints) {
		return
			this.tsvTable.stream()
					.map(p -> (ExperienceTableRecord) p)
					.filter(p -> p.getCharacterClass().equalsIgnoreCase(characterClass.toLowerCase()))
					.filter(p -> p.getMinExperiencePoints() <= experiencePoints && p.getMaxExperiencePoint() > experiencePoints)
					.findFirst().get();
	}
	
	public ExperienceTableRecord getRecord(String characterClass, int level) {
		//should this be null?
		ExperienceTableRecord experienceTableRecord=new ExperienceTableRecord();
		try {
			experienceTableRecord = this.tsvTable.stream()
					.map(p -> (ExperienceTableRecord) p)
					.filter(p -> p.getCharacterClass().equalsIgnoreCase(characterClass.toLowerCase()))
					.filter(p -> p.getExperienceLevel() == level)
					.findFirst().get();
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
		ExperienceTableLoader xt = new ExperienceTableLoader();
		String characterClass  = scanner.next();
//		System.out.println("Please Enter Level <level> :== [1-29]");
//		System.out.println( xt.getRecord(scanner.nextInt()).toString());
		System.out.println("Please Enter XP <long> :== ");
		System.out.println( xt.getRecordByXP(characterClass, scanner.nextLong()));
			
		scanner.close();
		

	}

}
