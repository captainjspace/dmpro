package dmpro.character.race;

import dmpro.Ability.AbilityType;
import dmpro.attributes.Dexterity;
import dmpro.attributes.Intelligence;
import dmpro.attributes.Strength;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.character.SavingThrowType;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.SavingThrowModifier;
import dmpro.modifier.SpellEffectModifier;
import dmpro.spells.SpellLibrary;
import dmpro.utils.Dice;

public class Halfling extends Race {
	/**
	 * Fighter max 6, Thief or Fighter/Thief
	 * restricted to thief equipment when operating as thief
	 * 
	 * Attribute Modifier - Magic Resistance & Poison Resistance X
	 *  (1-5) +1 Save against Rod, Staff, Wands | Spells | Poison -  per Constitution.getAbilityScore() / 3.5
	 * language: common, dwarven, elven, gnome, goblin, halfling, orcish
	 * 
	 * Attribute Modifiers: -1 Strength, +1 Dexterity X
	 * 
	 * Attribute Modifier = + 1 language per Int > 16 X
	 * 
	 * Infravision Stout - 60', Mixed 30' X ignore mixed
	 * 
	 * Ability:  X
	 * Grade Recognition:
	 * d4, 1-3, 75% - up or down
	 * Direction Recognition d4, 1-2, 50%
	 * 
	 * Surprise as Elf
	 */

	public Halfling() {
		// TODO Auto-generated constructor stub
		this.raceType = RaceType.HALFLING;
		
		AttributeModifier a = new AttributeModifier();
		a.setAttributeToModify(Dexterity.class);
		a.setAttributeType(AttributeType.DEXTERITY);
		a.setBonus(1);
		a.setDescription("	-->Halfling gets +1 Dexterity");
		raceAttributes.add(a);
		
		a = new AttributeModifier();
		a.setAttributeToModify(Strength.class);
		a.setAttributeType(AttributeType.STRENGTH);
		a.setBonus(-1);
		a.setDescription("	-->Halfling gets -1 Stregnth");
		raceAttributes.add(a);
		
		a = new AttributeModifier();
		a.setAttributeToModify(Dexterity.class);
		a.setAttributeType(AttributeType.DEXTERITY);
		a.setBonus(0);
		a.setDescription("	-->Halfling gets +1 to hit with bows (missileAttackAdjustment+1) - need to look at weapon");
		raceAttributes.add(a);
		
		a= new AttributeModifier();
		a.setAttributeToModify(Intelligence.class);
		a.setAttributeType(AttributeType.INTELLIGENCE);
		//a.setBonus(intelligence); DETERMINED BY CHARACTER AT RUNTIME, CONSIDER OTHER MODIFIERS
		a.setDescription("	-->	Halfling get (Intelligence - 16) additional bonus languages");
		a.setModifiesAbilityScore(false);
		raceAttributes.add(a);
		
		//SAVING THROW MODS
		AbilityModifier savingThrowBonus;
		SavingThrowType[] saves = { SavingThrowType.POISON, SavingThrowType.RODSTAFFWAND, SavingThrowType.SPELLS };
		
		for (SavingThrowType s : saves) {
			savingThrowBonus = new SavingThrowModifier(s);
			savingThrowBonus.getAbility().setAbilityName("Saving Throw Bonus " + s);
			savingThrowBonus.getAbility().setAbilityType(AbilityType.MAGICRESISTANCE);
			savingThrowBonus.setPermanent(true);
			savingThrowBonus.setModifierFormula("Math.floor(constitution.getAbilityScore/3.5)");
			savingThrowBonus.setDescription("Halfling MagicResistance");
			raceAbilities.add(savingThrowBonus);
			savingThrowBonus = null;
		}
		
		
		SpellLibrary sl = SpellLibrary.getSpellLibrary();
		SpellEffectModifier permanentSpellEffect = new SpellEffectModifier();
		permanentSpellEffect.getAbility().setAbilityName("Infravision 60'");
		permanentSpellEffect.getAbility().setAbilityType(AbilityType.SPELL);
		permanentSpellEffect.setPermanent(true);
		permanentSpellEffect.setDescription("Halfling Infravision");
		permanentSpellEffect.setSpellInEffect(sl.getSpell("infravision"));
		raceAbilities.add(permanentSpellEffect);
		permanentSpellEffect = null;
		
		AbilityModifier secret = new AbilityModifier();
		secret.getAbility().setAbilityName("Silence Movement and Surprise");
		secret.getAbility().setAbilityType(AbilityType.SURPRISE);
		secret.getAbility().setAbilityDie(Dice.d6);
		secret.getAbility().setRequiredRoll(4);
		secret.setPermanent(true);
		secret.setModifier(-2);
		secret.setDescription("Halfling Surprise on 1-4 if 90' separate from party, 1-2 if going through a door");
		secret.getAbility().setDescription("Surprise Advantage");
		raceAbilities.add(secret);
		
		AbilityModifier detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Grade Detection");
		detection.getAbility().setAbilityType(AbilityType.DIRECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance((float)0.75);
		detection.setPermanent(true);
		detection.setDescription("Halfling Grade Detection at 75%");
		raceAbilities.add(detection);
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Know Direction");
		detection.getAbility().setAbilityType(AbilityType.DIRECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance((float)0.50);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Halfling Sense of Direction 50%");
		raceAbilities.add(detection);
		detection = null;

	}

}
