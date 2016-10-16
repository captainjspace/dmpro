/**
 * 
 */
package dmpro.character;


import dmpro.modifier.Modifier;



import java.util.Set;
import java.util.Date;
import java.util.HashSet;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 6, 2016
 * Pojo for combat stats
 * Created by the CharacterService when executing UpdateCombatStats
 * Triggered by the UPDATECOMBATSTATS message 
 * Currently directly queued in the Character requiredActions Set
 * 
 * @see dmpro.character.managementaction.UpdateCombatStats
 * @see dmpro.character.managementaction.CharacterManagementActions
 * @see dmpro.character.CharacterService
 * @see dmpro.character.Character
 * 
 * Active modifiers will be filtered down, and cast to subtypes and summed to get values.
 * Values can be used for standard reference or in the combat loop / applied to random rolls
 * Modifiers are retained and attached to "dice modifiers" for reference and display
 * Use of Sets prevents duplicates.
 * 
 */
public class CombatStatistics {
	
	private Date timestamp;
	
	private int meleeToHitBonus;
	private Set<Modifier> meleeToHitFactors = new HashSet<Modifier>();
	
	private int missileToHitBonus;
	private Set<Modifier> missileToHitFactors = new HashSet<Modifier>();
	
	private int meleeDamageBonus;
	private Set<Modifier> meleeDamageFactors = new HashSet<Modifier>();
	
	private int missileDamageBonus;
	private Set<Modifier> missileDamageFactors = new HashSet<Modifier>();
	
	private int armorClass;
	private Set<Modifier> armorClassFactors = new HashSet<Modifier>();
	
	private int movement;
	private Set<Modifier> movementFactors = new HashSet<Modifier>();
	
	private int initiativeModifier;
	private Set<Modifier> initiativeFactors = new HashSet<Modifier>();
	
	private int supriseRoll;
	private Set<Modifier> surpriseFactors = new HashSet<Modifier>();
	
	
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
	public Set<Modifier> getMeleeToHitFactors() {
		return meleeToHitFactors;
	}
	/**
	 * @param meleeToHitFactors the meleeToHitFactors to set
	 */
	public void setMeleeToHitFactors(Set<Modifier> meleeToHitFactors) {
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
	public Set<Modifier> getMissileToHitFactors() {
		return missileToHitFactors;
	}
	/**
	 * @param missileToHitFactors the missileToHitFactors to set
	 */
	public void setMissileToHitFactors(Set<Modifier> missileToHitFactors) {
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
	public Set<Modifier> getArmorClassFactors() {
		return armorClassFactors;
	}
	/**
	 * @param acMod the armorClassFactors to set
	 */
	public void setArmorClassFactors(Set<Modifier> acMod) {
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
	public Set<Modifier> getMovementFactors() {
		return movementFactors;
	}
	/**
	 * @param movementFactors the movementFactors to set
	 */
	public void setMovementFactors(Set<Modifier> movementFactors) {
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
	public Set<Modifier> getInitiativeFactors() {
		return initiativeFactors;
	}
	/**
	 * @param initiativeFactors the initiativeFactors to set
	 */
	public void setInitiativeFactors(Set<Modifier> initiativeFactors) {
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
	public Set<Modifier> getSurpriseFactors() {
		return surpriseFactors;
	}
	/**
	 * @param surpriseFactors the surpriseFactors to set
	 */
	public void setSurpriseFactors(Set<Modifier> surpriseFactors) {
		this.surpriseFactors = surpriseFactors;
	}
	/**
	 * @return the damageBonus
	 */
	public int getDamageBonus() {
		return meleeDamageBonus;
	}
	/**
	 * @param damageBonus the damageBonus to set
	 */
	public void setDamageBonus(int damageBonus) {
		this.meleeDamageBonus = damageBonus;
	}


	/**
	 * @return the meleeDamageBonus
	 */
	public int getMeleeDamageBonus() {
		return meleeDamageBonus;
	}
	/**
	 * @param meleeDamageBonus the meleeDamageBonus to set
	 */
	public void setMeleeDamageBonus(int meleeDamageBonus) {
		this.meleeDamageBonus = meleeDamageBonus;
	}
	/**
	 * @return the meleeDamageFactors
	 */
	public Set<Modifier> getMeleeDamageFactors() {
		return meleeDamageFactors;
	}
	/**
	 * @param meleeDamageFactors the meleeDamageFactors to set
	 */
	public void setMeleeDamageFactors(Set<Modifier> meleeDamageFactors) {
		this.meleeDamageFactors = meleeDamageFactors;
	}
	/**
	 * @return the missileDamageBonus
	 */
	public int getMissileDamageBonus() {
		return missileDamageBonus;
	}
	/**
	 * @param missileDamageBonus the missileDamageBonus to set
	 */
	public void setMissileDamageBonus(int missileDamageBonus) {
		this.missileDamageBonus = missileDamageBonus;
	}
	/**
	 * @return the missileDamageFactors
	 */
	public Set<Modifier> getMissileDamageFactors() {
		return missileDamageFactors;
	}
	/**
	 * @param missileDamageFactors the missileDamageFactors to set
	 */
	public void setMissileDamageFactors(Set<Modifier> missileDamageFactors) {
		this.missileDamageFactors = missileDamageFactors;
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
				+ "meleeDamageBonus=" + meleeDamageBonus + ", "
				+ (meleeDamageFactors != null ? "meleeDamageFactors=" + meleeDamageFactors + ", " : "")
				+ "missileDamageBonus=" + missileDamageBonus + ", "
				+ (missileDamageFactors != null ? "missileDamageFactors=" + missileDamageFactors + ", " : "")
				+ "armorClass=" + armorClass + ", "
				+ (armorClassFactors != null ? "armorClassFactors=" + armorClassFactors + ", " : "") + "movement="
				+ movement + ", " + (movementFactors != null ? "movementFactors=" + movementFactors + ", " : "")
				+ "initiativeModifier=" + initiativeModifier + ", "
				+ (initiativeFactors != null ? "initiativeFactors=" + initiativeFactors + ", " : "") + "supriseRoll="
				+ supriseRoll + ", " + (surpriseFactors != null ? "surpriseFactors=" + surpriseFactors : "") + "]";
	}	

}
