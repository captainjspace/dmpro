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
import dmpro.character.classes.CharacterClassType;
import dmpro.character.race.*;
import dmpro.core.ReferenceDataSet;
import dmpro.core.Server;
import dmpro.data.loaders.ClassAttributeLoader;
import dmpro.data.loaders.ClassRaceLoader;
import dmpro.data.loaders.RaceAttributeLoader;
import dmpro.data.loaders.ClassAttributeLoader.ClassAttributeRecord;
import dmpro.data.loaders.ClassAttributeLoader.ListPossibleClassResults;
import dmpro.data.loaders.ClassRaceRecord;
import dmpro.data.loaders.ClassRaceLoader.ListPossibleClassRaceResults;
import dmpro.data.loaders.RaceAttributeLoader.ListPossibleRaceResults;
import dmpro.data.loaders.RaceAttributeRecord;
import dmpro.data.loaders.RaceClassAgeLoader;
import dmpro.data.loaders.RaceSizeLoader;


public class CharacterBuildDirector {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Server application;

	//	CharacterModifierEngine characterModifierEngine;
	CharacterBuilder characterBuilder;
	private Scanner scanner = new Scanner(System.in);
	private Formatter output = new Formatter(System.out);
	AttributeLoader attributeLoader;

	//TODO: evalutate if these would be better as locals -- had to force reset them when creating multiple characters.
	private String sex=null, nickname=null, prefix=null, firstName=null, lastName=null, title=null;
	private List<String> suffixes;;
	private int age = 0, height = 0, weight = 0;
	private Map<String,Attribute> attributes = new LinkedHashMap<String,Attribute>();
	private Race race;
	private List<CharacterClass> characterClasses = new ArrayList<CharacterClass>();;
	private CharacterClass characterClass;
	private ReferenceDataSet referenceDataSet;

	public CharacterBuildDirector(CharacterBuilder characterBuilder, Server application) {
		this.characterBuilder = characterBuilder;
		//		this.characterModifierEngine = new CharacterModifierEngine();
		this.application = application; 
		this.referenceDataSet = application.getReferenceDataSet();
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
		characterBuilder.buildCharacterRacePersonalInformation();

		getCharacterClass();
		characterBuilder.buildCharacterClass(characterClasses);

		getCharacterClassPersonalInformation();
		characterBuilder.buildCharacterClassInformation(age, height, weight);

		getCharacterHistory();
		characterBuilder.buildCharacterHistory();

		output.format("Character Created ID: %s\n", characterBuilder.getCharacter().getCharacterId());
		output.flush();
	}

	private void getCharacterPersonalInformation() {
		//reset
		sex=null; nickname=null; prefix=null; firstName=null; lastName=null; title=null;
		suffixes = new ArrayList<String>();

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

		do { 
			output.format("Enter Character Title:");
			output.flush();
			title = scanner.nextLine();
		}  while (title.isEmpty());

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
		attributes = new LinkedHashMap<String,Attribute>();

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
		//attributeRolls = new int[] {18,18,18,18,18,18}; //for testing

		attributes.put("Strength", referenceDataSet.getStrengthLoader().getRecord(attributeRolls[0]));
		attributes.put("Intelligence", referenceDataSet.getIntelligenceLoader().getRecord( attributeRolls[1]));
		attributes.put("Wisdom", referenceDataSet.getWisdomLoader().getRecord(attributeRolls[2]));
		attributes.put("Dexterity", referenceDataSet.getDexterityLoader().getRecord(attributeRolls[3]));
		attributes.put("Constitution", referenceDataSet.getConstitutionLoader().getRecord( attributeRolls[4]));
		attributes.put("Charisma", referenceDataSet.getCharismaLoader().getRecord(attributeRolls[5]));

		showAttributes();

	}

	private void getCharacterRace() {
		race = null;

		output.format("-------------- Character Race ----------------\n");
		RaceAttributeLoader raceAttributeLoader = referenceDataSet.getRaceAttributeLoader();
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

		//TODO: Get this to work... for some reason the classes method is not seeing the race..
		//this.race = raceType.newRace();

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
		characterClasses = new ArrayList<CharacterClass>();

		output.format ("------------------------------- Character Class ------------------------\n");
		//CLASS RESTRICTION on RACE
		ClassRaceLoader classRaceLoader = referenceDataSet.getClassRaceLoader();
		ListPossibleClassRaceResults possibleClassRaceResults = classRaceLoader.listPossibleClasses(race.getRaceType());
		if (!possibleClassRaceResults.limitRecords.isEmpty()) {
			output.format("Race Restrictions on your Class:\n");
			for (ClassRaceRecord classRaceRecord : possibleClassRaceResults.limitRecords) {
				output.format("\t%s\n",classRaceRecord.toString());
			}
		}
		output.format("-------------Valid Classes for ----------- %s\n", race.getRaceType());
		possibleClassRaceResults.possibleClasses.stream().forEach(p -> output.format("\t%s\n",p.className));
		//Class Restriction by Attribute
		showAttributes();
		output.format("-------------Checking Attribute Restrictions -----------------\n");
		output.flush();

		ClassAttributeLoader classAttributeLoader = referenceDataSet.getClassAttributeLoader();
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
					characterClassType = CharacterClassType.ByIndex(index);
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


		//TODO: put classes in enum
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
		// TODO page 12 DMG, probably create loader.
		age = 0; height = 0; weight = 0;
		RaceClassAgeLoader raceClassAgeLoader = referenceDataSet.getRaceClassAgeLoader();
		RaceSizeLoader raceSizeLoader = referenceDataSet.getRaceSizeLoader();
		//TODO: make loop when adding multiclass to get the oldest...
		age = raceClassAgeLoader.getAge(this.race.getRaceType(),  characterClasses.get(0).getCharacterClassType());
		height = raceSizeLoader.getHeight(this.race.getRaceType());
		weight = raceSizeLoader.getWeight(this.race.getRaceType());
		output.format("You are a wee child of %d years old!  and you are %d inches tall and weigh %d lbs\n", age,height,weight);
		return;
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
