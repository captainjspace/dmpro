package dmpro.data.loaders;


import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.character.race.RaceType;

public class RaceClassAgeRecord implements TSVData{
	
	static final int fieldCount = 3;
	private RaceType raceType;
	private CharacterClassType characterClassType;
	private String ageRoll;
	
	public RaceClassAgeRecord(String[] fields) {
		raceType = RaceType.valueOf(fields[0]);
		characterClassType = CharacterClassType.valueOf(fields[1]);
		ageRoll = fields[2];
	}

	/**
	 * @return the raceType
	 */
	public RaceType getRaceType() {
		return raceType;
	}

	/**
	 * @return the characterClassType
	 */
	public CharacterClassType getCharacterClassType() {
		return characterClassType;
	}

	/**
	 * @return the ageRoll
	 */
	public String getAgeRoll() {
		return ageRoll;
	}

}
