package dmpro.modifier;

public class MovementModifier extends AbilityModifier {
	public enum MovementModifierType {
		SPEED, //faster slower - potions, rings, spells, etc
		ENCUMBRANCE, //base movement - carry weight
		ENVIRONMENT; //swamp, darkness, planar
	}
	
	public MovementModifierType movementModifierType;
	
	public MovementModifier() {
		this.modifierType = ModifierType.MOVEMENT;
	}

}
