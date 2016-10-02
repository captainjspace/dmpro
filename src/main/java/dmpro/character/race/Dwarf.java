package dmpro.character.race;

import dmpro.Ability.AbilityType;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.attributes.Charisma;
import dmpro.attributes.Constitution;
import dmpro.attributes.Intelligence;
import dmpro.character.Language;
import dmpro.character.SavingThrowType;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.SavingThrowModifier;
import dmpro.modifier.SpellEffectModifier;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.spells.SpellLibrary;
import dmpro.utils.Dice;

public class Dwarf extends Race {

	/** The Stout Dwarf!!!
	 * Fighter max 9, Thief, Assassin Max 9, Fighter/Thief - restrictions on gear when thieving
	 * Saving Throw Bonus: Rods Staves, Wands and Spells, Poison
	 * + 1-5  --- Math.floor(consitiution.getAbilityScore() / 3.5 ) + 1
	 * 
	 * Languages: dwarven, gnome, goblin, kobold, orcish, common, alignment
	 * Max of two additional regardless of intelligence
	 * 
	 * Infravision 60' X
	 * 
	 * Dwarven Mining Skills X
	 * DETECT at 10'
	 * Slope or Grade 75% ,d4 1-3
	 * New Construction of passage/tunnel 75%
	 * Sliding or Shifting Walls / rooms 66 2/3% d6, 1-4
	 * Detect Traps: Pits, falling blocks, stonework 50% d4, 1-2
	 * Determine approximate depth underground 50%
	 * 
	 * Melee:
	 * +1 to hit half-orc, orc, goblin, hobgoblin
	 * Defense:
	 * Monster has -4 to hit : ogres, trolls,ogre magi, giants, titans
	 * 
	 * Attributes:
	 * +1 Constitution, -1 Charisma X
	 * MAx Charisma 16???
	 */
	public Dwarf() {
		raceType = RaceType.DWARF;
		
		for (String i : new String[]{"dwarven", "gnome", "goblin", "kobold", "orcish", "common"})
			this.languages.add(new Language(i));
		
		AttributeModifier a = new AttributeModifier();
		a.modifierPriority = ModifierPriority.HIGH;
		a.setAttributeToModify(Constitution.class);
		a.setAttributeType(AttributeType.CONSTITUTION);
		a.setBonus(1);
		a.setDescription("	-->Dwarven +1 Constitution");
		raceAttributes.add(a);
		
		a = new AttributeModifier();
		a.modifierPriority = ModifierPriority.HIGH;
		a.setAttributeToModify(Charisma.class);
		a.setAttributeType(AttributeType.CHARISMA);
		a.setBonus(-1);
		a.setDescription("	-->Dwarven are gruff and mean -1 Charisma");
		raceAttributes.add(a);
		
		//how should i handle limits
		a = new AttributeModifier();
		a.setAttributeToModify(Charisma.class);
		a.setAttributeType(AttributeType.CHARISMA);
		a.setBonus(0);
		a.setDescription("	-->Dwarven max Charisma -16");
		raceAttributes.add(a);
		
		a= new AttributeModifier();
		a.setAttributeToModify(Intelligence.class);
		a.setAttributeType(AttributeType.INTELLIGENCE);
		a.setDescription("	-->Dwarven has max language bonus of 2 Int:11");
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
			savingThrowBonus.setDescription("Dwarven MagicResistance");
			raceAbilities.add(savingThrowBonus);
			savingThrowBonus = null;
		}
		
		
		SpellLibrary sl = SpellLibrary.getSpellLibrary();
		SpellEffectModifier permanentSpellEffect = new SpellEffectModifier();
		permanentSpellEffect.getAbility().setAbilityName("Infravision 60'");
		permanentSpellEffect.getAbility().setAbilityType(AbilityType.SPELL);
		permanentSpellEffect.setPermanent(true);
		permanentSpellEffect.setDescription("Dwarven Infravision");
		permanentSpellEffect.setSpellInEffect(sl.getSpell("infravision"));
		raceAbilities.add(permanentSpellEffect);
		permanentSpellEffect = null;
		
		AbilityModifier detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Grade Detection");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance(3/4);
		detection.setPermanent(true);
		detection.setDescription("Dwarven Grade Detection at 75%");
		raceAbilities.add(detection);
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect New Construction of passage or tunnel");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance(3/4);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Dwarven Detection of Construction");
		raceAbilities.add(detection);
		detection = null;
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect Traps: Pits, falling blocks, stonework");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance(3/4);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Dwarven Detection of Traps");
		raceAbilities.add(detection);
		detection = null;
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect Sliding or Shifting Walls and Rooms");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d6);
		detection.getAbility().setPercentChance(4/6);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Dwarven Detection of Shifitng Walls");
		raceAbilities.add(detection);
		detection = null;
		
		detection = new AbilityModifier();
		detection.getAbility().setAbilityName("Detect Depth Underground");
		detection.getAbility().setAbilityType(AbilityType.DETECTION);
		detection.getAbility().setAbilityDie(Dice.d4);
		detection.getAbility().setPercentChance(1/2);// why the cast java?
		detection.setPermanent(true);
		detection.setDescription("Dwarven Detection of Depth Underground");
		raceAbilities.add(detection);
		detection = null;
		
		//Combat -- Encounter specific
		AbilityModifier meleeModifier = new AbilityModifier();
		meleeModifier.getAbility().setAbilityName("+1 toHit : half-orc, orc, goblin, hobgoblin");
		meleeModifier.getAbility().setAbilityType(AbilityType.MELEE);
		meleeModifier.getAbility().setAbilityDie(Dice.d20);
		meleeModifier.getAbility().setPermanent(true);
		meleeModifier.getAbility().setPercentChance(1/20);
		meleeModifier.setPermanent(true);
		meleeModifier.setModifier(1);
		meleeModifier.setDescription("Dwarven Battle bonus");
		raceAbilities.add(meleeModifier);
		
		meleeModifier = new AbilityModifier();
		meleeModifier.getAbility().setAbilityName("-4 toHit for Giant Class:ogres, trolls, ogre magi, giants, titans");
		meleeModifier.getAbility().setAbilityType(AbilityType.MELEE);
		meleeModifier.getAbility().setAbilityDie(Dice.d20);
		meleeModifier.getAbility().setPermanent(true);
		meleeModifier.getAbility().setPercentChance(-4/20);
		meleeModifier.setPermanent(true);
		meleeModifier.setModifier(4);
		meleeModifier.setDescription("Dwarven Size Advantage");
		raceAbilities.add(meleeModifier);
		
		
	}

}
