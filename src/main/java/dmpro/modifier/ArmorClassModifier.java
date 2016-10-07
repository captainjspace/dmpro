package dmpro.modifier;

public class ArmorClassModifier extends AbilityModifier {
	
	public enum ArmorClassModifierType {
		ALL, //armor
		MISSILE, //shield +1, +4 vs missiles
		FRONT, //shields...
		REAR,// generally a negative
		FLANK,//also likely negative - discounting shields and sych
		DAMAGE; //field plate/fullplate
	}

	public ArmorClassModifierType armorClassModifierType;
	public ArmorClassModifier() {
		this.modifierType = ModifierType.ARMORCLASS;
	}
	/**
	 * @return the armorClassModifierType
	 */
	public ArmorClassModifierType getArmorClassModifierType() {
		return armorClassModifierType;
	}
	/**
	 * @param armorClassModifierType the armorClassModifierType to set
	 */
	public void setArmorClassModifierType(ArmorClassModifierType armorClassModifierType) {
		this.armorClassModifierType = armorClassModifierType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((armorClassModifierType == null) ? 0 : armorClassModifierType.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ArmorClassModifier)) {
			return false;
		}
		ArmorClassModifier other = (ArmorClassModifier) obj;
		if (armorClassModifierType != other.armorClassModifierType) {
			return false;
		}
		return true;
	}
	
}
