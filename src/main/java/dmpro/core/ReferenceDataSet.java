package dmpro.core;

import dmpro.attributes.*;
import dmpro.data.loaders.*;
import dmpro.items.Item;
import dmpro.character.race.*;
import dmpro.character.classes.*;
import dmpro.character.classes.CharacterClassType;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmpro.spells.SpellLibrary;

public class ReferenceDataSet implements Runnable {

	public boolean isReady = false;
	
	
	private AsciiArt asciiArt;
	private CombatTableLoader combatTableLoader;
	private TurnUndeadLoader turnUndeadLoader;
	private ExperienceTableLoader experienceTableLoader;
	private SavingThrowLoader savingThrowTableLoader;
	private ThiefAbilityTableLoader thiefAbilityTableLoader;
	private SpellsAllowedTableLoader spellsAllowedTableLoader;
	private ClassRaceLoader classRaceLoader;
	private ClassAttributeLoader classAttributeLoader;
	private RaceAttributeLoader raceAttributeLoader;
	private MagicItemLoader magicItemLoader ;
	private WeaponItemLoader weaponItemLoader;
	private ArmorTableLoader armorTableLoader;
	private StandardItemLoader standardItemLoader;
	//	private ItemLoader itemLoader - new ItemLoader(); //IMPLEMENT ME
	private SpellLibrary spellLibrary;
	private AttributeLoader attributeLoader;
	private StrengthLoader strengthLoader;
	private IntelligenceLoader intelligenceLoader ;
	private WisdomLoader wisdomLoader;
	private DexterityLoader dexterityLoader;
	private ConstitutionLoader constitutionLoader;
	private CharismaLoader charismaLoader;
	private RaceSizeLoader raceSizeLoader;
	private RaceClassAgeLoader raceClassAgeLoader;
	private Map<RaceType,Race> race= new HashMap<RaceType, Race>();
	private Map<CharacterClassType, CharacterClass> classes = new HashMap<CharacterClassType, CharacterClass>();
	


	public void run() {
		asciiArt = new AsciiArt();
		combatTableLoader = new CombatTableLoader();
		turnUndeadLoader = new TurnUndeadLoader();
		experienceTableLoader = new ExperienceTableLoader();
		savingThrowTableLoader = new SavingThrowLoader();
		thiefAbilityTableLoader = new ThiefAbilityTableLoader();
		spellsAllowedTableLoader = new SpellsAllowedTableLoader();
		classRaceLoader = new ClassRaceLoader();
		classAttributeLoader = new ClassAttributeLoader();
		raceAttributeLoader = new RaceAttributeLoader();
		magicItemLoader = new MagicItemLoader();
		weaponItemLoader = new WeaponItemLoader();
		armorTableLoader = new ArmorTableLoader();
		standardItemLoader = new StandardItemLoader();
		//		private ItemLoader itemLoader - new ItemLoader(); //IMPLEMENT ME
		spellLibrary = new SpellLibrary();
		strengthLoader = new StrengthLoader();
		intelligenceLoader = new IntelligenceLoader();
		wisdomLoader = new WisdomLoader();
		dexterityLoader = new DexterityLoader();
		constitutionLoader = new ConstitutionLoader();
		charismaLoader = new CharismaLoader();
		attributeLoader = new AttributeLoader();
		raceClassAgeLoader = new RaceClassAgeLoader();
		raceSizeLoader = new RaceSizeLoader();
		loadMaps();
		isReady = true;
	}
	
	/**
	 * quick assembly of Maps
	 */
	private void loadMaps() {
		for(RaceType r : RaceType.values()) {
			race.put(r, r.newRace());
		}
		for(CharacterClassType c : CharacterClassType.values()) {
			classes.put(c, c.newCharacterClass());
		}
	}
	
	/**
	 * @return the combatTableLoader
	 */
	public CombatTableLoader getCombatTableLoader() {
		return combatTableLoader;
	}
	/**
	 * @return the experienceTableLoader
	 */
	public ExperienceTableLoader getExperienceTableLoader() {
		return experienceTableLoader;
	}
	/**
	 * @return the savingThrowTableLoader
	 */
	public SavingThrowLoader getSavingThrowTableLoader() {
		return savingThrowTableLoader;
	}
	/**
	 * @return the thiefAbilityTableLoader
	 */
	public ThiefAbilityTableLoader getThiefAbilityTableLoader() {
		return thiefAbilityTableLoader;
	}
	/**
	 * @return the spellsAllowedTableLoader
	 */
	public SpellsAllowedTableLoader getSpellsAllowedTableLoader() {
		return spellsAllowedTableLoader;
	}
	/**
	 * @return the classRaceLoader
	 */
	public ClassRaceLoader getClassRaceLoader() {
		return classRaceLoader;
	}
	/**
	 * @return the classAttributeLoader
	 */
	public ClassAttributeLoader getClassAttributeLoader() {
		return classAttributeLoader;
	}
	/**
	 * @return the raceAttributeLoader
	 */
	public RaceAttributeLoader getRaceAttributeLoader() {
		return raceAttributeLoader;
	}
	/**
	 * @return the magicItemLoader
	 */
	public MagicItemLoader getMagicItemLoader() {
		return magicItemLoader;
	}
	/**
	 * @return the weaponItemLoader
	 */
	public WeaponItemLoader getWeaponItemLoader() {
		return weaponItemLoader;
	}
	/**
	 * @return the spellLibrary
	 */
	public SpellLibrary getSpellLibrary() {
		return spellLibrary;
	}
	/**
	 * @return the strengthLoader
	 */
	public StrengthLoader getStrengthLoader() {
		return strengthLoader;
	}
	/**
	 * @return the intelligenceLoader
	 */
	public IntelligenceLoader getIntelligenceLoader() {
		return intelligenceLoader;
	}
	/**
	 * @return the wisdomLoader
	 */
	public WisdomLoader getWisdomLoader() {
		return wisdomLoader;
	}
	/**
	 * @return the dexterityLoader
	 */
	public DexterityLoader getDexterityLoader() {
		return dexterityLoader;
	}
	/**
	 * @return the constitutionLoader
	 */
	public ConstitutionLoader getConstitutionLoader() {
		return constitutionLoader;
	}
	/**
	 * @return the charismaLoader
	 */
	public CharismaLoader getCharismaLoader() {
		return charismaLoader;
	}
	/**
	 * @return the charismaLoader
	 */
	public AttributeLoader getAttributeLoader() {
		return attributeLoader;
	}
	/**
	 * @return the asciiArt
	 */
	public AsciiArt getAsciiArt() {
		return asciiArt;
	}
	/**
	 * @return the raceSizeLoader
	 */
	public RaceSizeLoader getRaceSizeLoader() {
		return raceSizeLoader;
	}
	/**
	 * @return the raceClassAgeLoader
	 */
	public RaceClassAgeLoader getRaceClassAgeLoader() {
		return raceClassAgeLoader;
	}
	/**
	 * @return the armorTableLoader
	 */
	public ArmorTableLoader getArmorTableLoader() {
		// TODO Auto-generated method stub
		return armorTableLoader;
	}
	/**
	 * @return the isReady
	 */
	public boolean isReady() {
		return isReady;
	}
	/**
	 * @return the turnUndeadLoader
	 */
	public TurnUndeadLoader getTurnUndeadLoader() {
		return turnUndeadLoader;
	}
	/**
	 * @return the standardItemLoader
	 */
	public StandardItemLoader getStandardItemLoader() {
		return standardItemLoader;
	}

	public static void main (String [] args) {
		ReferenceDataSet rds = new ReferenceDataSet();
		rds.run();
	}

	/**
	 * @return the race
	 */
	public Map<RaceType, Race> getRace() {
		return race;
	}

	/**
	 * @return the classes
	 */
	public Map<CharacterClassType, CharacterClass> getClasses() {
		return classes;
	}
	
	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<Item>();
		items.addAll(getWeaponItemLoader().getWeapons());
		items.addAll(getArmorTableLoader().getArmorItems());
		items.addAll(getStandardItemLoader().getStandardItems());
		return items;
	}
}
