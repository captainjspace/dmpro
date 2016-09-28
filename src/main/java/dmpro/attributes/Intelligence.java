package dmpro.attributes;
import java.util.ArrayList;
import java.util.List;

import dmpro.spells.Spell;

public class Intelligence extends Attribute {
	
	static final int fieldCount = 5;
	
	int bonusLanguageModifier;
	int maxSpelllevel;
	float percentKnowSpell;
	int maxSpellLevel;
	List<Spell> spellImmunity=new ArrayList<Spell>();
	
	public Intelligence(String[] fields) {
		attributeType = AttributeType.INTELLIGENCE;
		attributeName = "Intelligence";
		abilityScore = Integer.parseInt(fields[0]);
		if (!dashCheck(fields[1]))
			bonusLanguageModifier = Integer.parseInt(fields[1]);
		if (!dashCheck(fields[2]))
			maxSpellLevel = Integer.parseInt(fields[2]);
		if (!dashCheck(fields[3]))
			percentKnowSpell = Float.parseFloat(fields[3]);
		if (!dashCheck(fields[4]))
			parseSpellImmunity(fields[4]);
		
		
	}
	public int getBonusLanguageModifier() {
		return bonusLanguageModifier;
	}
	public void setBonusLanguageModifier(int bonusLanguageModifier) {
		this.bonusLanguageModifier = bonusLanguageModifier;
	}
	public int getMaxSpelllevel() {
		return maxSpelllevel;
	}
	public void setMaxSpelllevel(int maxSpelllevel) {
		this.maxSpelllevel = maxSpelllevel;
	}
	public float getPercentKnowSpell() {
		return percentKnowSpell;
	}
	public void setPercentKnowSpell(float percentKnowSpell) {
		this.percentKnowSpell = percentKnowSpell;
	}
	public int getMaxSpellLevel() {
		return maxSpellLevel;
	}
	public void setMaxSpellLevel(int maxSpellLevel) {
		this.maxSpellLevel = maxSpellLevel;
	}
	public List<Spell> getSpellImmunity() {
		return spellImmunity;
	}
	
	public void setSpellImmunity(List<Spell> spellImmunity) {
		this.spellImmunity = spellImmunity;
	}
	
	public void parseSpellImmunity(String spells) {
		
		for (String s : spells.split(",")) {
			spellImmunity.add(new Spell(s.trim()));
		}
	}
	
	@Override
	public String toString() {
		return "Intelligence [bonusLanguageModifier=" + bonusLanguageModifier + ", maxSpelllevel=" + maxSpelllevel
				+ ", percentKnowSpell=" + percentKnowSpell + ", maxSpellLevel=" + maxSpellLevel + ", spellImmunity="
				+ spellImmunity + "]";
	}
	
	
	
	
}
