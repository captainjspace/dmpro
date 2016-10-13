
package dmpro.modifier;

/**
 * Modifier.java (and it's subclasses...
 * 
 * Categorized at the top level by ModifierType, ModifierSource and ModifierPriorty enumerations.
 * Subclasses continue with enumerations - defining a route for execution of basic math somewhere.
 * 
 * <p> Modifiers are the crux of event evaluation during game play.  They are statistically critical and layered.
 * Modifiers can be injected by any entity.
 * There are numerous permutations of known modifiers.
 * I've cataloged as many as I could off the top of my head and intend by brute force
 * to evaluate the enumerations and determine what the die roll to apply the modifiers to.
 * Ultimate game play is driven by entered die rolls or ramdomly generated die rolls.
 * Options for both are included.
 * 
 * <p> There is a distinction between the "damage" bonus of strength.
 * I always played the game starting from the strength bonus and moving on to specialization, and magical bonuses,
 * then finally spell bonuses. In writing the code I realized that actually Strength is just another modifier emitter.
 * So in the case of of strength you might receive an AttributeModifier and emit 2 Melee Modifiers.
 * All are active.  They are evaluated -on change- and become critical in combat.
 * 
 * <p> Another critical reason for stacking the layer of classes which seem fairly empty the intent to be declarative.
 * I have no intention of building classes for magic items.  They need to be described as JSON objects which will allow 
 * simple injection into this gaming world.
 * 
 * Using a relatively simple notation with JSON an Item can be injected into the game identifying which 
 * Modifier subclass will be instanced, what key data will be needed, and the modifier engine will have a clear directive of where to 
 * apply bonuses during game play when random numbers are generated.
 * 
 * <code>
 * {
	"itemId": 2,
	"itemName": "Ring of Dexterity",
	"itemType": "MagicItemType.RING",
	"itemValue": 1000,
	"weight": 1,
	"isMagic": true,
	"isTreasure": true,
	"isWeapon": false,
	"isProtection": false,
	"description": "test description",
	"modifiers": [
		{
			"CLASSNAME": "dmpro.attributes.AttributeModifier",
			"INSTANCE": {
				"attributeType": "DEXTERITY",
				"bonus": 1,
				"description": "\t--\u003e\tRing of Dexterity +1",
				"modifiesAbilityScore": true,
				"modifierType": "ATTRIBUTE"
			}
		}
	]
}
</code>
 *  
 * 
 * 
 * @author joshualandman
 *
 */
public class Modifier {
	
	public enum ModifierType {
		ABILITY, //general
		ATTRIBUTE, //modifies attribute score (generates other modifiers
		SPELLEFFECT, //generate
		SAVINGTHROW, //boosts/reduces saving throws
		MAGICRESISTANCE, //spell turning, percentage reductions - general
		MELEE, //to hit, damage, attacks per round
		MISSILE,//to hit, attacks per round
		INITIATIVE, //combat engine
		SURPRISE, // encounters
		ARMORCLASS, //armor class boost/reduce
		MOVEMENT,//impacts movement rate
		ENVIRONMENT,//impacts what actions are allowed in this env;
		DAMAGE,
		WEAPONSKILL;
	}
	
	public enum ModifierSource {
		UNIVERSE,
		PLANE,
		LOCATION,
		ENCOUNTER,
		PARTY,
		CLASS,
		RACE,
		ATTRIBUTE,
		ENCHANTMENT,
		MAGICITEM,
		SPELLEFFECT,
		ARMOR;
	}

	public enum ModifierPriority {
		LOW,
		MEDIUM,
		HIGH;
	}
	
	/**
	 * Subclasses in package should declare there type
	 * TODO - relocate all subclasses and change to protected
	 */
	public ModifierType modifierType;
	public ModifierSource modifierSource;
	public ModifierPriority modifierPriority;
	
	/** 
	 * engines will use the type to determine how to modify state
	 * @return ModifierType
	 */
	public ModifierType getModifierType() {
		return modifierType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modifierPriority == null) ? 0 : modifierPriority.hashCode());
		result = prime * result + ((modifierSource == null) ? 0 : modifierSource.hashCode());
		result = prime * result + ((modifierType == null) ? 0 : modifierType.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Modifier)) {
			return false;
		}
		Modifier other = (Modifier) obj;
		if (modifierPriority != other.modifierPriority) {
			return false;
		}
		if (modifierSource != other.modifierSource) {
			return false;
		}
		if (modifierType != other.modifierType) {
			return false;
		}
		return true;
	}
	
	
}
