package dmpro.character.classes;

import dmpro.items.DamageRoll;

public class Cleric extends CharacterClass {

	public Cleric() {
		// TODO Auto-generated constructor stub
		this.addXPBonus(new XPBonus("wisdom", 15));
		this.setInitialGold(( new DamageRoll("3d6").getDamageRoll() * 10 ));
	}

}
