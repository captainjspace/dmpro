package dmpro.modifier;

public class MissileModifier extends AbilityModifier {
	
	public enum MissileModifierType {
		TOHIT,
		DAMAGE,
		ATTACKS,
		RANGE;
	}
	
	public MissileModifierType missileModifierType;
	
	public MissileModifier() {
		this.modifierType = ModifierType.MISSILE;
	}
}
