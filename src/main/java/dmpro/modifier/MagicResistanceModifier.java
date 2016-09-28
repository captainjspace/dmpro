package dmpro.modifier;

import dmpro.spells.Spell;

/***
 * this may already be deprecated...
 * @author joshualandman
 *
 */
public class MagicResistanceModifier extends AbilityModifier {
	
	private Spell resistedSpell;
	private float percentResisted;
	private String savingThrowClass;
	private int savingThrowClassModifier;
	//Elf, Ring of Spell Turning, etc...
	private String source;

	public Spell getResistedSpell() {
		return resistedSpell;
	}
	public void setResistedSpell(Spell resistedSpell) {
		this.resistedSpell = resistedSpell;
	}
	public float getPercentResisted() {
		return percentResisted;
	}
	public void setPercentResisted(float percentResisted) {
		this.percentResisted = percentResisted;
	}
	public String getSavingThrowClass() {
		return savingThrowClass;
	}
	public void setSavingThrowClass(String savingThrowClass) {
		this.savingThrowClass = savingThrowClass;
	}
	public int getSavingThrowClassModifier() {
		return savingThrowClassModifier;
	}
	public void setSavingThrowClassModifier(int savingThrowClassModifier) {
		this.savingThrowClassModifier = savingThrowClassModifier;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
