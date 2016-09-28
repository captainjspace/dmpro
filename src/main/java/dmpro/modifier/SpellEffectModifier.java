package dmpro.modifier;

import dmpro.spells.Spell;

public class SpellEffectModifier extends AbilityModifier {
	
	public SpellEffectModifier() {
		this.modifierType = ModifierType.SPELLEFFECT;
	}
	
	private Spell spellInEffect;

	public Spell getSpellInEffect() {
		return spellInEffect;
	}
	public void setSpellInEffect(Spell spellInEffect) {
		this.spellInEffect = spellInEffect;
	}
	
	

}
