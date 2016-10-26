package dmpro.character.classes;
import java.util.ArrayList;
import java.util.List;

import dmpro.character.Proficiency;
import dmpro.data.loaders.CombatRecord;
import dmpro.data.loaders.CombatTableLoader;
import dmpro.data.loaders.ExperienceTableLoader;
import dmpro.data.loaders.ExperienceTableRecord;
import dmpro.data.loaders.SavingThrowLoader;
import dmpro.data.loaders.SavingThrowRecord;
import dmpro.items.Item;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.utils.Die;

public class CharacterClass {
	
	public CharacterClass() {
		
	}
	/**
	 * TODO:	Add Unearthed Arcana classes
	 * 			Finish Monk and Bard
	 * @author Joshua Landman, joshua.s.landman@gmail.com
	 *
	 */

	CharacterClassType characterClassType;
	String className;
	long experiencePoints;
	int experiencePointsPerLevelAfterMax;
	int experienceLevel=-1;//determined by experience points
	int hitTableId;//?
	int savingThrowTableId;//?
	boolean hasSpells;
	boolean readyForLevelUp=false;
	int initialGold; //not really important to persist this...
	
	Die hitDie; 
	List<HitDieRecord> hitDieHistory = new ArrayList<HitDieRecord>();
	int maxHitDice;
	int hitPointPerLevelAfterMax;
	
	//proficiencies;
	int startingProficiencies;
	int newProficienyPerLevel;
	int nonProficiencyPenalty;
	List<Proficiency> weaponProficiencies = new ArrayList<Proficiency>();
	//specialization is for fighters only
	
//	List<SavingThrow> savingThrowTable = new ArrayList<SavingThrow>();
	CombatRecord combatRecord;
	SavingThrowRecord savingThrowRecord;
//	SpellsAllowedRecord spellsAllowed;
	
//	List<CombatRecord> combatTables = new ArrayList<CombatRecord>();	
	List<Item> restrictedItems = new ArrayList<Item>(); //how to manage?
	List<AbilityModifier> classAbilities = new ArrayList<AbilityModifier>(); //vague
	List<AttributeModifier> classAttribute = new ArrayList<AttributeModifier>();
	List<XPBonus> xpBonusRequirements = new ArrayList<XPBonus>();
	
	public void addXPBonus(XPBonus xpBonus) {
		xpBonusRequirements.add(xpBonus);
	}
	public List<XPBonus> getXPBonusRequirements() {
		return xpBonusRequirements;
	}
	
	//public getSavingThrow(SavingThrow.Enum,, CharacterSubClass, level)
	
	
//	public void addExperiencePoints(int i) {
//		// TODO Auto-generated method stub
//		// check for 10% bonus
//		this.experiencePoints+=i;
//		processExperience();
//		
//	}
	

//	public void processExperience(int i) {
//    	this.experiencePoints+=i;
//    	int llevel = this.getExperienceLevel();
//    	ExperienceTableLoader xt = new ExperienceTableLoader(this.className);
////    	System.out.println(this.getClassName() + ":" + xt.experienceTable.size());
//    	ExperienceTableRecord xtr = xt.getRecordByXP(this.getExperiencePoints());
//    	if (llevel < xtr.getExperienceLevel()) {
//    		System.out.println("Congratulations you went up a level!");
//    		this.readyForLevelUp=true;
//    		if (xtr.getExperienceLevel() - llevel > 1) {
//    			//rule to be sorted
//    			System.out.println("Technically you should only go up one level - hmmmm - we may have to roll backs some xp");
//    		}
//    		
//    	}
//    	this.setExperienceLevel( xtr.getExperienceLevel() );
//    	//return xtr;
////    	handles both load and increase
//    	processLevel(xtr);
//    }
//	protected void processLevel(ExperienceTableRecord xtr) {
//		// TODO Auto-generated method stub
//		System.out.println("PROCESS LEVEL");
//		processCombat();
//		//proficiencies and to hit
//		
//		processSavingThrows();
//		//leave to spell casters subclass
//		//if (this.hasSpells) processSpells();
//		addHitPoints();
////		processProficiencies();
//		
//		
//	}
//	
//	protected void processSavingThrows() {
//		// to be loaded 
//		System.out.println("PROCESS SAVING THROWS");
//		SavingThrowLoader stt = new SavingThrowLoader(this.className);
//		this.savingThrowRecord = stt.getRecord(this.getExperienceLevel());
//	}
//	protected void processCombat() {
//		System.out.println("PROCESS COMBAT");
//		CombatTableLoader ct = new CombatTableLoader(this.className);
//		this.combatRecord = ct.getRecord(this.getExperienceLevel());
//	}
//

//	public void addHitPoints() {
//		System.out.println("PROCESS HIT POINTS");
//		// TODO Auto-generated method stub
//		//insubclass I think
//		//works for single level - or character generation.
//		//check for constitution bonuses
//		//and additional fighter bonuses
//		//fix below to work for rangers.
//		while (this.hitDieRecord.size() < this.getExperienceLevel()) {
//			hitDieRecord.add(new Integer(hitDie.roll()));
//		}
//	}
	
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public long getExperiencePoints() {
		return experiencePoints;
	}
	
	public void setExperiencePoints(long experiencePoints) {
		this.experiencePoints = experiencePoints;
	}

	public int getExperienceLevel() {
		return experienceLevel;
	}
	
	public void setExperienceLevel(int experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	
	public int getHitTableId() {
		return hitTableId;
	}
	
	public void setHitTableId(int hitTableId) {
		this.hitTableId = hitTableId;
	}

	public int getSavingThrowTableId() {
		return savingThrowTableId;
	}

	public void setSavingThrowTableId(int savingThrowTableId) {
		this.savingThrowTableId = savingThrowTableId;
	}

	public List<Item> getRestrictedItems() {
		return restrictedItems;
	}

	public void setRestrictedItems(List<Item> restrictedItems) {
		this.restrictedItems = restrictedItems;
	}

	public List<AbilityModifier> getClassAbilities() {
		return classAbilities;
	}

	public void setClassAbilities(List<AbilityModifier> classAbility) {
		this.classAbilities = classAbility;
	}

	public boolean isHasSpells() {
		return hasSpells;
	}
	
	public void setHasSpells(boolean hasSpells) {
		this.hasSpells = hasSpells;
	}
	
	public Die getHitDie() {
		return hitDie;
	}
	
	public void setHitDie(Die hitDie) {
		this.hitDie = hitDie;
	}
	
	public boolean isReadyForLevelUp() {
		return readyForLevelUp;
	}
	
	public void setReadyForLevelUp(boolean readyForLevelUp) {
		this.readyForLevelUp = readyForLevelUp;
	}

	public List<HitDieRecord> getHitDieHistory() {
		return hitDieHistory;
	}
	
	public void setHitDieHistory(List<HitDieRecord> hitDieHistory) {
		this.hitDieHistory = hitDieHistory;
	}
	
	public int getStartingProficiencies() {
		return startingProficiencies;
	}
	
	public void setStartingProficiencies(int startingProficiencies) {
		this.startingProficiencies = startingProficiencies;
	}
	
	public int getNewProficienyPerLevel() {
		return newProficienyPerLevel;
	}
	
	public void setNewProficienyPerLevel(int newProficienyPerLevel) {
		this.newProficienyPerLevel = newProficienyPerLevel;
	}
	
	public int getNonProficiencyPenalty() {
		return nonProficiencyPenalty;
	}
	
	public void setNonProficiencyPenalty(int nonProficiencyPenalty) {
		this.nonProficiencyPenalty = nonProficiencyPenalty;
	}
	
	public List<Proficiency> getWeaponProficiencies() {
		return weaponProficiencies;
	}
	
	public void setWeaponProficiencies(List<Proficiency> weaponProficiencies) {
		this.weaponProficiencies = weaponProficiencies;
	}
	
	public CombatRecord getCombatRecord() {
		return combatRecord;
	}
	
	public void setCombatRecord(CombatRecord toHit) {
		this.combatRecord = toHit;
	}
	
	public SavingThrowRecord getSavingThrow() {
		return savingThrowRecord;
	}
	
	public void setSavingThrow(SavingThrowRecord savingThrowRecord) {
		this.savingThrowRecord = savingThrowRecord;
	}


	
	public CharacterClassType getCharacterClassType() {
		return characterClassType;
	}

	
	public void setCharacterClassType(CharacterClassType characterClassType) {
		this.characterClassType = characterClassType;
	}

	
	public List<AttributeModifier> getClassAttribute() {
		return classAttribute;
	}

	
	public void setClassAttribute(List<AttributeModifier> classAttribute) {
		this.classAttribute = classAttribute;
	}
	
	
	public int getMaxHitDice() {
		return maxHitDice;
	}

	public void setMaxHitDice(int maxHitDice) {
		this.maxHitDice = maxHitDice;
	}

	public int getHitPointPerLevelAfterMax() {
		return hitPointPerLevelAfterMax;
	}
	
	public void setHitPointPerLevelAfterMax(int hitPointPerLevelAfterMax) {
		this.hitPointPerLevelAfterMax = hitPointPerLevelAfterMax;
	}
	
	public int getExperiencePointsPerLevelAfterMax() {
		return experiencePointsPerLevelAfterMax;
	}
	
	public void setExperiencePointsPerLevelAfterMax(int experiencePointsPerLevelAfterMax) {
		this.experiencePointsPerLevelAfterMax = experiencePointsPerLevelAfterMax;
	}
	/**
	 * @return the initialGold
	 */
	public int getInitialGold() {
		return initialGold;
	}
	/**
	 * @param initialGold the initialGold to set
	 */
	public void setInitialGold(int initialGold) {
		this.initialGold = initialGold;
	}
}
