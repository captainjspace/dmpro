/**
 * 
 */
package dmpro.character.classes;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 1, 2016
 * 
 * Holds sum of proficiencies
 */
public class ProficiencyData {
	boolean hasFighter = false;
	int totalProficiencySlots;
	
	public ProficiencyData( boolean hasFighter, int totalProficiencySlots) {
		this.hasFighter = hasFighter;
		this.totalProficiencySlots = totalProficiencySlots;
	}
	
	/**
	 * @return the hasFighter
	 */
	public boolean isHasFighter() {
		return hasFighter;
	}
	/**
	 * @param hasFighter the hasFighter to set
	 */
	public void setHasFighter(boolean hasFighter) {
		this.hasFighter = hasFighter;
	}
	/**
	 * @return the totalProficiencySlots
	 */
	public int getTotalProficiencySlots() {
		return totalProficiencySlots;
	}
	/**
	 * @param totalProficiencySlots the totalProficiencySlots to set
	 */
	public void setTotalProficiencySlots(int totalProficiencySlots) {
		this.totalProficiencySlots = totalProficiencySlots;
	}
}
