package dmpro.data.loaders;

public class ThiefAbilityRecord implements TSVData {
	/**
	 * a data record - could be an inner class
	 */
	static final int fieldCount = 9;
	int experienceLevel;
	float pickPockets;
	float openLocks;
	float findAndRemoveTraps;
	float moveSilently;
	float hideInShadows;
	float hearNoise;
	float climbWalls;
	float readLanguages;
	
	public ThiefAbilityRecord() {}
	
	public ThiefAbilityRecord(String[] fields) {
		
		this.experienceLevel = Integer.parseInt(fields[0]);
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
	 * @return the experienceLevel
	 */
	public int getExperienceLevel() {
		return experienceLevel;
	}

	/**
	 * @param experienceLevel the experienceLevel to set
	 */
	public void setExperienceLevel(int experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	/**
	 * @return the pickPockets
	 */
	public float getPickPockets() {
		return pickPockets;
	}

	/**
	 * @param pickPockets the pickPockets to set
	 */
	public void setPickPockets(float pickPockets) {
		this.pickPockets = pickPockets;
	}

	/**
	 * @return the openLocks
	 */
	public float getOpenLocks() {
		return openLocks;
	}

	/**
	 * @param openLocks the openLocks to set
	 */
	public void setOpenLocks(float openLocks) {
		this.openLocks = openLocks;
	}

	/**
	 * @return the findAndRemoveTraps
	 */
	public float getFindAndRemoveTraps() {
		return findAndRemoveTraps;
	}

	/**
	 * @param findAndRemoveTraps the findAndRemoveTraps to set
	 */
	public void setFindAndRemoveTraps(float findAndRemoveTraps) {
		this.findAndRemoveTraps = findAndRemoveTraps;
	}

	/**
	 * @return the moveSilently
	 */
	public float getMoveSilently() {
		return moveSilently;
	}

	/**
	 * @param moveSilently the moveSilently to set
	 */
	public void setMoveSilently(float moveSilently) {
		this.moveSilently = moveSilently;
	}

	/**
	 * @return the hideInShadows
	 */
	public float getHideInShadows() {
		return hideInShadows;
	}

	/**
	 * @param hideInShadows the hideInShadows to set
	 */
	public void setHideInShadows(float hideInShadows) {
		this.hideInShadows = hideInShadows;
	}

	/**
	 * @return the hearNoise
	 */
	public float getHearNoise() {
		return hearNoise;
	}

	/**
	 * @param hearNoise the hearNoise to set
	 */
	public void setHearNoise(float hearNoise) {
		this.hearNoise = hearNoise;
	}

	/**
	 * @return the climbWalls
	 */
	public float getClimbWalls() {
		return climbWalls;
	}

	/**
	 * @param climbWalls the climbWalls to set
	 */
	public void setClimbWalls(float climbWalls) {
		this.climbWalls = climbWalls;
	}

	/**
	 * @return the readLanguages
	 */
	public float getReadLanguages() {
		return readLanguages;
	}

	/**
	 * @param readLanguages the readLanguages to set
	 */
	public void setReadLanguages(float readLanguages) {
		this.readLanguages = readLanguages;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThiefAbilityRecord [experienceLevel=" + experienceLevel + ", pickPockets=" + pickPockets
				+ ", openLocks=" + openLocks + ", findAndRemoveTraps=" + findAndRemoveTraps + ", moveSilently="
				+ moveSilently + ", hideInShadows=" + hideInShadows + ", hearNoise=" + hearNoise + ", climbWalls="
				+ climbWalls + ", readLanguages=" + readLanguages + "]";
	}
}
