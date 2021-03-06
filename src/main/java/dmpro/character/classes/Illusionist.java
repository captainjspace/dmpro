package dmpro.character.classes;

//import dmpro.character.classes.CharacterClassType;
import dmpro.items.DamageRoll;
import dmpro.utils.Dice;
import dmpro.utils.Die;

public class Illusionist extends CharacterClass implements SpellCaster {
	public Illusionist() {
		characterClassType = CharacterClassType.ILLUSIONIST;
		combatClass = CharacterClassType.MAGICUSER;
		className = "Illusionist";
		
		this.setHitDie(new Die (Dice.d4));
		this.setHitPointPerLevelAfterMax(1);//read from table?
		this.setMaxHitDice(10); //read from table?
		// no bonus this.addXPBonus(new XPBonus("wisdom", 15));
		this.setInitialGold(( new DamageRoll("2d4").getDamageRoll() * 10 ));
		//all could be in table
		this.setStartingProficiencies(1);
		this.setNewProficienyPerLevel(5);
		this.setNonProficiencyPenalty(-5);
	}

}
