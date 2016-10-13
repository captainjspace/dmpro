package dmpro.data.loaders;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SpellsAllowedTableLoader extends TSVLoader implements ResourceLoader {

	List<SpellsAllowedRecord> spellsAllowed = new ArrayList<SpellsAllowedRecord>();
	
	public SpellsAllowedTableLoader() {
		super(SpellsAllowedRecord.class, "spells-useable.tsv");
	}
	

	public SpellsAllowedRecord getRecord(String characterClass, int level) {
		return tsvTable.stream()
				.map(p -> (SpellsAllowedRecord) p)
				.filter( p -> p.getCharacterClassName().toLowerCase().equals(characterClass.toLowerCase()))
				.filter(p -> p.getExperienceLevel() == level)
				.findFirst().get();
	}
	
	public static void main(String[] args) {

		SpellsAllowedTableLoader spellsAllowed = new SpellsAllowedTableLoader();
		Scanner scanner = new Scanner(System.in);
		System.out.println("------------Welcome to the Spells Useable Utility-------------");
		System.out.println("Please Enter Class <class> :== [magic-user, cleric, illusionist, druid, ranger, paladin, bard]");
		String characterClass = scanner.next();
		System.out.println("Please Enter Level <level> :== [1-29]");
		System.out.println( spellsAllowed.getRecord(characterClass, scanner.nextInt()).toString());
		scanner.close();
	}



}
