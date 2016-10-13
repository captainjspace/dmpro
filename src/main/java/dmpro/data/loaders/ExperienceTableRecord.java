package dmpro.data.loaders;

public class ExperienceTableRecord implements TSVData{
	private final boolean DEBUG = false;
	
	static final int fieldCount = 7;
	
	String characterClass;
	long minExperiencePoints;
	long maxExperiencePoint;
	int experienceLevel;
	int hitDice;
	int bonusXP;
	String levelTitle;
	
	public ExperienceTableRecord() {}
	
	public String getCharacterClass() {
		return characterClass;
	}
	public void setCharacterClass(String characterClass) {
		this.characterClass = characterClass;
	}
	public long getMinExperiencePoints() {
		return minExperiencePoints;
	}
	public void setMinExperiencePoints(long minExperiencePoints) {
		this.minExperiencePoints = minExperiencePoints;
	}
	public long getMaxExperiencePoint() {
		return maxExperiencePoint;
	}
	public void setMaxExperiencePoint(long maxExperiencePoint) {
		this.maxExperiencePoint = maxExperiencePoint;
	}
	public int getExperienceLevel() {
		return experienceLevel;
	}
	public void setExperienceLevel(int experienceLevel) {
		this.experienceLevel = experienceLevel;
	}
	public int getBonusXP() {
		return bonusXP;
	}
	public void setBonusXP(int bonusXP) {
		this.bonusXP = bonusXP;
	}
	public String getLevelTitle() {
		return levelTitle;
	}
	public void setLevelTitle(String levelTitle) {
		this.levelTitle = levelTitle;
	}
	
	
	public ExperienceTableRecord(String characterClass, int minExperiencePoints, int maxExperiencePoint, int experienceLevel,
			int bonusXP, String levelTitle) {
		super();
		this.characterClass = characterClass;
		this.minExperiencePoints = minExperiencePoints;
		this.maxExperiencePoint = maxExperiencePoint;
		this.experienceLevel = experienceLevel;
		this.bonusXP = bonusXP;
		this.levelTitle = levelTitle;
	}
	
	public ExperienceTableRecord(String [] fields) {
		
		this.characterClass = fields[0];
		this.minExperiencePoints = Integer.parseInt(fields[1]);
		this.maxExperiencePoint = Integer.parseInt(fields[2]);
		this.experienceLevel = Integer.parseInt(fields[3]);
		this.hitDice = (!fields[4].isEmpty())?Integer.parseInt(fields[4]): 0;
		this.bonusXP = (!fields[5].isEmpty())? (Integer.parseInt(fields[5])) : 0;
		this.levelTitle = fields[6];
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExperienceTable [" + (characterClass != null ? "characterClass=" + characterClass + ", " : "")
				+ "minExperiencePoints=" + minExperiencePoints + ", maxExperiencePoint=" + maxExperiencePoint
				+ ", experienceLevel=" + experienceLevel + ", bonusXP=" + bonusXP + ", "
				+ (levelTitle != null ? "levelTitle=" + levelTitle : "") + "]";
	}

	/**
	 * @return the hitDice
	 */
	public int getHitDice() {
		return hitDice;
	}

	/**
	 * @param hitDice the hitDice to set
	 */
	public void setHitDice(int hitDice) {
		this.hitDice = hitDice;
	}

	
	
}
