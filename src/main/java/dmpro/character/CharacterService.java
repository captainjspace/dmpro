package dmpro.character;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import dmpro.attributes.AttributeLoader;
import dmpro.attributes.Charisma;
import dmpro.attributes.CharismaLoader;
import dmpro.attributes.Constitution;
import dmpro.attributes.ConstitutionLoader;
import dmpro.attributes.Dexterity;
import dmpro.attributes.DexterityLoader;
import dmpro.attributes.Intelligence;
import dmpro.attributes.IntelligenceLoader;
import dmpro.attributes.Strength;
import dmpro.attributes.StrengthLoader;
import dmpro.attributes.Wisdom;
import dmpro.attributes.WisdomLoader;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.MagicUser;
import dmpro.character.classes.Thief;
import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.character.managementaction.CharacterManagementActions;
import dmpro.character.managementaction.ManagementAction;
import dmpro.character.race.Halfling;
import dmpro.core.Application;
import dmpro.core.Server;
import dmpro.core.StubApp;
import dmpro.data.loaders.ResourceLoader;
import dmpro.data.loaders.WeaponItemLoader;
import dmpro.items.TreasureItemViewer;
import dmpro.serializers.CharacterGsonService;
import dmpro.utils.Dice;
import dmpro.utils.Die;




/**
 * CharacterService.java
 * Responsible for CRUD operations of Character objects.
 * This service uses JSON format to seriealize and deserialize the character from the file system
 * <p>
 * Could be refactored to use Mongo or key-val store or db.
 * 
 */
//@SuppressWarnings("unused")
public class CharacterService implements Runnable, ResourceLoader {
	/**
	 * Mostly a test class for modifying characters and input/output of JSON
	 */
	private Logger logger = Logger.getLogger(this.getClass().toString());
	private Server application;
	private CharacterModifierEngine characterModifierEngine;
	private CharacterBuildDirector characterBuildDirector;
	private CharacterBuilder characterBuilder;
	private XPProcessor xpProcessor;
	private Scanner input;
	private Formatter output;
	
	
	
	Map<String,Character> characters = new HashMap<String,Character>();
	private Die die= new Die();
	private Character character;
	private Gson gson = CharacterGsonService.getCharacterGson();
	
	/**
	 * Constructor to Load Characters from Save State when Game comes on line
	 * Probably should be on demand load - but for now we'll retrieve what we have
	 * 
	 */
	
	/**
	 * test creation constructor
	 */
	public CharacterService() {
		//testing set up
		
		//this.characterModifierEngine = new CharacterModifierEngine(new StubApp());
	}
	
	/**
	 * application mode
	 * @param application
	 */
	public CharacterService(Server application) {
		this.application = application;
		
		this.characterBuilder = new PCCharacterBuilder();
		this.characterBuildDirector = new CharacterBuildDirector(characterBuilder, this.application);
		this.characterModifierEngine = new CharacterModifierEngine(this.application);
		this.xpProcessor = new XPProcessor(this.application);
	}
	public void run() {}
	
	public void loadAllCharacters() {
		
	}
	
	public Character getCharacter(String characterId) {
		// need to offer a reference
		Character character = null;
		if (!characters.containsKey(characterId)) {
			logger.log(Level.INFO, "Retrieveing " + characterId + " from character map");
			return characters.get(characterId);
		} else {
			//lazyload
			character = loadCharacter(characterId);
			if (character == null)
				logger.log(Level.WARNING, "Character " + characterId + " does not exist: Check Id, and Directories");
			//probably throw exception to create character or evaluate id, config
		}
		return character;
	}
	
	/**
	 * Wraps input streams for use with sockets, stdio etc.
	 * @param input java.util.Scanner
	 * @param output java.util.Formatter
	 * @return dmpro.character.Character
	 */
	public Character createCharacter(Scanner input, Formatter output) {
		this.input = input;
		this.output = output;
		characterBuildDirector.setInput(input);
		characterBuildDirector.setOutput(output);
		return createCharacter();
	}
	public Character createCharacter() {
		//CharacterBuilder pCCharacterBuilder = new PCCharacterBuilder();
		//characterBuildDirector = new CharacterBuildDirector(pCCharacterBuilder);
		characterBuildDirector.buildCharacter();
		character = characterBuilder.getCharacter();
		character = characterModifierEngine.processModifiers(characterBuilder.getCharacter());
		character = xpProcessor.evaluateExperience(character);
		character = processManagementActions(character);
		saveCharacter(character);
		return character;
	}

	private Character processManagementActions(Character character) {
		CharacterManagementActions characterManagementAction;
	
		while (!character.getRequiredActions().isEmpty())
		{
		    characterManagementAction = character.getRequiredActions().remove(0);
			logger.log(Level.INFO, "executing managementAction " + characterManagementAction);
			ManagementAction action = characterManagementAction.getManagementAction();
			character = action.execute(character, this.application, this.input, this.output);
			saveCharacter(character); //save after each mod.
		}
		return character;
	}

	public Character saveCharacter(Character character) {
		
//		java.util.Formatter formatter = new java.util.Formatter();
//		String file = formatter.format("data/characters/c%.0f.json", character.characterId).toString();
//		formatter.close();

		String file = dataDirectory + "characters/c" + character.characterId + ".json";
		//String file = "src/main/resources/data/characters/c" + character.getCharacterId() + ".json";
		//probably should do time/date checks to see if modified version is later than last save
		character.setModified(System.currentTimeMillis());
		String json = null;
		try {
			json=gson.toJson(character);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "JSON failure", e);
			//e.printStackeTrace();
		}
		
		try {
			FileWriter f = new FileWriter(file);
			f.write(json);
			f.close();
			logger.log(Level.INFO, "Successfully Saved " + character.characterId);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to Save Character " + character.getCharacterId(), e);
		}

		return character;
	}
	
	public Character loadCharacter(String characterId){
		Character load = null;
		String file = dataDirectory + "characters/c" + characterId + ".json";
		try {
			FileReader reader = new FileReader(file);
			load = gson.fromJson(reader, Character.class);
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "File not Found: " + file +
					" Character not loaded CharacterId:" + characterId, e);
		}
		if (!characters.containsKey(load.getCharacterId())) {
			logger.log(Level.INFO, "Succcessfully loaded characterId:" + characterId, characterId);
			characters.put(load.getCharacterId(), load);
		} else {
			//log warning - already loaded character may be modified
			logger.log(Level.WARNING, "Character already loaded characterId:" + characterId, characterId);
			//should we return character from map?  or ask if the intent is to reload
		}
		return load; //null indicates action on other side.
	}
	
	//HACK TESTING CODE...
	public static void main(String[] args) {
	  Server application = new StubApp();
	  application.getReferenceDataSet().run();
	  CharacterService characterService = new CharacterService(application);
	  Character c = characterService.createCharacter();
	  //Character c = characterService.loadCharacter(args[0].trim());
	  //c.characterId=args[1];
	  characterService.saveCharacter(c);
	  //diff output current loses dailySpells and spellBook
	  
	  System.out.println(c.toString());
	  
	  System.exit(0);
	  
	  
//	  try {	
//		  if (args[0].equals("load")) {
//			  int id = Integer.parseInt(args[1]);
//		//	  characterService.character = characterService.loadCharacter(id);
//			  System.out.println(characterService.character.toString());
//			  for (CharacterClassType k : characterService.character.getClasses().keySet() ) {
//				 
//				 if (characterService.character.classes.get(k).getCharacterClassType() == CharacterClassType.MAGICUSER) {
//					 System.out.println("FOUND MAGIC USER");
//					 MagicUser mu = (MagicUser) (characterService.character.classes.get(k));
//					 mu.processExperience(300000);
//					 characterService.character.classes.put(k,mu);
//				 }
//				characterService.character.classes.get(k).processExperience(50000);
//			  }
//			  characterService.saveCharacter(characterService.character);
//			  System.exit(0);
//		  } 
//	  } catch (ArrayIndexOutOfBoundsException ae) {}
//
////	  try {
//		  if (args[0].equals("gentest")) {
//			  
//			  Random r = new Random();
//			  //AttributeLoader al;
//			 // characterService.character.characterId = r.nextInt(10000);
//			  
//			  characterService.character.name="TIM the Halfling Thief";
//			  characterService.character.race=new Halfling();
//			  CharacterClass cc = new Thief();
//			  System.out.println(cc.getClass());
//			  cc.processExperience(564000);
//			  characterService.character.classes.put(cc.getCharacterClassType(),cc);
//			 
//			  characterService.character.setHitPoints(); //looks at classes, hitDiceRecord lists
//			  //initialize character
//			  characterService.die.setDieType(Dice.d100);
//			  characterService.character.age=characterService.die.roll();
//			  characterService.die.setDieType(Dice.d6);
//			  characterService.character.alignment = Alignment.values()[characterService.die.roll()];
//			  
//			  AttributeLoader al;
//			  al = new StrengthLoader();
//			  characterService.c.strength = (Strength) al.getRecord( characterService.die.roll(3) );
//			  al = new IntelligenceLoader();
//			  characterService.c.intelligence = (Intelligence) al.getRecord( characterService.die.roll(3));
//			  al = new WisdomLoader();
//			  characterService.c.wisdom = (Wisdom) al.getRecord( characterService.die.roll(3));
//			  al = new DexterityLoader();
//			  characterService.c.dexterity = (Dexterity) al.getRecord( 17);
//			  al = new ConstitutionLoader();
//			  characterService.c.constitution = (Constitution) al.getRecord( characterService.die.roll(3));
//			  al = new CharismaLoader();
//			  characterService.c.charisma = (Charisma) al.getRecord( characterService.die.roll() * 3);
			  
//			  String[] level1 = { "sleep", "dancing lights", "read magic", "magic missile",
//					  				"levitate", "knock","invisibility","web",
//			  						"fireball","haste","fly", "lightning bolt"};
//			  List<String> l = Arrays.asList(level1);
////			  l.addAll(Arrays.asList(level2));
////			  l.addAll(Arrays.asList(level3));
//			  for (String sp : level1) {
//				  cm.c.addSpellBook(sp);
//			  }
			  //daily spells have a fixed number of slots and
			  //must choose spells available in the spell book
//			  cm.c.readSpellBook();
//			  WeaponItemLoader wil=new WeaponItemLoader();
//			  characterService.character.getEquipment().add(wil.getWeaponItem("Sword, Short"));
//			 // characterService.character.processAttributeModifiers();
//			  characterService.character.processAbilityModifiers();
//			  characterService.saveCharacter(characterService.character);
//			  for (CharacterClassType k : characterService.character.getClasses().keySet() ) {
//
//				  if (characterService.character.classes.get(k).getCharacterClassType() == CharacterClassType.MAGICUSER) {
//					  System.out.println("FOUND MAGIC USER");
//					  MagicUser mu = (MagicUser) (characterService.character.classes.get(k));
//					  mu.processExperience(100000);
//					  characterService.character.classes.put(k,mu);
//				  }
//			  }
//			  System.exit(0);
//			  TreasureItemViewer tiv = new TreasureItemViewer();
//			  tiv.load();
//			  characterService.character.equipment.addAll(tiv.getTreasureItems().stream()
//					  .filter(p -> p.recipient.equalsIgnoreCase("elfie"))
//					  .collect(Collectors.toList()));
//			  characterService.saveCharacter(characterService.character);
//		  }
//	  } catch (ArrayIndexOutOfBoundsException ae) {}

	}

	/**
	 * Need to be able to set the IO streams
	 * @return the characterBuildDirector
	 */
	public CharacterBuildDirector getCharacterBuildDirector() {
		return characterBuildDirector;
	}

	
}
