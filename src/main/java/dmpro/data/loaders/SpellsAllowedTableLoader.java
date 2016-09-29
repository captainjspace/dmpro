package dmpro.data.loaders;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SpellsAllowedTableLoader {

	List<SpellsAllowedRecord> spellsAllowed = new ArrayList<SpellsAllowedRecord>();
	
	public SpellsAllowedTableLoader() {
		load();
	}
	
	public SpellsAllowedTableLoader(String characterClass) {
		load();
		this.spellsAllowed = spellsAllowed.stream()
				.filter( p -> p.getCharacterClassName().toLowerCase()
				.equals(characterClass.toLowerCase()))
				.collect(Collectors.toList());
	}
	public boolean load() {
		String spFile = "spells-useable.tsv";
		String spDir = "data/tables/" + spFile;
		FileReader reader;
		try {
			reader = new FileReader(spDir);
			Scanner scanner = new Scanner(reader);
			SpellsAllowedRecord spellsAllowedRecord;
			scanner.nextLine(); //skip header
			while (scanner.hasNext()) {
				String lineInput = scanner.nextLine();
				spellsAllowedRecord = new SpellsAllowedRecord(lineInput);
				spellsAllowed.add(spellsAllowedRecord);
			}
			
			scanner.close();
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	public SpellsAllowedRecord getRecord(String characterClass, int level) {
		return spellsAllowed.stream()
				.filter( p -> p.getCharacterClassName().toLowerCase().equals(characterClass.toLowerCase()))
				.filter(p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	public SpellsAllowedRecord getRecord(int level) {
		return spellsAllowed.stream()
				.filter (p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpellsAllowedTableLoader spellsAllowed;
		Scanner scanner = new Scanner(System.in);
		System.out.println("------------Welcome to the Spells Useable Utility-------------");
		System.out.println("Please Enter Class <class> :== [magic-user, cleric, illusionist, druid, ranger, paladin, bard]");
		spellsAllowed = new SpellsAllowedTableLoader(scanner.next());
		System.out.println("Please Enter Level <level> :== [1-29]");
		System.out.println( spellsAllowed.getRecord(scanner.nextInt()).toString());
		scanner.close();
	}



}
