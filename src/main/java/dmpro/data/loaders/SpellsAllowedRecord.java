package dmpro.data.loaders;

public class SpellsAllowedRecord {
	
	private String characterClassName;
	int experienceLevel;
	
	int Level1;
	int Level2;
	int Level3;
	int Level4;
	int Level5;
	int Level6;
	int Level7;
	int Level8;
	int Level9;

	public SpellsAllowedRecord(String lineInput) {
		
		String[] field = lineInput.split("\t", 11);
		
		characterClassName = field[0];
		experienceLevel = Integer.parseInt(field[1]);
		Level1 = (!field[2].isEmpty()) ? Integer.parseInt(field[2]) : 0;
		Level2 = (!field[3].isEmpty()) ? Integer.parseInt(field[3]) : 0;
		Level3 = (!field[4].isEmpty()) ? Integer.parseInt(field[4]) : 0;
		Level4 = (!field[5].isEmpty()) ? Integer.parseInt(field[5]) : 0;
		Level5 = (!field[6].isEmpty()) ? Integer.parseInt(field[6]) : 0;
		Level6 = (!field[7].isEmpty()) ? Integer.parseInt(field[7]) : 0;
		Level7 = (!field[8].isEmpty()) ? Integer.parseInt(field[8]) : 0;
		Level8 = (!field[9].isEmpty()) ? Integer.parseInt(field[9]) : 0;
		Level9 = (!field[10].isEmpty()) ? Integer.parseInt(field[10]) : 0;
		
		
		
	}

	public SpellsAllowedRecord() {
		//to initialize a spell caster
	}

	/**
	 * @return the characterClassName
	 */
	public String getCharacterClassName() {
		return characterClassName;
	}

	/**
	 * @param characterClassName the characterClassName to set
	 */
	public void setCharacterClassName(String characterClassName) {
		this.characterClassName = characterClassName;
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
	 * @return the level1
	 */
	public int getLevel1() {
		return Level1;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(int level1) {
		Level1 = level1;
	}

	/**
	 * @return the level2
	 */
	public int getLevel2() {
		return Level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(int level2) {
		Level2 = level2;
	}

	/**
	 * @return the level3
	 */
	public int getLevel3() {
		return Level3;
	}

	/**
	 * @param level3 the level3 to set
	 */
	public void setLevel3(int level3) {
		Level3 = level3;
	}

	/**
	 * @return the level4
	 */
	public int getLevel4() {
		return Level4;
	}

	/**
	 * @param level4 the level4 to set
	 */
	public void setLevel4(int level4) {
		Level4 = level4;
	}

	/**
	 * @return the level5
	 */
	public int getLevel5() {
		return Level5;
	}

	/**
	 * @param level5 the level5 to set
	 */
	public void setLevel5(int level5) {
		Level5 = level5;
	}

	/**
	 * @return the level6
	 */
	public int getLevel6() {
		return Level6;
	}

	/**
	 * @param level6 the level6 to set
	 */
	public void setLevel6(int level6) {
		Level6 = level6;
	}

	/**
	 * @return the level7
	 */
	public int getLevel7() {
		return Level7;
	}

	/**
	 * @param level7 the level7 to set
	 */
	public void setLevel7(int level7) {
		Level7 = level7;
	}

	/**
	 * @return the level8
	 */
	public int getLevel8() {
		return Level8;
	}

	/**
	 * @param level8 the level8 to set
	 */
	public void setLevel8(int level8) {
		Level8 = level8;
	}

	/**
	 * @return the level9
	 */
	public int getLevel9() {
		return Level9;
	}

	/**
	 * @param level9 the level9 to set
	 */
	public void setLevel9(int level9) {
		Level9 = level9;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpellsAllowedRecord ["
				+ (characterClassName != null ? "characterClassName=" + characterClassName + ", " : "")
				+ "experienceLevel=" + experienceLevel + ", Level1=" + Level1 + ", Level2=" + Level2 + ", Level3="
				+ Level3 + ", Level4=" + Level4 + ", Level5=" + Level5 + ", Level6=" + Level6 + ", Level7=" + Level7
				+ ", Level8=" + Level8 + ", Level9=" + Level9 + "]";
	}
	
	
	
}
