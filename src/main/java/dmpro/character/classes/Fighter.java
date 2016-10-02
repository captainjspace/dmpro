package dmpro.character.classes;

import java.util.ArrayList;
import java.util.List;

import dmpro.items.DamageRoll;
import dmpro.items.WeaponItem;
import dmpro.utils.Dice;
import dmpro.utils.Die;

public class Fighter extends CharacterClass {
	
	/** Fighting Men
	 * Strength min 9, Constitution 7 - handled ClassAttributeLoader
	 * +10%xp Strength over 15 - handled XPProcessor
	 * 9th level Freehold - castle, clear 20-50 mile radius
	 * attract men-at-arms (mercenaries)
	 * collect monthly revenue of 7 s.p. from inhabitants
	 * 250000 
	 * 
	 * Unearthed Arcana : weapon specialization
	 */

	private List<WeaponItem> specialized = new ArrayList<WeaponItem>();
	
	public Fighter() {
		
		//really all of this could be a data set
		characterClassType = CharacterClassType.FIGHTER;
		className = "Fighter";
		
		this.setHitDie(new Die (Dice.d10));
		this.setHitPointPerLevelAfterMax(3);//read from table?
		this.setMaxHitDice(9); //read from table?
		this.setExperiencePointsPerLevelAfterMax(250000);
		this.addXPBonus(new XPBonus("strength", 15));
		this.setInitialGold(( new DamageRoll("5d4").getDamageRoll() * 10 ));
		
		//all could be in table
		this.setStartingProficiencies(4);
		this.setNewProficienyPerLevel(3);
		this.setNonProficiencyPenalty(-2);
		
		//freehold??? world building stuff...
	}
	
	//specialization should just to be redundant entries in the proficiency list I think.

}
