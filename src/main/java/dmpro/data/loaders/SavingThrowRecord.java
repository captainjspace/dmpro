package dmpro.data.loaders;

public class SavingThrowRecord implements TSVData {

	//	CharacterClass characterClass;
	static final int fieldCount = 7;
	int characterClassId;
	String characterClassName;
	int characterClassExperienceLevel;
	int paralyzationPoisonOrDeathMagic;
	int petrifactionOrPolymorph;
	int rodStaffOrWand;
	int breathWeapon;
	int spell;

	public SavingThrowRecord () {};
	
	public SavingThrowRecord(String [] fields) {
		
		this.characterClassName = fields[0];
		this.characterClassExperienceLevel=Integer.parseInt(fields[1]);
		this.paralyzationPoisonOrDeathMagic=Integer.parseInt(fields[2]);
		this.petrifactionOrPolymorph=Integer.parseInt(fields[3]);
		this.rodStaffOrWand=Integer.parseInt(fields[4]);
		this.breathWeapon=Integer.parseInt(fields[5]);
		this.spell=Integer.parseInt(fields[6]);
	}

	public int getCharacterClassId() {
		return characterClassId;
	}

	public void setCharacterClassId(int characterClassId) {
		this.characterClassId = characterClassId;
	}

	public int getCharacterClassExperienceLevel() {
		return characterClassExperienceLevel;
	}

	public void setCharacterClassExperienceLevel(int characterClassExperienceLevel) {
		this.characterClassExperienceLevel = characterClassExperienceLevel;
	}

	public int getParalyzationPoisonOrDeathMagic() {
		return paralyzationPoisonOrDeathMagic;
	}

	public void setParalyzationPoisonOrDeathMagic(int paralyzationPoisonOrDeathMagic) {
		this.paralyzationPoisonOrDeathMagic = paralyzationPoisonOrDeathMagic;
	}

	public int getPetrifactionOrPolymorph() {
		return petrifactionOrPolymorph;
	}

	public void setPetrifactionOrPolymorph(int petrifactionOrPolymorph) {
		this.petrifactionOrPolymorph = petrifactionOrPolymorph;
	}

	public int getRodStaffOrWand() {
		return rodStaffOrWand;
	}

	public void setRodStaffOrWand(int rodStaffOrWand) {
		this.rodStaffOrWand = rodStaffOrWand;
	}

	public int getBreathWeapon() {
		return breathWeapon;
	}

	public void setBreathWeapon(int breathWeapon) {
		this.breathWeapon = breathWeapon;
	}

	public int getSpell() {
		return spell;
	}

	public void setSpell(int spell) {
		this.spell = spell;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SavingThrow [characterClassId=" + characterClassId + ", "
				+ (characterClassName != null ? "characterClassName=" + characterClassName + ", " : "")
				+ "characterClassExperienceLevel=" + characterClassExperienceLevel + ", paralyzationPoisonOrDeathMagic="
				+ paralyzationPoisonOrDeathMagic + ", petrifactionOrPolymorph=" + petrifactionOrPolymorph
				+ ", rodStaffOrWand=" + rodStaffOrWand + ", breathWeapon=" + breathWeapon + ", spell=" + spell + "]";
	}
}
