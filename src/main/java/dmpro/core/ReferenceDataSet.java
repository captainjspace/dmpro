package dmpro.core;

import dmpro.CombatTableLoader;
import dmpro.ExperienceTableLoader;
import dmpro.SavingThrowLoader;
import dmpro.SpellsAllowedTableLoader;
import dmpro.ThiefAbilityTableLoader;
import dmpro.attributes.*;
import dmpro.character.classes.ClassAttributeLoader;
import dmpro.character.classes.ClassRaceLoader;
import dmpro.character.race.RaceAttributeLoader;
import dmpro.items.MagicItemLoader;
import dmpro.items.WeaponItemLoader;
import dmpro.spells.SpellLibrary;

public class ReferenceDataSet implements Runnable{

	protected boolean isReady = false;
	
	
	
	private CombatTableLoader combatTableLoader;
	private ExperienceTableLoader experienceTableLoader;
	private SavingThrowLoader savingThrowTableLoader;
	private ThiefAbilityTableLoader thiefAbilityTableLoader;
	private SpellsAllowedTableLoader spellsAllowedTableLoader;
	private ClassRaceLoader classRaceLoader;
	private ClassAttributeLoader classAttributeLoader;
	private RaceAttributeLoader raceAttributeLoader;
	private MagicItemLoader magicItemLoader ;
	private WeaponItemLoader weaponItemLoader ;
	//	private ItemLoader itemLoader - new ItemLoader(); //IMPLEMENT ME
	private SpellLibrary spellLibrary;
	private AttributeLoader attributeLoader;
	private StrengthLoader strengthLoader;
	private IntelligenceLoader intelligenceLoader ;
	private WisdomLoader wisdomLoader;
	private DexterityLoader dexterityLoader;
	private ConstitutionLoader constitutionLoader;
	private CharismaLoader charismaLoader;


	public void run() {
		combatTableLoader = new CombatTableLoader();
		experienceTableLoader = new ExperienceTableLoader();
		savingThrowTableLoader = new SavingThrowLoader();
		thiefAbilityTableLoader = new ThiefAbilityTableLoader();
		spellsAllowedTableLoader = new SpellsAllowedTableLoader();
		classRaceLoader = new ClassRaceLoader();
		classAttributeLoader = new ClassAttributeLoader();
		raceAttributeLoader = new RaceAttributeLoader();
		magicItemLoader = new MagicItemLoader();
		weaponItemLoader = new WeaponItemLoader();
		//		private ItemLoader itemLoader - new ItemLoader(); //IMPLEMENT ME
		spellLibrary = new SpellLibrary();
		strengthLoader = new StrengthLoader();
		intelligenceLoader = new IntelligenceLoader();
		wisdomLoader = new WisdomLoader();
		dexterityLoader = new DexterityLoader();
		constitutionLoader = new ConstitutionLoader();
		charismaLoader = new CharismaLoader();
		attributeLoader = new AttributeLoader();
		isReady = true;
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


}
