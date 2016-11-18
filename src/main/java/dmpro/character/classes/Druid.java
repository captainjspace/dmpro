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
 */
public class Druid extends CharacterClass {


	public Druid() {
		characterClassType = CharacterClassType.DRUID;
		combatClass = CharacterClassType.DRUID;
		className = "Druid";

		this.setHitDie(new Die (Dice.d8));
		//this.setHitPointPerLevelAfterMax(2);//read from table?
		this.setMaxHitDice(14); //read from table?
		this.addXPBonus(new XPBonus("wisdom", 15));
		this.setInitialGold(( new DamageRoll("3d6").getDamageRoll() * 10 ));
		//all could be in table
		this.setStartingProficiencies(2);
		this.setNewProficienyPerLevel(5);
		this.setNonProficiencyPenalty(-4);
	}
}
