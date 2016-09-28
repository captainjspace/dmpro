package dmpro.modifier;

import dmpro.SavingThrowType;

public class SavingThrowModifier extends AbilityModifier {
	
	public SavingThrowType savingThrowType; //which saving throw to modify

	public SavingThrowModifier() {
		this.modifierType = ModifierType.SAVINGTHROW;
	}
	
	public SavingThrowModifier(SavingThrowType savingThrowType) {
		this();
		this.savingThrowType = savingThrowType;
	}
}
