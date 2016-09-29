package dmpro.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import dmpro.attributes.*;
import dmpro.character.classes.*;
import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.character.race.*;
import dmpro.core.Server;
import dmpro.data.loaders.ClassAttributeLoader;
import dmpro.data.loaders.ClassRaceLoader;
import dmpro.data.loaders.RaceAttributeLoader;
import dmpro.data.loaders.ClassAttributeLoader.ClassAttributeRecord;
import dmpro.data.loaders.ClassAttributeLoader.ListPossibleClassResults;
import dmpro.data.loaders.ClassRaceLoader.ClassRaceRecord;
import dmpro.data.loaders.ClassRaceLoader.ListPossibleClassRaceResults;
import dmpro.data.loaders.RaceAttributeLoader.ListPossibleRaceResults;
import dmpro.data.loaders.RaceAttributeLoader.RaceAttributeRecord;


public class CharacterBuildDirector {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Server application;
	
	CharacterModifierEngine characterModifierEngine;
	CharacterBuilder characterBuilder;
	private Scanner scanner = new Scanner(System.in);
	private Formatter output = new Formatter(System.out);
	AttributeLoader attributeLoader;
	
	private String sex=null, nickname=null, prefix=null, firstName=null, lastName=null, title=null;
	private List<String> suffixes = new ArrayList<String>();
	private int age = 0, height = 0, weight = 0;
	private Map<String,Attribute> attributes = new LinkedHashMap<String,Attribute>();
	private Race race;
	private List<CharacterClass> characterClasses = new ArrayList<CharacterClass>();;
	private CharacterClass characterClass;

	public CharacterBuildDirector(CharacterBuilder characterBuilder, Server application) {
		this.characterBuilder = characterBuilder;
		this.characterModifierEngine = new CharacterModifierEngine();
		this.application = application; 
	}

	/**
	 * This is the build character sequence in steps.
	 * Methods are in order below
	 * Acquire all information in command line wizards like interface
	 * send information to builder.
	 * 
	 * TODO: implement back/next for command line.
	 * 
	 */
	public void buildCharacter() {
		output.format("------------------- Welcome to the Character Director --------------------\n");
		output.format("-----------We will execute character build in sections using a builder ---\n");
		output.flush();
		characterBuilder.buildCharacterInitialize();
		
		
		getCharacterPersonalInformation();
		characterBuilder.buildCharacterPersonalInformation(sex, nickname, prefix, firstName, lastName, title, suffixes);
		
		getCharacterAttributes();
		characterBuilder.buildCharacterAttributes(attributes);
		
		getCharacterRace();
		characterBuilder.buildCharacterRace(race);
		
		getCharacterRacePersonalInformation();
		characterBuilder.buildCharacterRacePersonalInformation(age, height, weight);
		
		getCharacterClass();
		characterBuilder.buildCharacterClass(characterClasses);
		
		getCharacterClassPersonalInformation();
		characterBuilder.buildCharacterClassInformation();
		
		getCharacterHistory();
		characterBuilder.buildCharacterHistory();
		
		output.format("Character Created ID: %s\n", characterBuilder.getCharacter().getCharacterId());
		output.flush();
	}

	private void getCharacterPersonalInformation() {
		output.format("----------------------- Character Personal Information ------------------------\n");
		output.flush();
		do {
			output.format("Enter Character Sex: M or F >");
			output.flush();
			sex = scanner.next().toUpperCase().trim();
		} while ( (!sex.equals("M")) && (!sex.equals("F")) );

		do{
			output.format("Enter Character Nickname: >");
			output.flush();
			nickname= scanner.next();
		} while ( nickname.isEmpty() );
	
		do { 
			output.format("Enter Character Prefix (Sir, Mr, Mrs, etc.): >");
			output.flush();
			prefix = scanner.next();
		} while (prefix.isEmpty());

		do { 
			output.format("Enter Character First Name:");
			output.flush();
			firstName = scanner.next();
		} while (firstName.isEmpty());
			
		do { 
			output.format("Enter Character Last or Family Name: >");
			output.flush();
			lastName = scanner.next();
		} while (lastName.isEmpty());

		output.format("Enter Character Title:");
		output.flush();
		title = scanner.nextLine();

		output.format("Enter Suffixes (i.e. Esq., III, Jr, etc>>");
		output.flush();
		String suffix = null;
		suffix = scanner.nextLine();
		if (suffix != null) suffixes = Arrays.asList(suffix.split(" "));
	}

	/**
	 * TODO:	Add options to re-rolls
	 * 			Re-assign values 
	 * 			Possible show class, race restrictions before approval....
	 */
	private void getCharacterAttributes() {
		output.format("---------------- Character Attributes -------------------\n");
		int answer=0;
		String s;
		//TODO: allow input of rolls
		do {
			output.format( "Would you like to AutoGenerate Attribute rolls(1) or enter manually(2):>");
			output.flush();
			s = scanner.next();
			try {
				answer = Integer.parseInt(s); 
			} catch (NumberFormatException e) {
				//whatever...
			}
		} while ( answer!=1 && answer !=2 );
		
		output.format("Autogen it is!!!\n");
		AttributeRoller attributeRoller = new AttributeRoller();
		int[] attributeRolls = attributeRoller.attributeRolls();
		
		//TODO: hook up to central.
		//attributeLoader = new StrengthLoader();
		//attributes.put("Strength", (Strength) attributeLoader.getRecord( attributeRolls[0] ));
		attributes.put("Strength", application.getReferenceDataSet().getStrengthLoader().getRecord(attributeRolls[0]));
	    attributes.put("Intelligence", application.getReferenceDataSet().getIntelligenceLoader().getRecord( attributeRolls[1]));
		attributes.put("Wisdom", application.getReferenceDataSet().getWisdomLoader().getRecord( attributeRolls[2]));
		attributes.put("Dexterity", application.getReferenceDataSet().getDexterityLoader().getRecord(attributeRolls[3]));
		attributes.put("Constitution", application.getReferenceDataSet().getConstitutionLoader().getRecord( attributeRolls[4]));
		attributes.put("Charisma", application.getReferenceDataSet().getCharismaLoader().getRecord(attributeRolls[5]));
		
		showAttributes();
		
	}

	private void getCharacterRace() {
		output.format("-------------- Character Race ----------------\n");
		RaceAttributeLoader raceAttributeLoader = new RaceAttributeLoader();
		ListPossibleRaceResults listPossibleRaceResults = raceAttributeLoader.listPossibleRaces(attributes, sex);
		if (!listPossibleRaceResults.limitRecords.isEmpty()) {
			output.format("Attribute Restrictions on your Race:\n");
			for (RaceAttributeRecord raceAttributeRecord : listPossibleRaceResults.limitRecords) {
				output.format("\t%s\n",raceAttributeRecord.toString());
			}
		}
		RaceType raceType = null;
		for (RaceType r : listPossibleRaceResults.possibleRaces )
			output.format("Id: %d - Race: %s\n", r.raceIndex(), r.raceName());
		String raceIndex=null;
		while (true) {
			output.format("Select a Race Id: >");
			output.flush();
			raceIndex = scanner.next();
			if (raceIndex != null && raceIndex.matches("[0-9]")) {
				int index= Integer.parseInt(raceIndex);
				if (listPossibleRaceResults.possibleRaces.stream()
						.anyMatch(p -> p.raceIndex() == index))
					raceType = RaceType.ByIndex(index);
			}
			//raceType = RaceType.ByIndex(raceIndex);
			if (raceType != null) break;
		}
		switch(raceType) {
		case HUMAN:
			race  = new Human();
			break;
		case ELF:
			race = new Elf();
			break;
		case DWARF:
			race = new Dwarf();
			break;
		case HALFELF:
			race = new HalfElf();
			break;
		case GNOME:
			race = new Gnome();
			break;
		case HALFORC:
			race = new HalfOrc();
			break;
		case HALFLING:
			race = new Halfling();
			break;
		default:
			logger.log(Level.INFO, "Race not matched");
			output.format("ERROR - No race specified\n");
			output.flush();
			break;
		}
		output.format("Congratulations - you have selected to be an %s\n", race);
		output.flush();
	}

	private void getCharacterRacePersonalInformation() {
		//after class
		
	}
	
	/**
	 * TODO:	Add Multiclass support 
	 * 			Need Loader with rules.
	 */
	private void getCharacterClass() {
		output.format ("------------------------------- Character Class ------------------------\n");
		//TODO : CLASS RESTRICTION on RACE
		ClassRaceLoader classRaceLoader = new ClassRaceLoader();
		ListPossibleClassRaceResults possibleClassRaceResults = classRaceLoader.listPossibleClasses(race.getRace());
		if (!possibleClassRaceResults.limitRecords.isEmpty()) {
			output.format("Race Restrictions on your Class:\n");
			for (ClassRaceRecord classRaceRecord : possibleClassRaceResults.limitRecords) {
				output.format("\t%s\n",classRaceRecord.toString());
			}
		}
		output.format("-------------Valid Classes for ----------- %s\n", race.getRace());
		possibleClassRaceResults.possibleClasses.stream().forEach(p -> output.format("\t%s\n",p.className));
		//Class Restriction by Attribute
		showAttributes();
		output.format("-------------Checking Attribute Restrictions -----------------\n");
		output.flush();
		
		ClassAttributeLoader classAttributeLoader = new ClassAttributeLoader();
		ListPossibleClassResults listPossibleClassResults = classAttributeLoader.listPossibleClasses(attributes);
		if (!listPossibleClassResults.limitRecords.isEmpty()) {
			output.format("Attribute Restrictions on your Class:\n");
			for (ClassAttributeRecord classAttributeRecord : listPossibleClassResults.limitRecords) {
				output.format("\t%s\n",classAttributeRecord.toString());
			}
		}
		output.format("-------------Valid Classes for Given Attributes-----------\n");
		listPossibleClassResults.possibleClasses.stream()
		.forEach(p -> output.format("\t%s\n",p.className));
		
		//Class Restriction by Attribute
		output.format("-------------Merging Lists-----------\n");
		
		List<CharacterClassType> possibleClasses = 
				listPossibleClassResults.possibleClasses.stream()
				.filter(p -> possibleClassRaceResults.possibleClasses
						.stream()
						.anyMatch(b -> b.className.equals(p.className)))
				.collect(Collectors.toList());
		
//		possibleClasses.stream().forEach(p -> output.format("Possible Class: %s\n",p.className));
		
		CharacterClassType characterClassType = null;
		for (CharacterClassType cct : possibleClasses) {
			output.format("ID: %d\tClass: %s\n" , cct.classIndex, cct.className);
		}
		String classIndex=null;
		while (true) {
			output.format("Select a class Id: >");
			output.flush();
			classIndex = scanner.next();
			if (classIndex != null && classIndex.matches("[0-9]")) {
				int index= Integer.parseInt(classIndex);
				if (possibleClasses.stream()
						.anyMatch(p -> p.classIndex == index))
					characterClassType = characterClassType.ByIndex(index);
			}
			//classType = classType.ByIndex(classIndex);
			if (characterClassType != null) break;
		}
//		int classIndex = -1;
//		while (true) {
//			output.print("Enter Class ID: ");
//			classIndex = scanner.nextInt();
//			characterClassType = CharacterClassType.ByIndex(classIndex);
//			if ( characterClassType != null) break;
//		}
		output.format("Character Class Selected is %s, %s, Id=%d\n",
				characterClassType, characterClassType.className, characterClassType.classIndex);
		
 		switch (characterClassType ) {
 		case MAGICUSER:
 			characterClass = new MagicUser();
 			break;
 		case FIGHTER:
 			characterClass = new Fighter();
 			break;
 		case THIEF:
 			characterClass = new Thief();
 			break;
 		case CLERIC:
 			characterClass = new Cleric();
 			break;
 		default:
 			logger.log(Level.INFO, "Failed to select class");
 			output.format("ERROR -- no Class Specificied");
 		}
 			
 		characterClasses.add(characterClass);
 		//TODO add multiclass;
 		output.flush();
	}
	
	private void getCharacterClassPersonalInformation() {
		// TODO Auto-generated method stub

	}
	
	private void getCharacterHistory() {
		// TODO Auto-generated method stub

	}

	private void showAttributes() {
		attributes.values().stream().forEach(p -> output.format("%s - %d\n", p.attributeName, p.abilityScore));
		output.flush();
	}

	/**
	 * @return the scanner
	 */
	public Scanner getInput() {
		return scanner;
	}

	/**
	 * @param scanner the scanner to set
	 */
	public void setInput(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * @return the output
	 */
	public Formatter getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(Formatter output) {
		this.output = output;
	}
	
}
