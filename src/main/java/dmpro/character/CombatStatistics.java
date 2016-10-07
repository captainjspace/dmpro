/**
 * 
 */
package dmpro.character;

import dmpro.modifier.ArmorClassModifier;
import dmpro.modifier.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * @author Joshua Landman, <joshua.s.landman@gmail.com>
 * created on Oct 6, 2016
 * pojo for combat stats
 */
public class CombatStatistics {
	
	private Date timestamp;
	
	private int meleeToHitBonus;
	private List<Modifier> meleeToHitFactors = new ArrayList<Modifier>();
	
	private int missileToHitBonus;
	private List<Modifier> missileToHitFactors = new ArrayList<Modifier>();
	
	private int damageBonus;
	private List<Modifier> damageFactors = new ArrayList();
	
	private int armorClass;
	private List<ArmorClassModifier> armorClassFactors = new ArrayList<ArmorClassModifier>();
	
	private int movement;
	private List<Modifier> movementFactors = new ArrayList<Modifier>();
	
	private int initiativeModifier;
	private List<Modifier> initiativeFactors = new ArrayList<Modifier>();
	
	private int supriseRoll;
	private List<Modifier> surpriseFactors = new ArrayList<Modifier>();
	
	
	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the meleeToHitBonus
	 */
	public int getMeleeToHitBonus() {
		return meleeToHitBonus;
	}
	/**
	 * @param meleeToHitBonus the meleeToHitBonus to set
	 */
	public void setMeleeToHitBonus(int meleeToHitBonus) {
		this.meleeToHitBonus = meleeToHitBonus;
	}
	/**
	 * @return the meleeToHitFactors
	 */
	public List<Modifier> getMeleeToHitFactors() {
		return meleeToHitFactors;
	}
	/**
	 * @param meleeToHitFactors the meleeToHitFactors to set
	 */
	public void setMeleeToHitFactors(List<Modifier> meleeToHitFactors) {
		this.meleeToHitFactors = meleeToHitFactors;
	}
	/**
	 * @return the missileToHitBonus
	 */
	public int getMissileToHitBonus() {
		return missileToHitBonus;
	}
	/**
	 * @param missileToHitBonus the missileToHitBonus to set
	 */
	public void setMissileToHitBonus(int missileToHitBonus) {
		this.missileToHitBonus = missileToHitBonus;
	}
	/**
	 * @return the missileToHitFactors
	 */
	public List<Modifier> getMissileToHitFactors() {
		return missileToHitFactors;
	}
	/**
	 * @param missileToHitFactors the missileToHitFactors to set
	 */
	public void setMissileToHitFactors(List<Modifier> missileToHitFactors) {
		this.missileToHitFactors = missileToHitFactors;
	}
	/**
	 * @return the armorClass
	 */
	public int getArmorClass() {
		return armorClass;
	}
	/**
	 * @param armorClass the armorClass to set
	 */
	public void setArmorClass(int armorClass) {
		this.armorClass = armorClass;
	}
	/**
	 * @return the armorClassFactors
	 */
	public List<ArmorClassModifier> getArmorClassFactors() {
		return armorClassFactors;
	}
	/**
	 * @param acMod the armorClassFactors to set
	 */
	public void setArmorClassFactors(List<ArmorClassModifier> acMod) {
		this.armorClassFactors = acMod;
	}
	/**
	 * @return the movement
	 */
	public int getMovement() {
		return movement;
	}
	/**
	 * @param movement the movement to set
	 */
	public void setMovement(int movement) {
		this.movement = movement;
	}
	/**
	 * @return the movementFactors
	 */
	public List<Modifier> getMovementFactors() {
		return movementFactors;
	}
	/**
	 * @param movementFactors the movementFactors to set
	 */
	public void setMovementFactors(List<Modifier> movementFactors) {
		this.movementFactors = movementFactors;
	}
	/**
	 * @return the initiativeModifier
	 */
	public int getInitiativeModifier() {
		return initiativeModifier;
	}
	/**
	 * @param initiativeModifier the initiativeModifier to set
	 */
	public void setInitiativeModifier(int initiativeModifier) {
		this.initiativeModifier = initiativeModifier;
	}
	/**
	 * @return the initiativeFactors
	 */
	public List<Modifier> getInitiativeFactors() {
		return initiativeFactors;
	}
	/**
	 * @param initiativeFactors the initiativeFactors to set
	 */
	public void setInitiativeFactors(List<Modifier> initiativeFactors) {
		this.initiativeFactors = initiativeFactors;
	}
	/**
	 * @return the supriseRoll
	 */
	public int getSupriseRoll() {
		return supriseRoll;
	}
	/**
	 * @param supriseRoll the supriseRoll to set
	 */
	public void setSupriseRoll(int supriseRoll) {
		this.supriseRoll = supriseRoll;
	}
	/**
	 * @return the surpriseFactors
	 */
	public List<Modifier> getSurpriseFactors() {
		return surpriseFactors;
	}
	/**
	 * @param surpriseFactors the surpriseFactors to set
	 */
	public void setSurpriseFactors(List<Modifier> surpriseFactors) {
		this.surpriseFactors = surpriseFactors;
	}
	/**
	 * @return the damageBonus
	 */
	public int getDamageBonus() {
		return damageBonus;
	}
	/**
	 * @param damageBonus the damageBonus to set
	 */
	public void setDamageBonus(int damageBonus) {
		this.damageBonus = damageBonus;
	}
	/**
	 * @return the damageFactors
	 */
	public List<Modifier> getDamageFactors() {
		return damageFactors;
	}
	/**
	 * @param damageFactors the damageFactors to set
	 */
	public void setDamageFactors(List<Modifier> damageFactors) {
		this.damageFactors = damageFactors;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CombatStatistics [" + (timestamp != null ? "timestamp=" + timestamp + ", " : "") + "meleeToHitBonus="
				+ meleeToHitBonus + ", "
				+ (meleeToHitFactors != null ? "meleeToHitFactors=" + meleeToHitFactors + ", " : "")
				+ "missileToHitBonus=" + missileToHitBonus + ", "
				+ (missileToHitFactors != null ? "missileToHitFactors=" + missileToHitFactors + ", " : "")
				+ "damageBonus=" + damageBonus + ", "
				+ (damageFactors != null ? "damageFactors=" + damageFactors + ", " : "") + "armorClass=" + armorClass
				+ ", " + (armorClassFactors != null ? "armorClassFactors=" + armorClassFactors + ", " : "")
				+ "movement=" + movement + ", "
				+ (movementFactors != null ? "movementFactors=" + movementFactors + ", " : "") + "initiativeModifier="
				+ initiativeModifier + ", "
				+ (initiativeFactors != null ? "initiativeFactors=" + initiativeFactors + ", " : "") + "supriseRoll="
				+ supriseRoll + ", " + (surpriseFactors != null ? "surpriseFactors=" + surpriseFactors : "") + "]";
	}	

}
