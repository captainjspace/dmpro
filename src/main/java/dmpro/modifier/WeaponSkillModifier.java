/**
 * 
 */
package dmpro.modifier;

import dmpro.items.WeaponItem;
import dmpro.items.WeaponItem.WeaponType;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 10, 2016
 * 
 * This class will handle non-proficiency penalties and specialization
 */
public class WeaponSkillModifier extends AbilityModifier {

	private WeaponType weaponType;
	private WeaponItem weaponItem;
	private WeaponSkillModifierType weaponSkillModifierType;

	public enum WeaponSkillModifierType {
		TOHIT,
		DAMAGE,
		ATTACKS,
		RANGE;
	}
	
	public WeaponSkillModifier() {
		this.modifierType = ModifierType.WEAPONSKILL;
	}
	
	/**
	 * @return the weaponType
	 */
	public WeaponType getWeaponType() {
		return weaponType;
	}
	/**
	 * @param weaponType the weaponType to set
	 */
	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}
	/**
	 * @return the weaponItem
	 */
	public WeaponItem getWeaponItem() {
		return weaponItem;
	}
	/**
	 * @param weaponItem the weaponItem to set
	 */
	public void setWeaponItem(WeaponItem weaponItem) {
		this.weaponItem = weaponItem;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((weaponItem == null) ? 0 : weaponItem.hashCode());
		result = prime * result + ((weaponSkillModifierType == null) ? 0 : weaponSkillModifierType.hashCode());
		result = prime * result + ((weaponType == null) ? 0 : weaponType.hashCode());
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
		if (!(obj instanceof WeaponSkillModifier)) {
			return false;
		}
		WeaponSkillModifier other = (WeaponSkillModifier) obj;
		if (weaponItem == null) {
			if (other.weaponItem != null) {
				return false;
			}
		} else if (!weaponItem.equals(other.weaponItem)) {
			return false;
		}
		if (weaponSkillModifierType != other.weaponSkillModifierType) {
			return false;
		}
		if (weaponType != other.weaponType) {
			return false;
		}
		return true;
	}
	/**
	 * @return the weaponSkillModifierType
	 */
	public WeaponSkillModifierType getWeaponSkillModifierType() {
		return weaponSkillModifierType;
	}
	/**
	 * @param weaponSkillModifierType the weaponSkillModifierType to set
	 */
	public void setWeaponSkillModifierType(WeaponSkillModifierType weaponSkillModifierType) {
		this.weaponSkillModifierType = weaponSkillModifierType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeaponSkillModifier [" + (weaponType != null ? "weaponType=" + weaponType + ", " : "")
				+ (weaponItem != null ? "weaponItem=" + weaponItem + ", " : "")
				+ (weaponSkillModifierType != null ? "weaponSkillModifierType=" + weaponSkillModifierType + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (modifierType != null ? "modifierType=" + modifierType + ", " : "")
				+ (modifierSource != null ? "modifierSource=" + modifierSource + ", " : "")
				+ (modifierPriority != null ? "modifierPriority=" + modifierPriority : "") + "]";
	}
	
	
	
	
}
