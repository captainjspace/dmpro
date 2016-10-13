package dmpro.modifier;

public class MeleeModifier extends AbilityModifier {
	
	public enum MeleeModifierType {
		TOHIT,
		DAMAGE,
		ATTACK;
	}

	public MeleeModifierType meleeModifierType;
	
	public MeleeModifier() {
		this.modifierType = ModifierType.MELEE;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((meleeModifierType == null) ? 0 : meleeModifierType.hashCode());
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
		if (!(obj instanceof MeleeModifier)) {
			return false;
		}
		MeleeModifier other = (MeleeModifier) obj;
		if (meleeModifierType != other.meleeModifierType) {
			return false;
		}
		return true;
	}
	
	
}
