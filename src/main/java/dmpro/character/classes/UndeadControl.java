/**
 * 
 */
package dmpro.character.classes;

import java.util.Map;

import dmpro.character.Alignment;
import dmpro.data.loaders.TurnUndeadRecord;
import dmpro.data.loaders.UndeadType;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 20, 2016
 * 
 * Generic composition default methods for turning or controlling the undead
 * Would rather this be an interface but can't have members.
 */
public class UndeadControl {

	Alignment alignment; // impacts whether undead are enslaved or controlled
	Map<UndeadType, TurnUndeadRecord> turnUndeadMap;
	int adjustedLevel = -1; //Paladin at minus two
	
	/**
	 * @return the alignment
	 */
	public Alignment getAlignment() {
		return alignment;
	}

	/**
	 * @return the turnUndeadMap
	 */
	public Map<UndeadType, TurnUndeadRecord> getTurnUndeadMap() {
		return turnUndeadMap;
	}

	/**
	 * @param turnUndeadMap the turnUndeadMap to set
	 */
	public void setTurnUndeadMap(Map<UndeadType, TurnUndeadRecord> turnUndeadMap) {
		this.turnUndeadMap = turnUndeadMap;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	
	/**
	 * @return the adjustedLevel
	 */
	public int getAdjustedLevel() {
		return adjustedLevel;
	}

	/**
	 * @param adjustedLevel the adjustedLevel to set
	 */
	public void setAdjustedLevel(int adjustedLevel) {
		this.adjustedLevel = adjustedLevel;
	}

	public UndeadControl() {}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adjustedLevel;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((turnUndeadMap == null) ? 0 : turnUndeadMap.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UndeadControl other = (UndeadControl) obj;
		if (adjustedLevel != other.adjustedLevel)
			return false;
		if (alignment != other.alignment)
			return false;
		if (turnUndeadMap == null) {
			if (other.turnUndeadMap != null)
				return false;
		} else if (!turnUndeadMap.equals(other.turnUndeadMap))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UndeadControl [alignment=" + alignment + ", turnUndeadMap=" + turnUndeadMap + ", adjustedLevel="
				+ adjustedLevel + "]";
	}
	
	
}
