package dmpro.character;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import dmpro.attributes.Attribute;
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
import dmpro.character.classes.CharacterClassType;
import dmpro.character.managementaction.CharacterManagementActions;
import dmpro.character.classes.ICharacterClass;
import dmpro.character.classes.ProficiencyData;
import dmpro.character.race.Race;
import dmpro.items.CoinItem;
import dmpro.items.Item;
import dmpro.items.ProtectionItem;
import dmpro.items.WeaponItem;
import dmpro.items.CoinItem.CoinType;
import dmpro.items.Item.ItemType;
import dmpro.items.WeaponItem.WeaponType;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.Modifiable;
import dmpro.modifier.Modifier;
import dmpro.spells.Spell;
import dmpro.spells.SpellLibrary;
import dmpro.world.Environment;
/**
 * Character.java
 * Design Pattern: composite - composed of smaller data objects mostly for cleanliness....
 * The character owns all - at any given moment the character must evaluate all activeeEffects.
 * activeEffects accumulates all attributes and abilities
 * attributes and abilities modifiers are evaluated at runtime
 * some may be race based, class based, equipment based (magic, proficiency or specialized)
 * some may be spell based.
 * modifiers are processed in priority order and the runtime values are then used to modify rolls
 * rolls - for saving throws, to hit, damage or any ability checks
 * Character current state is output to JSON.  
 * Node watches the JSON files - and auto-updates the player, and dm's applications (angular)
 * 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * 
 */


public class Character implements Modifiable {
	long playerId;
	
	private static final boolean DEBUG=false;
	long created;
	long modified;
	String characterId;
	boolean playerCharacter;

	//character name section
	//move to CharacterPersonalInformation
	String name;  //quickname
	String prefix;
	String firstName;
	String middleName;
	String lastName;
	String title;
	List<String> suffixes = new ArrayList<String>();
	
	//person - requires race
	int age;
	int height; //inches
	int weight; //pounds
	String sex; 
	
	//abilities - input or autogen
	//note helper attribute methods below getAttribues[] and getAttributeAsMap
	Strength strength;
    Intelligence intelligence;
	Wisdom wisdom;
	Dexterity dexterity;
	Constitution constitution;
	Charisma charisma;
	int resurrectionCount;
	//boolean experienceBonus; //does not belong here 
	
	Race race;
	Map<CharacterClassType,CharacterClass> classes = new HashMap<CharacterClassType,CharacterClass>();
	List<Language> languages = new ArrayList<Language>();
	
	
	/* Class impacts possible alignment */
	//TODO: create data table for rule
	Alignment alignment;
	
	
	/* Movement and Encumbrance */
	long movementRate;; //to be calced at runtime
	int encumbranceState; //modifies movementRate
	
	/* Combat */
	List<WeaponType> proficiencies = new ArrayList<WeaponType>();
	CombatStatistics combatStats;
	int maxHitPoints;
	int currentHitPoints;
	
	
	/* Saving Throws - determined by level and class, race, and modifiers */
	Map<SavingThrowType,SavingThrow> savingThrowMap = new HashMap<SavingThrowType,SavingThrow>();
	
	/* active effects */
	List<Modifier> activeModifiers = new ArrayList<Modifier>();
	
	/* Queue of Executable actions */
	List<CharacterManagementActions> requiredActions = new ArrayList<CharacterManagementActions>();
	
	/* Character inventory */
	List<Item> inventory = new ArrayList<Item>();  //general inventory
	List<Item> equippedItems = new ArrayList<Item>(); //equipped
	SlottedItems slottedItems = new SlottedItems();  //limited equipped
	
	// probably remove thsi
	List<ProtectionItem> protections = new ArrayList<ProtectionItem>();
	
	//TODO: Move spells to Spell Casters...
	List<Spell> spellBook = new ArrayList<Spell>();
	List<Spell> dailySpells = new ArrayList<Spell>();
	
	
	AdventureParty currentAdventureParty;
	Environment currentEnvironment;
	
	public Character(){
	}
	

	/*SPELL Methods to be moved to engines */
	public void addSpellBook(String name) {
		SpellLibrary spellLibrary = SpellLibrary.getSpellLibrary();
		spellBook.add(spellLibrary.getSpell(name));
		System.out.println("----Study, Study, Study ---> Done! Adding Spell " + name + " to your Spell Book");
		spellLibrary = null;
	}
	
	public void addDailySpell(String name){
		try { 
			dailySpells.add(
					spellBook.stream()
					.filter(a -> a.getSpellName().equalsIgnoreCase(name))
					.findFirst().get()); 
		} catch (NoSuchElementException e) {
			String msg = "You don't know that spell - go back to the library!!!";
			System.out.println(msg);
		}
		
	}
	
	public void readSpellBook() {
		System.out.println("----------SPELL BOOK--------------");
		spellBook.stream().forEach(a -> System.out.println(a.toString()));
	}
	
	public void castSpell(String name) {
		Spell cast = null;
		try { 
			cast = dailySpells.stream()
					.filter(a -> a.getSpellName().equalsIgnoreCase(name))
					.findFirst().get();
			dailySpells.remove(cast);
			System.out.println("Casting " + cast.getSpellName());
				
		} catch (NoSuchElementException e) {
			String msg = "You don't have that spell in your dialy list -- go read your spell book!!!";
			System.out.println(msg);
		}
	}
	/* End Spell Methods */
	
	//move to CharacterLevelingManager
//	public void setHitPoints() {
//		// TODO Auto-generated method stub
//		this.setMaxHitPoints(classes.entrySet().stream()
//				.map(cls -> cls.getValue().getHitDieRecord())
//				.flatMap(hit -> hit.stream())
//				.reduce(0,Integer::sum)
//				);
//		//sort out constitution bonus and division for multiclass.
//		//previous incarnation with list
////		this.setMaxHitPoints(
////				classes.
////				classes.stream().map(cls -> cls.hitDieRecord)
////				.flatMap(hit ->hit.stream()).reduce(0,Integer::sum)
////				);
//	}
//	
	//// getter and setters
	public String getCharacterId() {
		return characterId;
	}
	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}
	public boolean isPlayerCharacter() {
		return playerCharacter;
	}
	public void setPlayerCharacter(boolean playerCharacter) {
		this.playerCharacter = playerCharacter;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getSuffixes() {
		return suffixes;
	}
	public void setSuffixes(List<String> suffixes) {
		this.suffixes = suffixes;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Strength getStrength() {
		return strength;
	}
	public void setStrength(Attribute strength) {
		this.strength = (Strength)strength;
	}
	public Intelligence getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(Attribute intelligence) {
		this.intelligence = (Intelligence)intelligence;
	}
	public Wisdom getWisdom() {
		return wisdom;
	}
	public void setWisdom(Attribute wisdom) {
		this.wisdom = (Wisdom)wisdom;
	}

	public Dexterity getDexterity() {
		return dexterity;
	}
	public void setDexterity(Attribute dexterity) {
		this.dexterity = (Dexterity) dexterity;
	}
	public Constitution getConstitution() {
		return constitution;
	}
	public void setConstitution(Attribute constitution) {
		this.constitution = (Constitution)constitution;
	}
	public Charisma getCharisma() {
		return charisma;
	}
	public void setCharisma(Attribute charisma) {
		this.charisma = (Charisma) charisma;
	}

	public Alignment getAlignment() {
		return alignment;
	}
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	public int getMaxHitPoints() {
		return maxHitPoints;
	}
	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}
	public int getArmorClass() {
		return getCombatStats().getArmorClass();
	}
//	public void setArmorClass(int armorClass) {
//		this.armorClass = armorClass;
//	}
	public List<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	public Map<CharacterClassType, CharacterClass> getClasses() {
		return classes;
	}
	public void setClasses(Map<CharacterClassType, CharacterClass> classes) {
		this.classes = classes;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public List<Item> getEquipment() {
		return inventory;
	}
	public void setEquipment(List<Item> inventory) {
		this.inventory = inventory;
	}
	public void addToInventory(Item item) {
		this.inventory.add(item);
	}
	
//	public List<SpellResistance> getSpellResistance() {
//		return spellResistance;
//	}
//	public void setSpellResistance(List<SpellResistance> spellResistance) {
//		this.spellResistance = spellResistance;
//	}
	
	//Weapon proficiencies...
	public List<WeaponType> getProficiencies() {
		return proficiencies;
	}
	public void setProficiencies(List<WeaponType> proficiencies) {
		this.proficiencies = proficiencies;
	}
	public void addProficiency(WeaponType weaponType) {
		this.proficiencies.add(weaponType);
	}
	
	//not sure this is necessary.
	public List<ProtectionItem> getProtections() {
		return protections;
	}
	public void setProtections(List<ProtectionItem> protections) {
		this.protections = protections;
	}
	public List<Spell> getSpellBook() {
		return spellBook;
	}
	public void setSpellBook(List<Spell> spellBook) {
		this.spellBook = spellBook;
	}
	public List<Spell> getDailySpells() {
		return dailySpells;
	}
	public void setDailySpells(List<Spell> dailySpells) {
		this.dailySpells = dailySpells;
	}
	public long getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(long created) {
		this.created = created;
	}

	/**
	 * @return the modified
	 */
	public long getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(long modified) {
		this.modified = modified;
	}

	@Override
	public void addModifier(Modifier modifier) {
		this.activeModifiers.add(modifier);
	}


	/**
	 * @return the currentAdventureParty
	 */
	public AdventureParty getCurrentAdventureParty() {
		return currentAdventureParty;
	}
	
	/**
	 * @param currentAdventureParty the currentAdventureParty to set
	 */
	public void setCurrentAdventureParty(AdventureParty currentAdventureParty) {
		this.currentAdventureParty = currentAdventureParty;
	}

	public void setEnvironment(Environment environment) {
		this.currentEnvironment = environment;
	}
 	//specialization and double for Fighters?


	/* Attribute Accessibility Helper Functions */
	
	/**
	 * Attribute[] getAttributes
	 * @return dmpro.attributes.Attribute[]
	 */
	public Attribute[] getAttributes() {
		return new Attribute[] { this.strength,this.intelligence,this.wisdom,
				this.dexterity,this.constitution, this.charisma};
	}
	
	public Map<String,Attribute> getAttributesAsMap() {
		Map<String,Attribute> attributesMap = new HashMap<String,Attribute>();
		for (Attribute a : getAttributes() ) 
			attributesMap.put(a.attributeName.toLowerCase(), a);
		return attributesMap;
	}
	
	public void initializeModifiedAbilityScores() {
		if (strength.modifiedAbilityScore == -1) { strength.modifiedAbilityScore = strength.abilityScore; }
		if (intelligence.modifiedAbilityScore == -1) { intelligence.modifiedAbilityScore = intelligence.abilityScore; }
		if (wisdom.modifiedAbilityScore == -1) { wisdom.modifiedAbilityScore = wisdom.abilityScore; }
		if (constitution.modifiedAbilityScore == -1) { constitution.modifiedAbilityScore = constitution.abilityScore; }
		if (dexterity.modifiedAbilityScore == -1) { dexterity.modifiedAbilityScore = dexterity.abilityScore; }
		if (charisma.modifiedAbilityScore == -1) { charisma.modifiedAbilityScore = charisma.abilityScore; }
	}
	
	/**
	 * @param strength the strength to set
	 */
	public void setStrength(Strength strength) {
		this.strength = strength;
	}


	/**
	 * @param intelligence the intelligence to set
	 */
	public void setIntelligence(Intelligence intelligence) {
		this.intelligence = intelligence;
	}


	/**
	 * @param wisdom the wisdom to set
	 */
	public void setWisdom(Wisdom wisdom) {
		this.wisdom = wisdom;
	}


	/**
	 * @param dexterity the dexterity to set
	 */
	public void setDexterity(Dexterity dexterity) {
		this.dexterity = dexterity;
	}


	/**
	 * @param constitution the constitution to set
	 */
	public void setConstitution(Constitution constitution) {
		this.constitution = constitution;
	}


	/**
	 * @param charisma the charisma to set
	 */
	public void setCharisma(Charisma charisma) {
		this.charisma = charisma;
	}


	/**
	 * @param activeModifiers the activeModifiers to set
	 */
	public void setActiveModifiers(List<Modifier> activeModifiers) {
		this.activeModifiers = activeModifiers;
	}


	/**
	 * @param requiredActions the requiredActions to set
	 */
	public void setRequiredActions(List<CharacterManagementActions> requiredActions) {
		this.requiredActions = requiredActions;
	}


	/**
	 * @param equippedItems the equippedItems to set
	 */
	public void setEquippedItems(List<Item> equippedItems) {
		this.equippedItems = equippedItems;
	}


	/**
	 * @param savingThrowMap the savingThrowMap to set
	 */
	public void setSavingThrowMap(Map<SavingThrowType, SavingThrow> savingThrowMap) {
		this.savingThrowMap = savingThrowMap;
	}


	/**
	 * @param movementRate the movementRate to set
	 */
	public void setMovementRate(long movementRate) {
		this.movementRate = movementRate;
	}


	/**
	 * @param encumbranceState the encumbranceState to set
	 */
	public void setEncumbranceState(int encumbranceState) {
		this.encumbranceState = encumbranceState;
	}


	/**
	 * @param currentEnvironment the currentEnvironment to set
	 */
	public void setCurrentEnvironment(Environment currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	}


	/**
	 * @return the debug
	 */
	public static boolean isDebug() {
		return DEBUG;
	}


	/**
	 * @return the activeModifiers
	 */
	public List<Modifier> getActiveModifiers() {
		return activeModifiers;
	}


	/**
	 * @return the requiredActions
	 */
	public List<CharacterManagementActions> getRequiredActions() {
		return requiredActions;
	}
	
	
	public void addRequiredAction(CharacterManagementActions characterManagementAction) {
		if (requiredActions.contains(characterManagementAction)) return;
		this.requiredActions.add(characterManagementAction);
	}

	/**
	 * @return the equippedItems
	 */
	public List<Item> getEquippedItems() {
		return equippedItems;
	}


	/**
	 * @return the savingThrowMap
	 */
	public Map<SavingThrowType, SavingThrow> getSavingThrowMap() {
		return savingThrowMap;
	}


	/**
	 * @return the movementRate
	 */
	public long getMovementRate() {
		return movementRate;
	}


	/**
	 * @return the encumbranceState
	 */
	public int getEncumbranceState() {
		return encumbranceState;
	}


	/**
	 * @return the currentEnvironment
	 */
	public Environment getCurrentEnvironment() {
		return currentEnvironment;
	}


	/**
	 * @return the inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}


	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}


	/**
	 * @return the combatStats
	 */
	public CombatStatistics getCombatStats() {
		return combatStats;
	}


	/**
	 * @param combatStats the combatStats to set
	 */
	public void setCombatStats(CombatStatistics combatStats) {
		this.combatStats = combatStats;
	}


	/**
	 * @return the resurrectionCount
	 */
	public int getResurrectionCount() {
		return resurrectionCount;
	}


	/**
	 * @param resurrectionCount the resurrectionCount to set
	 */
	public void setResurrectionCount(int resurrectionCount) {
		this.resurrectionCount = resurrectionCount;
	}


	/**
	 * @return the slottedItems
	 */
	public SlottedItems getSlottedItems() {
		return slottedItems;
	}


	/**
	 * @param slottedItems the slottedItems to set
	 */
	public void setSlottedItems(SlottedItems slottedItems) {
		this.slottedItems = slottedItems;
	}

	
	/* Inventory Helper/Accesibility Functions */
    /**
     * @param coinMap - map of all characters coins
     */
    public void setCoinnage(Map<CoinType,CoinItem> coinMap) {
	//remove coins add map
	List<Item> inventory  = getInventory().stream().filter( p -> p.getItemType() != ItemType.COINS).collect(Collectors.toList());
	inventory.addAll(coinMap.values());
	this.setInventory(inventory);	
    }

    public Map<CoinType, CoinItem> getCoinnage() {
	Map<CoinType,CoinItem> coinMap = new HashMap<CoinType, CoinItem>(); 
	getInventory().stream()
	    .filter( p -> p.getItemType() == ItemType.COINS)
	    .map(p -> (CoinItem)p)
	    .collect(Collectors.groupingBy(CoinItem::getCoinType, 
					   Collectors.summingInt(CoinItem::getItemCount)))
	    .forEach( (k,v) -> coinMap.put(k, new CoinItem(k,v)));

	//consolidate coins in inventory
	this.setCoinnage(coinMap);
					
	return coinMap;
		
    }
	
	public ProficiencyData getProficiencySlots() {
		int proficiencySlots = 0;
	    boolean hasFighter = false;
	    for (CharacterClass characterClass : getClasses().values()) {
	    	proficiencySlots += characterClass.getStartingProficiencies();
	    	proficiencySlots += characterClass.getAdditionalProficiencies();
	    	if (characterClass.getCharacterClassType() == CharacterClassType.FIGHTER)
	    		hasFighter = true;
	    }
	    return new ProficiencyData(hasFighter,proficiencySlots);
	}


	/**
	 * @return the playerId
	 */
	public long getPlayerId() {
		return playerId;
	}


	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}


	/**
	 * @return the currentHitPoints
	 */
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}


	/**
	 * @param currentHitPoints the currentHitPoints to set
	 */
	public void setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}
}
