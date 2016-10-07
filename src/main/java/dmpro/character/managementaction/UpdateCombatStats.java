/**
 * 
 */
package dmpro.character.managementaction;

import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import dmpro.character.Character;
import dmpro.character.CharacterModifierEngine;
import dmpro.character.CombatStatistics;
import dmpro.core.Server;
import dmpro.core.StubApp;
import dmpro.modifier.*;
import dmpro.modifier.ArmorClassModifier.ArmorClassModifierType;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;
import dmpro.modifier.Modifier.ModifierType;

/**
 * @author Joshua Landman, <joshua.s.landman@gmail.com>
 * created on Oct 6, 2016
 */
public class UpdateCombatStats implements ManagementAction {

	/* (non-Javadoc)
	 * @see dmpro.character.managementaction.ManagementAction#execute(dmpro.character.Character, dmpro.core.Server, java.util.Scanner, java.util.Formatter)
	 */
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		// TODO : 
		CombatStatistics combatStats = new CombatStatistics();
		
		/* 
		 * Big Distinctions:
		 * Fixed AC -  Source ARMOR
		 * Modifier ALL - +/- Souce ATTRIBUTE,ABILITY,MAGIC
		 * Modifier directional - SHIELD
		 */
		
		/* Collect ArmorClass Modifiers */
		List<ArmorClassModifier> acMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.ARMORCLASS)
				.map(p -> (ArmorClassModifier) p)
				.collect(Collectors.toList());
		
		acMod.stream()
		//.filter(p -> p.getArmorClassModifierType() == ArmorClassModifierType.FRONT)
		//.filter(p -> p.modifierSource == ModifierSource.ARMOR)
		.forEach(p -> System.out.println(p.toString()));
		
		/* Find Armor */
		Optional<ArmorClassModifier> ac = acMod.stream()
				.filter(p -> p.getArmorClassModifierType() == ArmorClassModifierType.ALL)
				.filter(p -> p.modifierPriority == ModifierPriority.HIGH)
				.filter(p -> p.modifierSource == ModifierSource.ARMOR)
				.findFirst();
		int base = ( ac.isPresent() ) ? ac.get().getModifier() : 10;
				
		
		/* Get all non-Armor modifiers (i.e, protection items, dexterity) */
		int all = acMod.stream()
				.filter(p -> p.getArmorClassModifierType() == ArmorClassModifierType.ALL)
				.filter(p -> p.modifierSource != ModifierSource.ARMOR)
				.mapToInt( p -> p.getModifier()).sum();
		
		/* get all shield modifiers */
		int front = acMod.stream()
				.filter(p -> p.getArmorClassModifierType() == ArmorClassModifierType.FRONT)
				.filter(p -> p.modifierSource == ModifierSource.ARMOR)
				.mapToInt( p -> p.getModifier()).sum();
		
		combatStats.setArmorClass(base + all + front);
		combatStats.setArmorClassFactors(acMod);
		output.format("ArmorClass: %d, %d, %d - %s", base, all, front ,combatStats.toString());
		character.setCombatStats(combatStats);
		
		
		
		List<MeleeModifier> meleeMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.MELEE)
				.map(p -> (MeleeModifier) p)
				.collect(Collectors.toList());
		
		List<MissileModifier> missileMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.MISSILE)
				.map(p -> (MissileModifier) p)
				.collect(Collectors.toList());
		
		List<InitiativeModifier> initiativeMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.INITIATIVE)
				.map(p -> (InitiativeModifier) p)
				.collect(Collectors.toList());

		List<SurpriseModifier> surpriseMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.SURPRISE)
				.map(p -> (SurpriseModifier) p)
				.collect(Collectors.toList());
		return character;
	}

	public static void main (String [] args) {
		Server application = new StubApp();
		String charID = "1475876181862019861205";
		Character character = application.getCharacterService().loadCharacter(charID);
		character = (new CharacterModifierEngine(application)).processModifiers(character);
		character = application.getCharacterService().saveCharacter(character);
		UpdateCombatStats ucs = new UpdateCombatStats();
		Scanner input = new Scanner(System.in);
		Formatter output = new Formatter(System.out);
		ucs.execute(character, application, input, output);
		character = application.getCharacterService().saveCharacter(character);
	}
}
