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
 * <p> requires strength 12, intelligence 9, wisdom 12, constitution 9, charisma 17</p>
 * <h3> Abilities </h3>
 * <ul>
 *  <li> Detect Evil 60' </li>
 *  <li> Immune to Disease </li>
 *  <li> + 2 on saving throws </li>
 *  <li> Lay on hands +2 hp/level 1 per day </li>
 *  <li> Cure disease 1 / week / per 5 levels
 *  <li> Protection from Evil 10' radius </li>
 *  <li> Turn undead as Cleric - 2 levels ( starts at 3rd level ) </li>
 *  <li> Summon Warhorse at 4th level - Intelligent Heavy Warhorse at 5+5, AC5 movement 18" - 1 per 10 years </li>
 *  <li> Holy Sword - will disple magic in a 10 foot radius </li>
 *  <li> Cleric spells at 9th level </li>
 *  <li> 10 Magic Items max - 1 armor, 1 shielf, 4 weapons, 4 other </li>
 *  <li> always give 10% of wealth </li>
 *  </ul>
 * 
 */
public class Paladin extends CharacterClass {

	public Paladin() {
		characterClassType = CharacterClassType.PALADIN;
		combatClass = CharacterClassType.FIGHTER;
		className = "Paladin";

		this.setHitDie(new Die (Dice.d10));
		this.setHitPointPerLevelAfterMax(3);//read from table?
		this.setMaxHitDice(9); //read from table?
		this.setExperiencePointsPerLevelAfterMax(350000);
		this.addXPBonus(new XPBonus("strength", 15));
		this.addXPBonus(new XPBonus("wisdom", 15));
		this.setInitialGold(( new DamageRoll("5d4").getDamageRoll() * 10 ));

		//all could be in table
		this.setStartingProficiencies(4);
		this.setNewProficienyPerLevel(3);
		this.setNonProficiencyPenalty(-2);	
	}
	
}
