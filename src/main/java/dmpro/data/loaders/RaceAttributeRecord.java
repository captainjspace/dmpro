/**
 * 
 */
package dmpro.data.loaders;

import java.util.Formatter;

import dmpro.character.race.RaceType;

/**
 * @author Joshua Landman, <joshua.s.landman@gmail.com>
 * created on Oct 13, 2016
 */
public class RaceAttributeRecord implements TSVData {


	static final int fieldCount = 5;
	RaceType raceType;
	String sex;
	String attribute;
	int min;
	int max;

	public RaceAttributeRecord(String[] fields) {
		raceType = RaceType.valueOf(fields[0]);
		sex = fields[1];
		attribute = fields[2];
		min = Integer.parseInt(fields[3]);
		max = Integer.parseInt(fields[4]);
	}
	public String toString() {
		Formatter formatter = new Formatter();
		String record = 
				formatter.format("Attribute Limit: Attribute: %s, Race:%s, Sex:%s, Min:%d, Max:%d",
						attribute, raceType, sex, min, max).toString();
		formatter.close();
		return record;

	}


}
