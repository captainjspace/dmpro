package dmpro.character.race;

import java.util.Arrays;
import dmpro.Ability.AbilityType;
import dmpro.attributes.Constitution;
import dmpro.attributes.Dexterity;
import dmpro.attributes.Intelligence;
import dmpro.attributes.Strength;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.character.Language;
import dmpro.items.WeaponItem.WeaponType;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.MagicResistanceModifier;
import dmpro.modifier.Modifier;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;
import dmpro.modifier.Modifier.ModifierType;
import dmpro.modifier.SpellEffectModifier;
import dmpro.modifier.WeaponSkillModifier;
import dmpro.modifier.WeaponSkillModifier.WeaponSkillModifierType;
import dmpro.spells.SpellLibrary;
//import dmpro.spells.SpellEffect;
//import dmpro.Character;
import dmpro.utils.Dice;

public class Elf extends Race {
	
	//List<Language> elfDefaultLanguages = new ArrayList<Language>();
	protected static String[] elfLang = {"elvish", "gnomish", "halfling", "goblin", 
	           "hobgoblin", "orcish", "gnollish", "common"};
////    List<AbilityModifier> raceAbilities = new ArrayList<AbilityModifier>();
//    List<AttributeModifier> raceAttributes = new ArrayList<AttributeModifier>();
	
	public Elf() {
		
		//refactor me
		raceType = RaceType.ELF;
		
		for (String s : Arrays.asList(elfLang)) {
			this.languages.add(new Language(s));
		}
		
		//TODO:  add modifier details in other character classes
		AttributeModifier a = new AttributeModifier();
		a.modifierSource = ModifierSource.RACE;
		a.modifierPriority = ModifierPriority.HIGH;
		a.setAttributeToModify(Dexterity.class);
		a.setAttributeType(AttributeType.DEXTERITY);
		a.setBonus(1);
		a.setDescription("Elf gets +1 Dexterity");
		raceAttributes.add(a);
		
		a = new AttributeModifier();
		a.modifierSource = ModifierSource.RACE;
		a.modifierPriority = ModifierPriority.HIGH;
		a.setAttributeToModify(Constitution.class);
		a.setAttributeType(AttributeType.CONSTITUTION);
		a.setBonus(-1);
		a.setDescription("Elf gets -1 Constitution");
		raceAttributes.add(a);
		
		//Rulebased
//		int intelligence = c.getIntelligence().getAbilityScore() - 15;
//		if ( intelligence > 0 ) {
//			a.setAttributeToModify(Intelligence.class);
//			a.setBonus(intelligence);
//			a.setDescription("	-->	Elf get (Intelligence - 15) additional bonus languages");
//			raceAttributes.add(a);
//		}
		
		/* these might be abilities */
		WeaponSkillModifier w = new WeaponSkillModifier();
		w.modifierSource = ModifierSource.RACE;
		w.modifierPriority = ModifierPriority.HIGH;
		w.modifierType = ModifierType.WEAPONSKILL;
		w.setWeaponType(WeaponType.ONEHANDEDSWORD);
		w.setWeaponSkillModifierType(WeaponSkillModifierType.TOHIT);
		w.setModifier(1);
		w.setDescription("Elf gets +1 to hit with one handed swords WeaponType.ONEHANDEDSWORD");
		raceAbilities.add(w);
		
		//need to split missile Attack and initiative
		w = new WeaponSkillModifier();
		w.modifierSource = ModifierSource.RACE;
		w.modifierPriority = ModifierPriority.HIGH;
		w.modifierType = ModifierType.WEAPONSKILL;
		w.setWeaponType(WeaponType.BOWCLASS);
		w.setWeaponSkillModifierType(WeaponSkillModifierType.TOHIT);
		w.setModifier(1);
		w.setDescription("Elf gets +1 to hit with bows WeaponType.BOWCLASS");
		raceAbilities.add(w);
		
		a= new AttributeModifier();
		a.modifierSource = ModifierSource.RACE;
		a.modifierPriority = ModifierPriority.HIGH;
		a.setAttributeToModify(Intelligence.class);
		a.setAttributeType(AttributeType.INTELLIGENCE);
		//a.setBonus(intelligence); DETERMINED BY CHARACTER AT RUNTIME, CONSIDER OTHER MODIFIERS
		a.setDescription("Elf gets (Intelligence - 15) additional bonus languages");
		a.setModifiesAbilityScore(false);
		raceAttributes.add(a);
		a = null;
		
		/* TODO:  This should be referencing the application */
		SpellLibrary sl = SpellLibrary.getSpellLibrary();

		SpellEffectModifier abilityModifier = new SpellEffectModifier();
		abilityModifier.modifierSource = ModifierSource.RACE;
		abilityModifier.modifierPriority = ModifierPriority.HIGH;
		abilityModifier.getAbility().setAbilityName("Infravision 60'");
		abilityModifier.getAbility().setAbilityType(AbilityType.SPELL);
		abilityModifier.setPermanent(true);
		abilityModifier.setDescription("Elvish Infravision");
		abilityModifier.setSpellInEffect(sl.getSpell("infravision"));
		raceAbilities.add(abilityModifier);
		abilityModifier = null;
		
		MagicResistanceModifier resistance= new MagicResistanceModifier();
		resistance.modifierSource = ModifierSource.RACE;
		resistance.modifierPriority = ModifierPriority.HIGH;
		resistance.getAbility().setAbilityName("Sleep Resistance");
		resistance.getAbility().setAbilityType(AbilityType.MAGICRESISTANCE);
		resistance.setPermanent(true);
		resistance.setDescription("Elven Sleep Resistance");
		resistance.setResistedSpell(sl.getSpell("sleep"));
		resistance.setPercentResisted(90);
		resistance.setSource("Elven Race Characteristic");
		raceAbilities.add(resistance);
		
		resistance= new MagicResistanceModifier();
		resistance.modifierSource = ModifierSource.RACE;
		resistance.modifierPriority = ModifierPriority.HIGH;
		resistance.getAbility().setAbilityName("Elven Charm Resistance");
		resistance.getAbility().setAbilityType(AbilityType.MAGICRESISTANCE);
		resistance.setPermanent(true);
		resistance.setDescription("Elven Charm Resistance");
		resistance.setResistedSpell(sl.getSpell("charm person"));
		resistance.setPercentResisted(90);
		resistance.setSource("Elven Race Characteristic");
		raceAbilities.add(resistance);
		
		AbilityModifier secret = new AbilityModifier();
		secret.modifierSource = ModifierSource.RACE;
		secret.modifierPriority = ModifierPriority.HIGH;
		secret.getAbility().setAbilityName("Secret Door Detection");
		secret.getAbility().setAbilityType(AbilityType.SECRETDOORS);
		secret.getAbility().setAbilityDie(Dice.d6);
		secret.getAbility().setRequiredRoll(1);
		secret.setModifier(1);
		secret.setPermanent(true);
		secret.setDescription("Elven Secret Door Detection + 1 when searching");
		secret.getAbility().setDescription("Detection of Secret Doors within 10'");
		raceAbilities.add(secret);
		
		secret = new AbilityModifier();
		secret.modifierSource = ModifierSource.RACE;
		secret.modifierPriority = ModifierPriority.HIGH;
		secret.getAbility().setAbilityName("Concealed Door Detection");
		secret.getAbility().setAbilityType(AbilityType.SECRETDOORS);
		secret.getAbility().setAbilityDie(Dice.d6);
		secret.getAbility().setRequiredRoll(2);
		secret.setPermanent(true);
		secret.setModifier(1);
		secret.setDescription("Elven Concealed Door Detection +1 when searching");
		secret.getAbility().setDescription("Detection of Concealed Doors within 10'");
		raceAbilities.add(secret);
		
		secret = new AbilityModifier();
		secret.modifierSource = ModifierSource.RACE;
		secret.modifierPriority = ModifierPriority.HIGH;
		secret.getAbility().setAbilityName("Silence Movement and Surprise");
		secret.getAbility().setAbilityType(AbilityType.SURPRISE);
		secret.getAbility().setAbilityDie(Dice.d6);
		secret.getAbility().setRequiredRoll(4);
		secret.setPermanent(true);
		secret.setModifier(-2);
		secret.setDescription("Elves Surprise on 1-4 if 90' separate from party, 1-2 if going through a door");
		secret.getAbility().setDescription("Surprise Advantage");
		raceAbilities.add(secret);
//		
		
	}

}