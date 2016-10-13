package dmpro.data.loaders;

import dmpro.character.race.RaceType;

public class RaceSizeRecord implements TSVData {
	
	static final int fieldCount = 5;
	private RaceType raceType;
	private String heightRoll;
	private String weightRoll;
	private float femaleHeightMultiplier;
	private float femaleWeightMultiplier;
	
	public RaceSizeRecord (String [] fields) {
		raceType = RaceType.valueOf(fields[0]);
		heightRoll = fields[1];
		weightRoll = fields[2];
		femaleHeightMultiplier = Float.parseFloat(fields[3]);
		femaleWeightMultiplier = Float.parseFloat(fields[4]);
	}

	/**
	 * @return the raceType
	 */
	public RaceType getRaceType() {
		return raceType;
	}

	/**
	 * @return the heightRoll
	 */
	public String getHeightRoll() {
		return heightRoll;
	}

	/**
	 * @return the weightRoll
	 */
	public String getWeightRoll() {
		return weightRoll;
	}

	/**
	 * @return the femaleHeightMultiplier
	 */
	public float getFemaleHeightMultiplier() {
		return femaleHeightMultiplier;
	}

	/**
	 * @return the femaleWeightMultiplier
	 */
	public float getFemaleWeightMultiplier() {
		return femaleWeightMultiplier;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RaceSizeRecord [" + (raceType != null ? "raceType=" + raceType + ", " : "")
				+ (heightRoll != null ? "heightRoll=" + heightRoll + ", " : "")
				+ (weightRoll != null ? "weightRoll=" + weightRoll + ", " : "") + "femaleHeightMultiplier="
				+ femaleHeightMultiplier + ", femaleWeightMultiplier=" + femaleWeightMultiplier + "]";
	}
			

}
