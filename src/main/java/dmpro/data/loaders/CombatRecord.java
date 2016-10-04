package dmpro.data.loaders;

import java.util.HashMap;
import java.util.Map;

public class CombatRecord implements TSVData {
	static final int fieldCount = 24;
	
	String characterClassName;
	int experienceLevel;
	Map<Integer,Integer> armorClass = new HashMap<Integer,Integer>();
	String fighterEquivalent;

	public CombatRecord(String [] fields) {
		//String[] fields = lineInput.split("\t",24);
		
		characterClassName = fields[0];
		experienceLevel = Integer.parseInt(fields[1]);
		int ac=-10;
		for (int i = 2; i<23; i++) {
			this.armorClass.put(ac++, Integer.parseInt(fields[i]));
//			System.out.printf("Armor class %d, to hot %d\n", ac,Integer.parseInt(field[i]) );
		}
		fighterEquivalent = fields[23];
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
	 * @return the armorClass
	 */
	public Map<Integer, Integer> getArmorClass() {
		return armorClass;
	}

	/**
	 * @param armorClass the armorClass to set
	 */
	public void setArmorClass(Map<Integer, Integer> armorClass) {
		this.armorClass = armorClass;
	}

	/**
	 * @return the fighterEquivalent
	 */
	public String getFighterEquivalent() {
		return fighterEquivalent;
	}

	/**
	 * @param fighterEquivalent the fighterEquivalent to set
	 */
	public void setFighterEquivalent(String fighterEquivalent) {
		this.fighterEquivalent = fighterEquivalent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CombatRecord [" + (characterClassName != null ? "characterClassName=" + characterClassName + ", " : "")
				+ "experienceLevel=" + experienceLevel + ", "
				+ (armorClass != null ? "armorClass=" + armorClass + ", " : "")
				+ (fighterEquivalent != null ? "fighterEquivalent=" + fighterEquivalent : "") + "]";
	}
	
	
}
