package dmpro.attributes;

import java.util.ArrayList;
/**
 * Attributes.java
 * 
 * <p>Attributes are fairly unique in that each receives boosts from race, magical items
 * and enchantments, some are permanent and some are temporary.  But at any given time the attributes is changed
 * it needs to generate Ability modifiers for various game time actions.
 * <p>  The most critical will be related to Stength and Dexterity which are combat related.
 * <br> Strength may begin at 15, but a Half-Orc moves permanently to 16.
 * <br> a half orc with Gauntlets of Ogre Power moves to 18/00 as long as Gauntlets are equipped.
 * <br>  A strength spell variably increases Strength for several hours.
 * <br>  A timer must be placed on the Strength spell and attribute queued in the modifier stack
 * and the engine must calculate new to Hit and Damage modifiers.
 * 
 */
import java.util.List;

import dmpro.modifier.AttributeModifier;
import dmpro.modifier.Modifiable;
import dmpro.modifier.Modifier;

public abstract class Attribute implements Modifiable {
	
	public enum AttributeType {
		STRENGTH("strength"),
		INTELLIGENCE("intelligence"),
		WISDOM("wisdom"),
		DEXTERITY("dexterity"),
		CONSTITUTION("constitution"),
		CHARISMA("charisma");
		
		private String regex;
		
		AttributeType(String regex) {
			this.regex = regex;
		}
		
		public String getRegex() {
			return this.regex;
		}
	}
	
	public AttributeType attributeType;
	List<AttributeModifier> attributeModifiers = new ArrayList<AttributeModifier>();
	List<Modifier>  outgoingModifiers = new ArrayList<Modifier>();
	
    public int fieldCount=0;
    public String attributeName;
    public int abilityScore;
    public int modifiedAbilityScore=-1; //the unset value
    
    public int fieldCount() { 
    	return fieldCount; 
    }
    
    public boolean dashCheck(String s) {
    	
     return 
    		 (s.matches(".*--.*") ) ? true : false;
    }

	public List<AttributeModifier> getAttributeModifiers() {
		return attributeModifiers;
	}

	public void setAttributeModifiers(List<AttributeModifier> attributeModifier) {
		this.attributeModifiers = attributeModifier;
	}

	public int getFieldCount() {
		return fieldCount;
	}

	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public int getAbilityScore() {
		return abilityScore;
	}

	public void setAbilityScore(int abilityScore) {
		this.abilityScore = abilityScore;
	}

	public int getModifiedAbilityScore() {
		//if set return modified otherwise just the base
		return modifiedAbilityScore;
	}

	public void setModifiedAbilityScore(int modifiedAbilityScore) {
		this.modifiedAbilityScore = modifiedAbilityScore;
	}

	public void addAttributeModifier(AttributeModifier am) {
		attributeModifiers.add(am);
	}
	
	public void addModifier(Modifier modifier) {
		
	}
}
