package dmpro;

import dmpro.utils.Dice;

public class Ability {
	/**
	 * not quite sure if I need this but I think so.
	 * certain abilities will be complicated at likely require a specific subclass to handle implementation
	 * 
	 * @author Joshua Landman, joshua.s.landman@gmail.com
	 *
	 */
	
	public enum AbilityType {
		MAGICRESISTANCE,
		THIEF,
		SECRETDOORS,
		DETECTION,
		SPELL,
		SURPRISE,
		DIRECTION,
		MELEE;
	}
	
	
	int id=-1;
	String abilityName;
	String description;
	private boolean isPermanent=false;
	
	Ability.AbilityType abilityType;
	Dice abilityDie;
	int requiredRoll;
	float percentChance;
	int meleeModifier;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAbilityName() {
		return abilityName;
	}
	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Dice getAbilityDie() {
		return abilityDie;
	}
	public void setAbilityDie(Dice abilityDie) {
		this.abilityDie = abilityDie;
	}
	public Ability.AbilityType getAbilityType() {
		return abilityType;
	}
	public void setAbilityType(Ability.AbilityType abilityType) {
		this.abilityType = abilityType;
	}
	public boolean isPermanent() {
		return isPermanent;
	}
	public void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}
	public float getRequiredRoll() {
		return requiredRoll;
	}
	public void setRequiredRoll(int requiredRoll) {
		this.requiredRoll = requiredRoll;
	}
	/**
	 * @return the percentChance
	 */
	public float getPercentChance() {
		return percentChance;
	}
	/**
	 * @param percentChance the percentChance to set
	 */
	public void setPercentChance(float percentChance) {
		this.percentChance = percentChance;
	}
	
}
