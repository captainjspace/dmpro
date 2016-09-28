package dmpro.modifier;

public class ArmorClassModifier extends AbilityModifier {
	
	public enum ArmorClassModifierType {
		ALL, //armor
		MISSILE, //shield +1, +4 vs missiles
		FRONT, //shields...
		REAR,// generally a negative
		FLANK;//also likely negative - discounting shields and sych
	}

	public ArmorClassModifierType armorClassModifierType;
	public ArmorClassModifier() {
		this.modifierType = ModifierType.ARMORCLASS;
	}
}
