package dmpro;

public class ThiefAbilityRecord {
	/**
	 * a data record - could be an inner class
	 */
	int experienceLevel;
	float pickPockets;
	float openLocks;
	float findAndRemoveTraps;
	float moveSilently;
	float hideInShadows;
	float hearNoise;
	float climbWalls;
	float readLanguages;
	
	public ThiefAbilityRecord(String lineInput) {
		String[] field = lineInput.split("\t",9);
		this.experienceLevel = Integer.parseInt(field[0]);
		this.pickPockets = Float.parseFloat(field[1]);
		this.openLocks = Float.parseFloat(field[2]);
		this.findAndRemoveTraps = Float.parseFloat(field[3]);
		this.moveSilently = Float.parseFloat(field[4]);
		this.hideInShadows = Float.parseFloat(field[5]);
		this.hearNoise = Float.parseFloat(field[6]);
		this.climbWalls = Float.parseFloat(field[7]);
		this.readLanguages = Float.parseFloat(field[8]);
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
