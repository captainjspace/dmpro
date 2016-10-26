/**
 * 
 */
package dmpro.data.loaders;

import java.util.Formatter;

import dmpro.character.classes.CharacterClassType;
import dmpro.character.race.RaceType;


/**
 * @author  Joshua Landman, joshua.s.landman@gmail.com created on Oct 13, 2016
 */

public class ClassRaceRecord implements TSVData {
	
	static final int fieldCount = 7;
	RaceType raceType;
	CharacterClassType characterClassType;
	boolean able;
	int maxLevel;
	String attribute;
	int attributeFloorScore;
	int levelIncreasePerAboveAttributeFloor;
	
	

	public ClassRaceRecord(String fields[]) {
		this.raceType = RaceType.valueOf(fields[0]);
		this.characterClassType = CharacterClassType.valueOf(fields[1]);
		this.able = Boolean.valueOf(fields[2]);
		if (!fields[3].isEmpty()) this.maxLevel = Integer.parseInt(fields[3]);
		if (!fields[4].isEmpty()) this.attribute = fields[4];
		if (!fields[5].isEmpty()) this.attributeFloorScore = Integer.parseInt(fields[5]);
		if (!fields[6].isEmpty()) this.levelIncreasePerAboveAttributeFloor = Integer.parseInt(fields[6]);
				
	}
	
	public String toString() {
		Formatter formatter = new Formatter();
		String retVal =
				formatter.format("ClassRaceLimit: Race: %s, Class %s, Permitted: %s",
						this.raceType, this.characterClassType, this.able).toString();
		formatter.close();
		return retVal;
	}
}