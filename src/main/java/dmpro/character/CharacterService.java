package dmpro.character;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import dmpro.character.classes.CharacterClassType;
import dmpro.character.managementaction.CharacterManagementActions;
import dmpro.character.managementaction.ManagementAction;
import dmpro.character.race.Halfling;
import dmpro.core.Application;
import dmpro.core.Server;
import dmpro.core.StubApp;
import dmpro.data.loaders.ResourceLoader;
import dmpro.data.loaders.WeaponItemLoader;
import dmpro.items.MagicItem;
import dmpro.items.TreasureItemViewer;
import dmpro.serializers.CharacterGsonService;
import dmpro.utils.Dice;
import dmpro.utils.Die;
import dmpro.utils.FileUtils;




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
	private String characterDir = dataDirectory + "characters/";
	
	
	
	Map<String,Character> characters = new HashMap<String,Character>();
	Map<String,String> charactersJSON = new HashMap<String,String>();
	Map<String,String> listCharacters = new HashMap<String,String>();
	
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
	 * @param application handle to application 
	 */
	public CharacterService(Server application) {
		this.application = application;
		this.loadAllCharacters();
		this.characterBuilder = new PCCharacterBuilder();
		this.characterBuildDirector = new CharacterBuildDirector(characterBuilder, this.application);
		this.characterModifierEngine = new CharacterModifierEngine(this.application);
		this.xpProcessor = new XPProcessor(this.application);
	}
	public void run() {}
	
	private void refreshServiceData() {
		for ( Entry<String, Character> e: this.characters.entrySet()) {
			this.charactersJSON.put(e.getKey(), gson.toJson(e.getValue()));
			this.listCharacters.put(e.getKey(),
					String.join(" ", e.getValue().getPrefix() ,
							e.getValue().getFirstName(),
							e.getValue().getLastName()));
		}
	}
	
	public void loadAllCharacters() {
		List<Path> characterFiles = null;
		try {
			characterFiles = FileUtils.listSourceFiles(FileSystems.getDefault().getPath(characterDir));
			//BufferedReader reader;
			for (Path path : characterFiles) {
				character = null;
				try (BufferedReader reader = Files.newBufferedReader(path) ){
					String chStr = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
					character = gson.fromJson(reader, Character.class);
					//character = characterModifierEngine.processModifiers(character);
					this.characters.put(character.getCharacterId(),character);
					this.charactersJSON.put(character.getCharacterId(), chStr);
					//for Rest API
					this.listCharacters.put(character.getCharacterId(),
							String.join(" ", character.getPrefix() ,
							                  character.getFirstName(),
							                  character.getLastName()));
					
					//saveCharacter(character);
					reader.close();
				} catch (IOException io) {
					logger.log(Level.WARNING, "File not Found: " + path,io);
				} catch (Exception e) {
					logger.log(Level.WARNING, "Gson error?" + character.getCharacterId(),e);
				} 
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, " IO Error Loading characters ");
		}
		if (characterFiles == null) {
			logger.log(Level.WARNING, "No character files found " + characterDir);
			return;
		}
		
		
	}
	public Map<String,Character> getCharacters() {
		return characters;
	}
	public Map<String,String> getCharactersJSON() {
		return charactersJSON;
	}
	public Map<String,String> getCharacterList(){
		return listCharacters;
	}
	
	
	public Character getCharacter(String characterId) {
		// need to offer a reference
		Character character = null;
		if (characters.containsKey(characterId)) {
			logger.log(Level.INFO, "Retrieveing " + characterId + " from character map");
			return characters.get(characterId);
		} else {
			//lazyload
			character = loadCharacter(characterId);
			
			if (character == null)
				logger.log(Level.WARNING, "Character " + characterId + " does not exist: Check Id, and Directories");
			else 
				characters.put(character.characterId, character);
			//probably throw exception to create character or evaluate id, config
		}
		if (character != null) return character;
		else throw new RuntimeException(characterId + " does not exist - Can't get it");
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
		while (!character.getRequiredActions().isEmpty()) {
		    characterManagementAction = character.getRequiredActions().remove(0);
			logger.log(Level.INFO, "executing managementAction " + characterManagementAction);
			ManagementAction action = characterManagementAction.getManagementAction();
			character = action.execute(character, this.application, this.input, this.output);
			character = characterModifierEngine.processModifiers(characterBuilder.getCharacter());
			saveCharacter(character); //save after each mod.
			this.output.format("Saved Character %s", character.getCharacterId());
		}
		return character;
	}

	public Character saveCharacter(Character character) {

		String file = dataDirectory + "characters/c" + character.characterId + ".json";
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
		characters.put(character.getCharacterId(), character); //refresh memory map
		refreshServiceData();
		return character;
	}
	
	public Character loadCharacter(String characterId){
		Character load = null;
		String file = dataDirectory + "characters/c" + characterId + ".json";
		try {
			FileReader reader = new FileReader(file);
			load = gson.fromJson(reader, Character.class);
		
			System.out.println(load.getFirstName());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "File not Found: " + file +
					" Character not loaded CharacterId:" + characterId);
			return null;
		}
		if (!characters.containsKey(load.getCharacterId())) {
			logger.log(Level.INFO, "Successfully loaded characterId:" + characterId, characterId);
			characters.put(load.getCharacterId(), load);
		} else {
			//log warning - already loaded character may be modified
			logger.log(Level.WARNING, "Character already loaded characterId:" + characterId, characterId);
			//TODO:check timestamps?
			load = characters.get(characterId);
			//should we return character from map?  or ask if the intent is to reload
		}
		return load; //null indicates action on other side.
	}
	
	public Character testLoad(String json) {
		Character test = null;
		test = gson.fromJson(json, Character.class);
		return test;
	}
	
	//HACK TESTING CODE...
	public static void main(String[] args) {
	  Server application = new StubApp();
	  application.getReferenceDataSet().run();
	  CharacterService characterService = new CharacterService(application);
	  //
	  String s = "{\"suffixes\":[\"hjkl\"],\"name\":\"hjkl\",\"prefix\":\"hjkl\",\"firstName\":\"hjkl\",\"lastName\":\"hjkl\",\"title\":\"hjkl\",\"sex\":\"M\"}";
	  Character c = characterService.testLoad(s);
	  System.out.format("%s ,  %s, %s, %s\n", c.getName(), c.getFirstName(), c.getLastName(), c.getSex());
	  //System.out.println(characterService.getCharacter("100").toString());
	  //characterService.loadAllCharacters();
	  characterService.getCharactersJSON().keySet().stream().forEach(key -> System.out.println(key));
	  //Character c = characterService.createCharacter();
//	  Character c = characterService.loadCharacter(args[0].trim());
//	  c = characterService.characterModifierEngine.processModifiers(c);
//	  //c.characterId=args[1];
//	  characterService.saveCharacter(c);
//	  //diff output current loses dailySpells and spellBook
//	  
//	  System.out.println(c.toString());
//	  
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

	/**
	 * @return the characterModifierEngine
	 */
	public CharacterModifierEngine getCharacterModifierEngine() {
		return characterModifierEngine;
	}

	/**
	 * @return the xpProcessor
	 */
	public XPProcessor getXpProcessor() {
		return xpProcessor;
	}

	/**
	 * @param c
	 */
	public Character initCharacter(Character c) {
		return saveCharacter(xpProcessor.evaluateExperience(characterModifierEngine.processModifiers(c)));
	}



	
}
