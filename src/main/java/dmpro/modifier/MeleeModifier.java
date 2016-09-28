package dmpro.modifier;

public class MeleeModifier extends AbilityModifier {
	
	public enum MeleeModifierType {
		TOHIT,
		DAMAGE,
		ATTACK;
	}

	public MeleeModifierType meleeModifierType;
	
	public MeleeModifier() {
		this.modifierType = ModifierType.MELEE;
	}
}
