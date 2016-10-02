package dmpro.character.race;

import java.util.Arrays;

import dmpro.Ability.AbilityType;
import dmpro.attributes.Intelligence;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.character.Language;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.MagicResistanceModifier;
import dmpro.modifier.SpellEffectModifier;
import dmpro.spells.SpellLibrary;
import dmpro.utils.Dice;

public class HalfElf extends Race {
	/**  Half Elf Characteristics
	 * ToDO:
	 * Cleric max 5, Fighter max 8, Assassin max 11
	 * Druid or Thief
	 * Multi-class: Cleric/Fighter, Cleric/Ranger, Cleric/MagicUser, Figher/MagicUser
	 * Figher/Thief, MagicUser/Thief, Cleric/Fighter/MagicUser, Fighter/MagicUser/Thief
	 * 
	 * Thief limits armor and weapons - when using thief skills (introduce Mode???, Active Class???)
	 * experience remains divided evenly -even after levelling max reached
	 * 
	 * 30% sleep/charm resistance
	 * languages - common, elvish, gnome, halfling, goblin, hobgoblin, orcish, gnoll
	 * 
	 * intelligence bonus +1 above Int-16
	 * Infravision 60'
	 * secret doors (as elf)
	 * 
	 */

	public HalfElf() {
		// TODO Auto-generated constructor stub
		this.setRaceType(RaceType.HALFELF);
		
		//cheap 
		for (String s : Arrays.asList(Elf.elfLang)) {
			languages.add(new Language(s));
		}
		/**refactor elf group
		 *  All of the below are directly redundant with the elf race - minor property diffs.
		 */
		
		AttributeModifier a = new AttributeModifier();
		a= new AttributeModifier();
		a.setAttributeToModify(Intelligence.class);
		a.setAttributeType(AttributeType.INTELLIGENCE);
		//a.setBonus(intelligence); DETERMINED BY CHARACTER AT RUNTIME, CONSIDER OTHER MODIFIERS
		a.setDescription("	-->	Half-Elf get (Intelligence - 16) additional bonus languages");
		a.setModifiesAbilityScore(false);
		raceAttributes.add(a);
		a = null;
		
		SpellLibrary sl = SpellLibrary.getSpellLibrary();
		SpellEffectModifier abilityModifier = new SpellEffectModifier();
		abilityModifier.getAbility().setAbilityName("Infravision 60'");
		abilityModifier.getAbility().setAbilityType(AbilityType.SPELL);
		abilityModifier.setPermanent(true);
		abilityModifier.setDescription("Half Elf Infravision");
		abilityModifier.setSpellInEffect(sl.getSpell("infravision"));
		raceAbilities.add(abilityModifier);
		abilityModifier = null;
		
		MagicResistanceModifier resistance= new MagicResistanceModifier();
		resistance.getAbility().setAbilityName("Sleep Resistance");
		resistance.getAbility().setAbilityType(AbilityType.MAGICRESISTANCE);
		resistance.setPermanent(true);
		resistance.setDescription("Half Elf Sleep Resistance");
		resistance.setResistedSpell(sl.getSpell("sleep"));
		resistance.setPercentResisted(30);
		resistance.setSource("Half Elf Race Characteristic");
		raceAbilities.add(resistance);
		
		resistance= new MagicResistanceModifier();
		resistance.getAbility().setAbilityName("Half Elf Charm Resistance");
		resistance.getAbility().setAbilityType(AbilityType.MAGICRESISTANCE);
		resistance.setPermanent(true);
		resistance.setDescription("HalfElf Charm Resistance");
		resistance.setResistedSpell(sl.getSpell("charm person"));
		resistance.setPercentResisted(30);
		resistance.setSource("Half Elf Race Characteristic");
		raceAbilities.add(resistance);
		
		AbilityModifier secret = new AbilityModifier();
		secret.getAbility().setAbilityName("Secret Door Detection");
		secret.getAbility().setAbilityType(AbilityType.SECRETDOORS);
		secret.getAbility().setAbilityDie(Dice.d6);
		secret.getAbility().setRequiredRoll(1);
		secret.setModifier(1);
		secret.setPermanent(true);
		secret.setDescription("Half Elf Secret Door Detection + 1 when searching");
		secret.getAbility().setDescription("Detection of Secret Doors within 10'");
		raceAbilities.add(secret);
		
		secret = new AbilityModifier();
		secret.getAbility().setAbilityName("Concealed Door Detection");
		secret.getAbility().setAbilityType(AbilityType.SECRETDOORS);
		secret.getAbility().setAbilityDie(Dice.d6);
		secret.getAbility().setRequiredRoll(2);
		secret.setPermanent(true);
		secret.setModifier(1);
		secret.setDescription("Half Elf Concealed Door Detection +1 when searching");
		secret.getAbility().setDescription("Detection of Concealed Doors within 10'");
		raceAbilities.add(secret);
		
		
	}

}
