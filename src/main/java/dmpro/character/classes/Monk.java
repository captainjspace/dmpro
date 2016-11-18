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
 * <p> Minimum Str 15, Wisdom 15, Dexteritry 15, constitution 11 </p>
 * NO ARMOR CLASS BONUS FOR DEXTERITY
 * 
 * TODO configure this class. seePHB p.31
 */
public class Monk extends CharacterClass {
	public Monk() {

		this.characterClassType=CharacterClassType.MAGICUSER;
		this.className="Magic-User";
		this.hasSpells = true;
		this.setHitDie(new Die(Dice.d4));
		this.setMaxHitDice(11);
		this.setHitPointPerLevelAfterMax(1);
		this.setExperiencePointsPerLevelAfterMax(375000);
		this.addXPBonus(new XPBonus("intelligence", 16));
		this.setInitialGold(( new DamageRoll("5d4").getDamageRoll()  ));

		this.setStartingProficiencies(1);
		this.setNewProficienyPerLevel(2);
		this.setNonProficiencyPenalty(-3);

		
	}

}
