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
 * <p> requires strength 13, intelligence 13, wisdom 14, constitution 14 </p>
 * <p> plus 10% for 15 strength and intelligence </p>
 * 
 * <h3>Abilities</h3>
 * <ul>
 *   <li>Giant Class + (level) damage vs GiantClass</li>
 *   <li> Surprise 50% of the time, surprised on 1 in 6 </li>
 *   <li>Tracking see PHB p.24 </li>
 *   <li> Druid spell 8th level, MU spells 9th level </li>
 *   <li> Use all Clairaudience Clairvoyance, ESP and Telepathy Magic Items at 9th level </li>
 *   <li> 10th level attracts 2-24 followers </li>
 *   <li> must be good alignment </li>
 *   <li> Cannot hire men at arms until 8th level</li>
 *   <li> max 3 rangers working together </li>
 *   <li> own only what you can carry </li>
 * </ul>
 * 
 * TODO: finish adding ability modifiers
 *   
 */
public class Ranger extends CharacterClass {
	
	
	public Ranger() {

		//really all of this could be a data set
		characterClassType = CharacterClassType.RANGER;
		combatClass = CharacterClassType.FIGHTER;
		className = "Ranger";

		this.setHitDie(new Die (Dice.d8));
		this.setHitPointPerLevelAfterMax(2);//read from table?
		this.setMaxHitDice(11); //read from table?
		this.setExperiencePointsPerLevelAfterMax(250000);
		this.addXPBonus(new XPBonus("strength", 15));
		this.addXPBonus(new XPBonus("intelligence", 15));
		this.addXPBonus(new XPBonus("wisdom", 15));
		this.setInitialGold(( new DamageRoll("5d4").getDamageRoll() * 10 ));

		//all could be in table
		this.setStartingProficiencies(4);
		this.setNewProficienyPerLevel(3);
		this.setNonProficiencyPenalty(-2);

		//freehold??? world building stuff...
	}

}
