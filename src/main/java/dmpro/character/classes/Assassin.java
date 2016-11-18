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
 * <p> Minimum Strength of 12, Intelligence of 11, Dexterity of 12
 */
public class Assassin extends CharacterClass  {

	// TODO finish config

	public Assassin() {
		characterClassType = CharacterClassType.ASSASSIN;
		combatClass = CharacterClassType.THIEF;
		
		className = "Assassin";
		this.setHitDie(new Die (Dice.d6));
		this.setMaxHitDice(15); //read from table?
		this.setInitialGold(( new DamageRoll("2d6").getDamageRoll() * 10 ));
		//all could be in table
		this.setStartingProficiencies(3);
		this.setNewProficienyPerLevel(4);
		this.setNonProficiencyPenalty(-2);
	}
}

