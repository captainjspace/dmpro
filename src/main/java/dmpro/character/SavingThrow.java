package dmpro.character;

import java.util.ArrayList;
import java.util.List;

import dmpro.modifier.Modifiable;
import dmpro.modifier.Modifier;
import dmpro.modifier.SavingThrowModifier;

/**
 * SavingThrow.java
 * 
 * This class will contain a single saving throw, and receive modifiers when attached to a characters
 * base values are determined by CharacterClass and level.
 * A SavingThrowRecord will be looked in the SavingThrowTable for base
 * 
 * AttributeModifiers of Wisdom can generate SavingThrowModifiers
 * Spells and Magic Items can bestow Modifiers
 * Situations and Locations can bestow modifiers.
 * 
 * ModifierEngine will evaluate the current saving throws.
 * Game time will use activeSavingThrowRoll
 * 
 * Character state will maintain the active collection of SavingThrows.
 * 
 * @author joshualandman
 *
 */

public class SavingThrow implements Modifiable {
	
	public SavingThrowType savingThrowType;
	int savingThrowRoll;
	List<SavingThrowModifier> savingThrowModifiers = new ArrayList<SavingThrowModifier>();
	
	//modifier engine to eval modifiers and set these values
	int bonusModifier;
	int activeSavingThrowRoll;
	
	///Getters and Setters
	/**
	 * @return the savingThrowType
	 */
	public SavingThrowType getSavingThrowType() {
		return savingThrowType;
	}
	/**
	 * @param savingThrowType the savingThrowType to set
	 */
	public void setSavingThrowType(SavingThrowType savingThrowType) {
		this.savingThrowType = savingThrowType;
	}
	/**
	 * @return the savingThrowRoll
	 */
	public int getSavingThrowRoll() {
		return savingThrowRoll;
	}
	/**
	 * @param savingThrowRoll the savingThrowRoll to set
	 */
	public void setSavingThrowRoll(int savingThrowRoll) {
		this.savingThrowRoll = savingThrowRoll;
	}
	/**
	 * @return the bonusModifier
	 */
	public int getBonusModifier() {
		return bonusModifier;
	}
	/**
	 * @param bonusModifier the bonusModifier to set
	 */
	public void setBonusModifier(int bonusModifier) {
		this.bonusModifier = bonusModifier;
	}
	/**
	 * @return the modifiedSavingThrowRoll
	 */
	public int getModifiedSavingThrowRoll() {
		return activeSavingThrowRoll;
	}
	/**
	 * @param modifiedSavingThrowRoll the modifiedSavingThrowRoll to set
	 */
	public void setModifiedSavingThrowRoll(int modifiedSavingThrowRoll) {
		this.activeSavingThrowRoll = modifiedSavingThrowRoll;
	}
	@Override
	public void addModifier(Modifier modifier) {
		//not if this is correct
		try {
		this.savingThrowModifiers.add((SavingThrowModifier) modifier);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
	}
	
	

}
