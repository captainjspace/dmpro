package dmpro.attributes;

import dmpro.utils.Dice;

public class Strength extends Attribute {
	
	static final int fieldCount=11;
	//final String attributeName="Strength"; //for json serialization
	
	//int abilityScore;
	float percentOverHuman;
	int hitProbabilityModifier;
	int damageAdjustmentModifier;
	int weightAllowanceModifier;
	float openDoors;
	float wizardLockOpenDoors;
	String openDoorDieRoll;
	Dice doorDie=Dice.d6;
	float bendBarsLiftGates;
	String notes;
	


	public Strength(String[] fields) {
		// TODO Auto-generated constructor stub
		attributeType = AttributeType.STRENGTH;
		attributeName = "Strength";
		abilityScore=Integer.parseInt(fields[0]);
		percentOverHuman=Float.parseFloat(fields[1]);
		hitProbabilityModifier=Integer.parseInt(fields[2]);
		damageAdjustmentModifier=Integer.parseInt(fields[3]);
		weightAllowanceModifier=Integer.parseInt(fields[4]);
		openDoors=Float.parseFloat(fields[5]);
		openDoorDieRoll=fields[6];
		doorDie=Dice.valueOf(fields[7]);
		wizardLockOpenDoors=Float.parseFloat(fields[8]);
		bendBarsLiftGates=Float.parseFloat(fields[9]);
		notes=fields[10].trim();
	}


	

	/**
	 * @return the percentOverHuman
	 */
	public float getPercentOverHuman() {
		return percentOverHuman;
	}




	/**
	 * @param percentOverHuman the percentOverHuman to set
	 */
	public void setPercentOverHuman(float percentOverHuman) {
		this.percentOverHuman = percentOverHuman;
	}




	/**
	 * @return the hitProbabilityModifier
	 */
	public int getHitProbabilityModifier() {
		return hitProbabilityModifier;
	}




	/**
	 * @param hitProbabilityModifier the hitProbabilityModifier to set
	 */
	public void setHitProbabilityModifier(int hitProbabilityModifier) {
		this.hitProbabilityModifier = hitProbabilityModifier;
	}




	/**
	 * @return the damageAdjustmentModifier
	 */
	public int getDamageAdjustmentModifier() {
		return damageAdjustmentModifier;
	}




	/**
	 * @param damageAdjustmentModifier the damageAdjustmentModifier to set
	 */
	public void setDamageAdjustmentModifier(int damageAdjustmentModifier) {
		this.damageAdjustmentModifier = damageAdjustmentModifier;
	}




	/**
	 * @return the weightAllowanceModifier
	 */
	public int getWeightAllowanceModifier() {
		return weightAllowanceModifier;
	}




	/**
	 * @param weightAllowanceModifier the weightAllowanceModifier to set
	 */
	public void setWeightAllowanceModifier(int weightAllowanceModifier) {
		this.weightAllowanceModifier = weightAllowanceModifier;
	}




	/**
	 * @return the openDoors
	 */
	public float getOpenDoors() {
		return openDoors;
	}




	/**
	 * @param openDoors the openDoors to set
	 */
	public void setOpenDoors(float openDoors) {
		this.openDoors = openDoors;
	}




	/**
	 * @return the wizardLockOpenDoors
	 */
	public float getWizardLockOpenDoors() {
		return wizardLockOpenDoors;
	}




	/**
	 * @param wizardLockOpenDoors the wizardLockOpenDoors to set
	 */
	public void setWizardLockOpenDoors(float wizardLockOpenDoors) {
		this.wizardLockOpenDoors = wizardLockOpenDoors;
	}




	/**
	 * @return the openDoorDieRoll
	 */
	public String getOpenDoorDieRoll() {
		return openDoorDieRoll;
	}




	/**
	 * @param openDoorDieRoll the openDoorDieRoll to set
	 */
	public void setOpenDoorDieRoll(String openDoorDieRoll) {
		this.openDoorDieRoll = openDoorDieRoll;
	}




	/**
	 * @return the doorDie
	 */
	public Dice getDoorDie() {
		return doorDie;
	}




	/**
	 * @param doorDie the doorDie to set
	 */
	public void setDoorDie(Dice doorDie) {
		this.doorDie = doorDie;
	}




	/**
	 * @return the bendBarsLiftGates
	 */
	public float getBendBarsLiftGates() {
		return bendBarsLiftGates;
	}




	/**
	 * @param bendBarsLiftGates the bendBarsLiftGates to set
	 */
	public void setBendBarsLiftGates(float bendBarsLiftGates) {
		this.bendBarsLiftGates = bendBarsLiftGates;
	}




	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}




	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}




	@Override
	public String toString() {
		return "Strength [abilityScore=" + abilityScore + ", percentOverHuman=" + percentOverHuman
				+ ", hitProbabilityModifier=" + hitProbabilityModifier + ", damageAdjustmentModifier="
				+ damageAdjustmentModifier + ", weightAllowanceModifier=" + weightAllowanceModifier + ", openDoors="
				+ openDoors + ", wizardLockOpenDoors=" + wizardLockOpenDoors + ", openDoorDieRoll=" + openDoorDieRoll
				+ ", doorDie=" + doorDie + ", bendBarsLiftGates=" + bendBarsLiftGates + ", notes=" + notes + "]";
	}




	
	
}
