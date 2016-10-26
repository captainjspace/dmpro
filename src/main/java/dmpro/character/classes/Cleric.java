package dmpro.character.classes;

import dmpro.character.classes.CharacterClassType;
import dmpro.items.DamageRoll;
import dmpro.utils.Dice;
import dmpro.utils.Die;

public class Cleric extends CharacterClass {

	public Cleric() {
		// TODO Finish Cleric Configure
		characterClassType = CharacterClassType.CLERIC;
		className = "Cleric";
		
		this.setHitDie(new Die (Dice.d8));
		this.setHitPointPerLevelAfterMax(2);//read from table?
		this.setMaxHitDice(9); //read from table?
		this.addXPBonus(new XPBonus("wisdom", 15));
		this.setInitialGold(( new DamageRoll("3d6").getDamageRoll() * 10 ));
		//all could be in table
		this.setStartingProficiencies(2);
		this.setNewProficienyPerLevel(4);
		this.setNonProficiencyPenalty(-3);
	}

}
