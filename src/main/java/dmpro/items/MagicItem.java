package dmpro.items;

import java.util.ArrayList;
import java.util.List;

import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.Modifier;


public class MagicItem extends Item {
	int experienceValue;
	
	MagicItemType magicItemType;
	
	public enum MagicItemType {
		POTION,
		SCROLL,
		RING,
		ROD,
		STAFF,
		WAND,
		ARMOR,
		MELEE,
		MISSILE,
		MISC
	}
	
	public MagicItem() {
		this.isMagic=true;	
	}

	public void setModifiers(List<Modifier> modifiers) {
		for (Modifier m : modifiers) {
			switch (m.modifierType) {
			case ATTRIBUTE:
				this.getModifiers().add((AttributeModifier)m);
				break;
			case ABILITY:
				this.getModifiers().add((AbilityModifier)m);
				break;
			default:
				modifiers.add(m);
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MagicItem [experienceValue=" + experienceValue + ", "
				+ "itemId=" + itemId + ", "
				+ (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "") + "itemValue=" + itemValue + ", weight="
				+ itemEncumbrance + ", isMagic=" + isMagic + ", isTreasure=" + isTreasure + ", isWeapon=" + isWeapon
				+ ", isProtection=" + isProtection + ", "
				+ (description != null ? "description=" + description + ", " : "") + "isMagic()=" + isMagic()
				+ ", isTreasure()=" + isTreasure() + ", isWeapon()=" + isWeapon() + ", isProtection()=" + isProtection()
				+ ", " + (getItemName() != null ? "getItemName()=" + getItemName() + ", " : "")
				+ (getItemType() != null ? "getItemType()=" + getItemType() + ", " : "") + "getItemValue()="
				+ getItemValue() + ", getWeight()=" + getItemEncumbrance() + ", getItemId()=" + getItemId() + ", "
				+ (super.toString() != null ? "toString()=" + super.toString() + ", " : "")
				+ (getDescription() != null ? "getDescription()=" + getDescription() + ", " : "")
				+ (getClass() != null ? "getClass()=" + getClass() + ", " : "") + "hashCode()=" + hashCode() + "]";
	}

	
	

}

