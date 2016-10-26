package dmpro.character.classes;

import java.util.List;

import dmpro.character.Proficiency;
import dmpro.character.classes.CharacterClassType;
import dmpro.data.loaders.CombatRecord;
import dmpro.data.loaders.SavingThrowRecord;
import dmpro.items.Item;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.utils.Die;

public interface ICharacterClass {

	void processExperience(int i);

	void addHitPoints();

	String getClassName();

	void setClassName(String className);

	long getExperiencePoints();

	void setExperiencePoints(long experiencePoints);

	int getExperienceLevel();

	void setExperienceLevel(int experienceLevel);

	int getHitTableId();

	void setHitTableId(int hitTableId);

	int getSavingThrowTableId();

	void setSavingThrowTableId(int savingThrowTableId);

	List<Item> getRestrictedItems();

	void setRestrictedItems(List<Item> restrictedItems);

	List<AbilityModifier> getClassAbilities();

	void setClassAbilities(List<AbilityModifier> classAbility);

	/**
	 * @return the hasSpells
	 */
	boolean isHasSpells();

	/**
	 * @param hasSpells the hasSpells to set
	 */
	void setHasSpells(boolean hasSpells);

	/**
	 * @return the hitDie
	 */
	Die getHitDie();

	/**
	 * @param hitDie the hitDie to set
	 */
	void setHitDie(Die hitDie);

	/**
	 * @return the readyForLevelUp
	 */
	boolean isReadyForLevelUp();

	/**
	 * @param readyForLevelUp the readyForLevelUp to set
	 */
	void setReadyForLevelUp(boolean readyForLevelUp);

	/**
	 * @return the hitDieRecord
	 */
	List<Integer> getHitDieRecord();

	/**
	 * @param hitDieRecord the hitDieRecord to set
	 */
	void setHitDieRecord(List<Integer> hitDieRecord);

	/**
	 * @return the startingProficiencies
	 */
	int getStartingProficiencies();

	/**
	 * @param startingProficiencies the startingProficiencies to set
	 */
	void setStartingProficiencies(int startingProficiencies);

	/**
	 * @return the newProficienyPerLevel
	 */
	int getNewProficienyPerLevel();

	/**
	 * @param newProficienyPerLevel the newProficienyPerLevel to set
	 */
	void setNewProficienyPerLevel(int newProficienyPerLevel);

	/**
	 * @return the nonProficiencyPenalty
	 */
	int getNonProficiencyPenalty();

	/**
	 * @param nonProficiencyPenalty the nonProficiencyPenalty to set
	 */
	void setNonProficiencyPenalty(int nonProficiencyPenalty);

	/**
	 * @return the weaponProficiencies
	 */
	List<Proficiency> getWeaponProficiencies();

	/**
	 * @param weaponProficiencies the weaponProficiencies to set
	 */
	void setWeaponProficiencies(List<Proficiency> weaponProficiencies);

	/**
	 * @return the savingThrowTable
	 */

	/**
	 * @return the toHit
	 */
	CombatRecord getToHit();

	/**
	 * @param toHit the toHit to set
	 */
	void setToHit(CombatRecord toHit);

	/**
	 * @return the savingThrow
	 */
	SavingThrowRecord getSavingThrow();

	/**
	 * @param savingThrowRecord the savingThrow to set
	 */
	void setSavingThrow(SavingThrowRecord savingThrowRecord);

	/**
	 * @return the characterClassType
	 */
	CharacterClassType getCharacterClassType();

	/**
	 * @param characterClassType the characterClassType to set
	 */
	void setCharacterClassType(CharacterClassType characterClassType);

	/**
	 * @return the classAttribute
	 */
	List<AttributeModifier> getClassAttribute();

	/**
	 * @param classAttribute the classAttribute to set
	 */
	void setClassAttribute(List<AttributeModifier> classAttribute);

	/**
	 * @return the maxHitDice
	 */
	int getMaxHitDice();

	/**
	 * @param maxHitDice the maxHitDice to set
	 */
	void setMaxHitDice(int maxHitDice);

	/**
	 * @return the hitPointPerLevelAfterMax
	 */
	int getHitPointPerLevelAfterMax();

	/**
	 * @param hitPointPerLevelAfterMax the hitPointPerLevelAfterMax to set
	 */
	void setHitPointPerLevelAfterMax(int hitPointPerLevelAfterMax);

	/**
	 * @return the experiencePointsPerLevelAfterMax
	 */
	int getExperiencePointsPerLevelAfterMax();

	/**
	 * @param experiencePointsPerLevelAfterMax the experiencePointsPerLevelAfterMax to set
	 */
	void setExperiencePointsPerLevelAfterMax(int experiencePointsPerLevelAfterMax);

}