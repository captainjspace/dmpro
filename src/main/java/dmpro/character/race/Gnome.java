package dmpro.character.race;

import dmpro.Ability.AbilityType;
import dmpro.attributes.Intelligence;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.character.Language;
import dmpro.character.SavingThrowType;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.SavingThrowModifier;
import dmpro.modifier.SpellEffectModifier;
import dmpro.spells.SpellLibrary;
import dmpro.utils.Dice;

public class Gnome extends Race {
	
	/** Ah the Gnome - so many gardens, so many illusions
	 * Fighter (6), Illusionist (7), Thief, Assassin(8)
	 * Figher/Thief, Illusionist/Thief, Fighter/Illusionist
	 * 
	 * Magic Resistance:Rods,Staves and Wands and Spells
	 * +1-5 Math.floor(constitution.getAbilityScore() / 3.5)
	 * 
	 * languages: gnome,common, alignment, dwarven,halfling,goblin, kobold, 
	 * burrowing animals (moles, badgers, ground squirrels)
	 * max of 2 additional
	 * 
	 * Infravision 60'
	 * 
	 * Detection:
	 * Grade or Slope 80%, d10, 1-8
	 * Detect Unsafe walls, ceilings, floors: 70%, d10, 1-1
	 * Depth Underground: 60%, d10, 1-6
	 * Direction underground 50%
	 * 
	 * Melee:
	 * +1 to hit: kobolds, goblins
	 * monster at -4 : gnolls, bugbears, ogres, trolls, ogres, ogre magi, giants, titans
	 */

	public Gnome() {
		raceType = RaceType.GNOME;
		
		
		for (String i : new String[]{"dwarven", "gnome", "halfling", "goblin", "kobold","common","burrowing animals:moles,badgers,ground squirrels"})
			this.languages.add(new Language(i));
		
		AttributeModifier a= new AttributeModifier();
		a.setAttributeToModify(Intelligence.class);
		a.setAttributeType(AttributeType.INTELLIGENCE);
		a.setDescription("	-->Gnomes have a max language bonus of 2 Int:11");
		a.setModifiesAbilityScore(false);
		raceAttributes.add(a);
		
		//SAVING THROW MODS
		AbilityModifier savingThrowBonus;
		
		SavingThrowType[] saves = { SavingThrowType.RODSTAFFWAND, SavingThrowType.SPELLS };
		for (SavingThrowType s : saves) {
			savingThrowBonus = new SavingThrowModifier(s);
			savingThrowBonus.getAbility().setAbilityName("Saving Throw Bonus " + s);
			savingThrowBonus.getAbility().setAbilityType(AbilityType.MAGICRESISTANCE);
			savingThrowBonus.setPermanent(true);
			savingThrowBonus.setModifierFormula("Math.floor(constitution.getModifiedAbilityScore()/3.5)");
			savingThrowBonus.setDescription("Gnomish MagicResistance");
			raceAbilities.add(savingThrowBonus);
			savingThrowBonus = null;
		}
		
		
		SpellLibrary sl = SpellLibrary.getSpellLibrary();
		SpellEffectModifier permanentSpellEffect = new SpellEffectModifier();
		permanentSpellEffect.getAbility().setAbilityName("Infravision 60'");
		permanentSpellEffect.getAbility().setAbilityType(AbilityType.SPELL);
		permanentSpellEffect.setPermanent(true);
		permanentSpellEffect.setDescription("Gnomish Infravision");
		permanentSpellEffect.setSpellInEffect(sl.getSpell("infravision"));
		raceAbilities.add(permanentSpellEffect);
		permanentSpellEffect = null;
		
		AbilityModifier detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Grade Detection");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d10);
		detection.getAbility().setPercentChance(8/10);
		detection.setPermanent(true);
		detection.setDescription("Gnomish Grade Detection at 80%");
		raceAbilities.add(detection);
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect Unsafe Walls, Ceilings, Floors");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d10);
		detection.getAbility().setPercentChance(7/10);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Gnomish Detection of Unsafe Room features");
		raceAbilities.add(detection);
		detection = null;
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect Depth Underground");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d10);
		detection.getAbility().setPercentChance(6/10);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Gnomish Detection of Depth Underground");
		raceAbilities.add(detection);
		detection = null;
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect Direction Underground");
		detection.getAbility().setAbilityType(AbilityType.DIRECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance(2/4);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Gnomish Detection of Direction Underground");
		raceAbilities.add(detection);
		detection = null;
		
		//Combat -- Encounter specific
		AbilityModifier meleeModifier = new AbilityModifier();
		meleeModifier.getAbility().setAbilityName("+1 toHit : kobolds, goblins");
		meleeModifier.getAbility().setAbilityType(AbilityType.MELEE);
		meleeModifier.getAbility().setAbilityDie(Dice.d20);
		meleeModifier.getAbility().setPermanent(true);
		meleeModifier.getAbility().setPercentChance(1/20);
		meleeModifier.setPermanent(true);
		meleeModifier.setModifier(1);
		meleeModifier.setDescription("Gnomish Battle bonus");
		raceAbilities.add(meleeModifier);
		
		meleeModifier = new AbilityModifier();
		meleeModifier.getAbility().setAbilityName("-4 toHit for gnolls, bugbears, ogres, trolls, ogres, ogre magi, giants, titans");
		meleeModifier.getAbility().setAbilityType(AbilityType.MELEE);
		meleeModifier.getAbility().setAbilityDie(Dice.d20);
		meleeModifier.getAbility().setPermanent(true);
		meleeModifier.getAbility().setPercentChance(-4/20);
		meleeModifier.setPermanent(true);
		meleeModifier.setModifier(4);
		meleeModifier.setDescription("Gnomish Size Advantage");
		raceAbilities.add(meleeModifier);
		

	}

}
