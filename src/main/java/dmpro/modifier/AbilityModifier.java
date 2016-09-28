package dmpro.modifier;

import dmpro.Ability;
import dmpro.character.Character;
/**
 * AbilityModifier.java
 * <p>
 *  The AbilityModifier has a slot for an subclass of an ability  which allow for some stable code injection.
 *  
 *  I do have concerns that I will inevitable run into the infinite possibilities problem of-course.
 *  - particularly with loose spells, artifacts and the overwhelming volume of magic items.
 *  For that reason - I assume at some point functional programming or anonymous functions will have to come into play.
 *  
 *  Finally, there is a point where the dungeon master will simply have to inject chaos.
 *  A wish spell for example simply cannot be coded for in advance...
 *  
 *  For now this is will serve as the base of most Modifiers
 *  AttributeModifiers are narrow and of higher priority in comparison.
 *  
 */
public class AbilityModifier extends Modifier{

	public AbilityModifier() {
		this.modifierType = ModifierType.ABILITY;
	}
	
	Class<? extends Ability> abilityClass; //a place to introduce custom character operations
	
	private Ability ability=new Ability();
	private int modifier; //amount to add-subtract
	private String modifierFormula;
	
	public void setAbility(Character c) {
	}
	
	String description;
	//is this needed???
	private boolean isPermanent;
	
	public Ability getAbility() {
		return ability;
	}
	public void setAbility(Ability ability) {
		this.ability = ability;
	}
	public int getModifier() {
		return modifier;
	}
	public void setModifier(int modifier) {
		this.modifier = modifier;
	}
	public boolean isPermanent() {
		return isPermanent;
	}
	public void setPermanent(boolean isPermanent) {
		this.isPermanent = isPermanent;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbilityModifier [" + (abilityClass != null ? "abilityClass=" + abilityClass + ", " : "")
				+ (ability != null ? "ability=" + ability + ", " : "") + "modifier=" + modifier + ", "
				+ (modifierFormula != null ? "modifierFormula=" + modifierFormula + ", " : "")
				+ (description != null ? "description=" + description + ", " : "") + "isPermanent=" + isPermanent + "]";
	}
	public Class<? extends Ability> getAbilityClass() {
		return abilityClass;
	}
	public void setAbilityClass(Class<? extends Ability> abilityClass) {
		this.abilityClass = abilityClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the modifierFormula
	 */
	public String getModifierFormula() {
		return modifierFormula;
	}
	/**
	 * @param modifierFormula the modifierFormula to set
	 */
	public void setModifierFormula(String modifierFormula) {
		this.modifierFormula = modifierFormula;
	}
	

}
