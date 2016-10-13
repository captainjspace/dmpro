package dmpro.modifier;

public class MissileModifier extends AbilityModifier {
	
	public enum MissileModifierType {
		TOHIT,
		DAMAGE,
		ATTACKS,
		RANGE;
	}
	
	public MissileModifierType missileModifierType;
	
	public MissileModifier() {
		this.modifierType = ModifierType.MISSILE;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((missileModifierType == null) ? 0 : missileModifierType.hashCode());
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
		if (!(obj instanceof MissileModifier)) {
			return false;
		}
		MissileModifier other = (MissileModifier) obj;
		if (missileModifierType != other.missileModifierType) {
			return false;
		}
		return true;
	}
	
	
}
