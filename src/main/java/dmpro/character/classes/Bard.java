/**
 * 
 */
package dmpro.character.classes;

import dmpro.items.DamageRoll;
import dmpro.utils.Dice;
import dmpro.utils.Die;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 24, 2016
 * 
 * Special class - technically starts as fighter, than thief, than bard...
 * check rules on create ...
 * TODO: configure this class
 */
public class Bard extends CharacterClass {
	
	public Bard() {
		characterClassType = CharacterClassType.BARD;
		combatClass = CharacterClassType.FIGHTER;
		
		className = "Bard";
		this.setHitDie(new Die (Dice.d6));
		this.setMaxHitDice(10); //read from table?
		this.setHitPointPerLevelAfterMax(1);//read from table?
		//this.setInitialGold(( new DamageRoll("2d6").getDamageRoll() * 10 ));
		//all could be in table
		this.setStartingProficiencies(3);
		this.setNewProficienyPerLevel(4);
		this.setNonProficiencyPenalty(-2);
	}

}
