package dmpro.modifier;

import dmpro.attributes.Attribute;
import dmpro.attributes.Attribute.AttributeType;
/**
 * AttributeModifier.java
 * 
 * <p>
 * This is the simplest class of Modifier.  My original intent was to continually modify the statistics on the ability.
 * So this class needs to be cleaned up...
 * 
 * In fact I realized that the main Attributes :
 * <ul>
 * <li>Strength</li>
 * <li>Intelligence</li>
 * <li>Wisdom</li>
 * <li>Dexterity</li>
 * <li>Constitution</li>
 * <li>Charisma</li>
 * </ul>
 * 
 * <p> are all receivers of AttributeModifiers and permanant emitters of AbilityModifiers.
 * 
 * <br>
 * So for example and Elf assigns an AttributeModifier to Dexterity boosting the score from 17 to 18.
 * As a result, Dexterity emits several an ArmorClassModifier of -3 rather than -2 and MissileModifier for Range attacks
 * also an InitiativeModifier, and so on.
 * 
 * Therefore AttributeModifiers must be processed first - even if temporary.
 * Slowed, stunned or prone - characters lose dexterity advantage but that can be expressed by cancelling the AbilityModifier
 * rather than lowering Dexterity - a cursed item or spell or enchantment can target Dexterity.
 * 
 * Or permanent/semi-permanent increase could be granted by a magic item.
 * Either way the original ability score and currently modifying sources will be attached to the attribute
 * The generated ability will be attached to the character more generally
 * 
 * 
 * AttributeModifiers are 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * @version v0.1
 * @since 9/1/2016
 *
 */
public class AttributeModifier extends Modifier {
	
	public AttributeModifier() {
		this.modifierType = ModifierType.ATTRIBUTE;
	}
	
	Class <? extends Attribute> attributeToModify;
	AttributeType attributeType;
	int bonus;
	String description;
	boolean modifiesAbilityScore=true;//default
	
	public AttributeType getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Class<? extends Attribute> getAttributeToModify() {
		return attributeToModify;
	}
	public void setAttributeToModify(Class<? extends Attribute> attributeToModify) {
		this.attributeToModify = attributeToModify;
	}
	public boolean modifiesAbilityScore() {
		return modifiesAbilityScore;
	}
	public void setModifiesAbilityScore(boolean modifiesAbilityScore) {
		this.modifiesAbilityScore = modifiesAbilityScore;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributeToModify == null) ? 0 : attributeToModify.hashCode());
		result = prime * result + ((attributeType == null) ? 0 : attributeType.hashCode());
		result = prime * result + bonus;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (modifiesAbilityScore ? 1231 : 1237);
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
		if (!(obj instanceof AttributeModifier)) {
			return false;
		}
		AttributeModifier other = (AttributeModifier) obj;
		if (attributeToModify == null) {
			if (other.attributeToModify != null) {
				return false;
			}
		} else if (!attributeToModify.equals(other.attributeToModify)) {
			return false;
		}
		if (attributeType != other.attributeType) {
			return false;
		}
		if (bonus != other.bonus) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (modifiesAbilityScore != other.modifiesAbilityScore) {
			return false;
		}
		return true;
	}

	
}
