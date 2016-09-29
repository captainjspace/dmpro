package dmpro.character;
/**
 * SavingThrowType.java
 * 
 * Basic Enumeration for Saving throws
 * Modifier Engine can use enumeration to determine which saving throw to apply bonus to.
 * Additive Restrictive rules will need to be implemented (i.e., max of 2 rings of protection applied)
 * So if a ring wearer has a +2 ring and a +3 ring -- they get +3 ALL
 * But a a ring wearer with a +3 ring, and +3 armor -- gets +6 ALL
 * 
 * @author joshualandman
 *
 */

public enum SavingThrowType {
	PARALYZATIONPOISONDEATHMAGIC(1, "Paralyzation, Poison and Death Magic"),
	PETRIFACTIONPOLYMORPH(2, "Petrifaction/Polymorph"),
	RODSTAFFWAND(3, "Rods, Staves, and Wands"),
	BREATHWEAPON(4, "Breath Weapon"),
	SPELLS(5, "Spells"),
	POISON(6,"Poison"),//for poison alone
	MENTALATTACK(7,"Mental Attack Spells"), //wisdom
	ALL(7, "All Saves"); // convenience for general modifier +3 ring of protection, etc.
	
	int savingThrowTypeIndex;
	String savingThrowTypeName;
	
	SavingThrowType (int savingThrowTypeIndex, String savingThrowTypeName) {
		this.savingThrowTypeIndex = savingThrowTypeIndex;
		this.savingThrowTypeName = savingThrowTypeName;
	}
	
}