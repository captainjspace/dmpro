/**
 * 
 */
package dmpro.data.loaders;

import dmpro.character.race.RaceType;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 10, 2016
 */
public class RaceThiefAbilityRecord extends ThiefAbilityRecord {
	
	static final int fieldCount = 9;
	RaceType raceType;
	

	public RaceThiefAbilityRecord() {}
	
	public RaceThiefAbilityRecord(String[] fields) {
		
		this.raceType = RaceType.valueOf(fields[0]);
		this.pickPockets = Float.parseFloat(fields[1]);
		this.openLocks = Float.parseFloat(fields[2]);
		this.findAndRemoveTraps = Float.parseFloat(fields[3]);
		this.moveSilently = Float.parseFloat(fields[4]);
		this.hideInShadows = Float.parseFloat(fields[5]);
		this.hearNoise = Float.parseFloat(fields[6]);
		this.climbWalls = Float.parseFloat(fields[7]);
		this.readLanguages = Float.parseFloat(fields[8]);
	}

	/**
	 * @return the raceType
	 */
	public RaceType getRaceType() {
		return raceType;
	}

	/**
	 * @param raceType the raceType to set
	 */
	public void setRaceType(RaceType raceType) {
		this.raceType = raceType;
	}

}
