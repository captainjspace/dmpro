package dmpro.character.race;

import dmpro.Ability.AbilityType;
import dmpro.attributes.Charisma;
import dmpro.attributes.Constitution;
import dmpro.attributes.Intelligence;
import dmpro.attributes.Strength;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.character.Language;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.SpellEffectModifier;
import dmpro.spells.SpellLibrary;

public class HalfOrc extends Race {
	/** HalfOrc Filth!!!
	 * Cleric (4),Fighter (10), thief (8), Assassin
	 * Cleric/Fighter, Cleric/Thief, Cleric/Assassin, Fighter/Thief, Fighter/Assassin
	 * 
	 * language: common, alignment, orcish
	 * max + 2 additional languages
	 * 
	 * Infravision 60'
	 * 
	 * Attributes:
	 * +1 Strength, Constitution
	 * -2 Charisma
	 */

	public HalfOrc() {
		// TODO Auto-generated constructor stub
		race=RaceType.HALFORC;
		this.languages.add(new Language("orcish"));
		
		AttributeModifier a = new AttributeModifier();
		a.setAttributeToModify(Strength.class);
		a.setAttributeType(AttributeType.STRENGTH);
		a.setBonus(1);
		a.setDescription("	-->HalfOrc +1 Strength");
		raceAttributes.add(a);
		
		a = new AttributeModifier();
		a.setAttributeToModify(Constitution.class);
		a.setAttributeType(AttributeType.CONSTITUTION);
		a.setBonus(1);
		a.setDescription("	-->HalfOrc +1 Constitution");
		raceAttributes.add(a);
		
		a = new AttributeModifier();
		a.setAttributeToModify(Charisma.class);
		a.setAttributeType(AttributeType.CHARISMA);
		a.setBonus(-2);
		a.setDescription("	-->HalfOrc -2 Charisma - you fugly!");
		raceAttributes.add(a);
		
		a= new AttributeModifier();
		a.setAttributeToModify(Intelligence.class);
		a.setAttributeType(AttributeType.INTELLIGENCE);
		a.setDescription("	-->HalfOrcs have max language bonus of 2 Int:11");
		a.setModifiesAbilityScore(false);
		raceAttributes.add(a);
		
		SpellLibrary sl = SpellLibrary.getSpellLibrary();
		SpellEffectModifier permanentSpellEffect = new SpellEffectModifier();
		permanentSpellEffect.getAbility().setAbilityName("Infravision 60'");
		permanentSpellEffect.getAbility().setAbilityType(AbilityType.SPELL);
		permanentSpellEffect.setPermanent(true);
		permanentSpellEffect.setDescription("HalfOrc Infravision");
		permanentSpellEffect.setSpellInEffect(sl.getSpell("infravision"));
		raceAbilities.add(permanentSpellEffect);
		permanentSpellEffect = null;
	}

}
