package dmpro.data.loaders;

public class TurnUndeadRecord implements TSVData {
	
	static final int fieldCount = 6;
	private int level;
	private UndeadType undeadType;
	private int toTurn;
	private TurnUndeadAffect turnUndeadAffect;
	private String numTurnedRoll;
	private String turnDurationRoll;
	
	
	public TurnUndeadRecord(String [] fields) {
		level = Integer.parseInt(fields[0]);
		undeadType = UndeadType.valueOf(fields[1].toUpperCase());
		toTurn = Integer.parseInt(fields[2]);
		turnUndeadAffect = TurnUndeadAffect.valueOf(fields[3]);
		numTurnedRoll = fields[4];
		turnDurationRoll = fields[5];
	}
	
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @return the undeadType
	 */
	public UndeadType getUndeadType() {
		return undeadType;
	}
	/**
	 * @return the toTurn
	 */
	public int getToTurn() {
		return toTurn;
	}
	/**
	 * @return the turnUndeadAffect
	 */
	public TurnUndeadAffect getTurnUndeadAffect() {
		return turnUndeadAffect;
	}
	/**
	 * @return the numTurnedRoll
	 */
	public String getNumTurnedRoll() {
		return numTurnedRoll;
	}
	/**
	 * @return the turnDurationRoll
	 */
	public String getTurnDurationRoll() {
		return turnDurationRoll;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TurnUndeadRecord [level=" + level + ", undeadType=" + undeadType + ", toTurn=" + toTurn
				+ ", turnUndeadAffect=" + turnUndeadAffect + ", numTurnedRoll=" + numTurnedRoll + ", turnDurationRoll="
				+ turnDurationRoll + "]";
	}
	
	

}
