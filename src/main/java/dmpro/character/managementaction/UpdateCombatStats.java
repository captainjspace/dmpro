/**
 * 
 */
package dmpro.character.managementaction;

import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import dmpro.character.Character;
import dmpro.character.CharacterModifierEngine;
import dmpro.character.CombatStatistics;
import dmpro.character.classes.CharacterClassType;
import dmpro.core.Server;
import dmpro.core.StubApp;
import dmpro.items.Item.ItemType;
import dmpro.items.WeaponItem;
import dmpro.items.WeaponItem.WeaponClass;
import dmpro.items.WeaponItem.WeaponType;
import dmpro.modifier.*;
import dmpro.modifier.ArmorClassModifier.ArmorClassModifierType;
import dmpro.modifier.MeleeModifier.MeleeModifierType;
import dmpro.modifier.MissileModifier.MissileModifierType;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;
import dmpro.modifier.Modifier.ModifierType;
import dmpro.modifier.WeaponSkillModifier.WeaponSkillModifierType;


/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 6, 2016
 * 
 * character -update combat statistics-<br>
 * <p>
 * evaluate all active modifiers that pertain specifically to the combat loop<br>
 * surprise - could be moved<br>
 * initiative, melee, missile (to hit) and damage and armor class are relevant every round of combat
 * <p>
 * TODO: this whole things needs to be cleaned up - decoupled from command line
 * additions include attacks per round - really only matter for fighters and multiclass that gain <br>
 * multiple attacks or can use a weapon that allows it.
 */
public class UpdateCombatStats implements ManagementAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	
	@Override
	public Character execute(Character character, Server application) {
		return this.execute(character,  application, null, null);
	}
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
		 * Modifier ALL - +/- Source ATTRIBUTE,ABILITY,MAGIC
		 * Modifier directional - SHIELD
		 */
		
		/* Collect ArmorClass Modifiers */
		List<ArmorClassModifier> acMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.ARMORCLASS)
				.map(p -> (ArmorClassModifier) p)
				.collect(Collectors.toList());
		
		//acMod.stream()
		//.filter(p -> p.getArmorClassModifierType() == ArmorClassModifierType.FRONT)
		//.filter(p -> p.modifierSource == ModifierSource.ARMOR)
		//.forEach(p -> System.out.println(p.toString()));
		
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
		combatStats.getArmorClassFactors().addAll(acMod);
		//output.format("ArmorClass: %d, %d, %d - %s\n", base, all, front ,combatStats.toString());
		character.setCombatStats(combatStats);
		
		
		/* Melee Modifiers */
		int specializedMeleeBonus = 0, specializedDamageBonus = 0;
		int proficiencyPenalty = 0;
		
		
		
		/* specialization check for fighters*/
		//character.getClasses().keySet().stream().forEach( p -> System.out.println(p));
		int nonProficiencyPenalty = 
				character.getClasses().entrySet().stream()
				.mapToInt(p -> p.getValue().getNonProficiencyPenalty()).min().orElse(proficiencyPenalty);
		//System.out.printf("NON-PROFICIENCY PENALTY = %d\n", nonProficiencyPenalty);
		/* check that equipped weapon is in proficiency list */

		List<WeaponItem> equippedWeapons = character.getEquippedItems().stream()
		.filter(w -> w.getItemType() == ItemType.WEAPON)
		.map(w -> (WeaponItem) w)
		.collect(Collectors.toList());
		
//		equippedWeapons.stream().forEach( w -> System.out.printf("%s - %s - %s\n", 
//				w.getItemName(), 
//				w.getWeaponType(), 
//				character.getProficiencies().stream().anyMatch(p -> p.equals(w.getWeaponType()))));
//		
//		
//				 {
//			System.out.println("Proficient");
//		} else {
//			System.out.println("Not - Proficient");
//		}
		
		if (character.getClasses().containsKey(CharacterClassType.FIGHTER)) {
			logger.log(Level.INFO, "I AM A FIGHTER");
			//TODO:  think about programming individual classes for different specialization types
			
			List<WeaponSkillModifier> specializationMod = 
					character.getActiveModifiers().stream()
					.filter(p -> p.modifierType == ModifierType.WEAPONSKILL)
					.map(p -> (WeaponSkillModifier) p )
					.filter(p -> p.getWeaponType().weaponClass() != WeaponClass.MISSILE)
					.collect(Collectors.toList());
			specializationMod.stream().forEach(p -> System.out.println(p.toString()));
			
			combatStats.getMeleeToHitFactors().addAll(
					specializationMod.stream().filter(p -> p.getWeaponSkillModifierType() == WeaponSkillModifierType.TOHIT).collect(Collectors.toList())
					);
			combatStats.getMeleeDamageFactors().addAll( 
					specializationMod.stream().filter(p -> p.getWeaponSkillModifierType() == WeaponSkillModifierType.DAMAGE).collect(Collectors.toList())
					);

			List<WeaponSkillModifier> specializedEquipped = equippedWeapons.stream()
					.flatMap(p -> specializationMod.stream()
							.filter(s -> p.getWeaponType() == s.getWeaponType()))
					.collect(Collectors.toList());
			
			//specializedEquipped.stream().forEach(p -> System.out.println("SpecializedMATCH: " + p.toString()));

			/* melee specialization */
			if ( specializedEquipped.size() >= 1) {
				specializedMeleeBonus = specializationMod.stream().filter(p -> p.getWeaponSkillModifierType() == WeaponSkillModifierType.TOHIT).mapToInt(p -> p.getModifier()).sum();
				specializedDamageBonus = specializationMod.stream().filter(p -> p.getWeaponSkillModifierType() == WeaponSkillModifierType.DAMAGE).mapToInt(p -> p.getModifier()).sum();
			}
			//output.format("Specialized to Hit: +%d\tSpecialized Damage: +%d\n", specializedMeleeBonus, specializedDamageBonus);
			//output.flush();
		} else {
			logger.log(Level.INFO,"I HAVE NO FIGHTER IN ME!");
		}
		
		/* To hit */
		List<MeleeModifier> meleeMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.MELEE)
				.map(p -> (MeleeModifier) p)
				.filter(p -> p.meleeModifierType == MeleeModifierType.TOHIT)
				.collect(Collectors.toList());
		int toHitMelee = meleeMod.stream().mapToInt(p -> p.getModifier()).sum();
		//meleeMod.stream().forEach(p -> output.format("MeleeMod:%s\n", p.toString()));
		//output.flush();
		combatStats.setMeleeToHitBonus(toHitMelee + specializedMeleeBonus);
		combatStats.getMeleeToHitFactors().addAll(meleeMod);
		
		/* Damage */
		List<MeleeModifier> meleeDamageMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.MELEE)
				.map(p -> (MeleeModifier) p)
				.filter(p -> p.meleeModifierType == MeleeModifierType.DAMAGE)
				.collect(Collectors.toList());
		int meleeDamageBonus = meleeMod.stream().mapToInt(p -> p.getModifier()).sum();
		//meleeDamageMod.stream().forEach(p -> output.format("Melee Damage:%s\n", p.toString()));
		//output.flush();
		
		
		combatStats.setMeleeDamageBonus(meleeDamageBonus + specializedDamageBonus);
		combatStats.getMeleeDamageFactors().addAll(meleeDamageMod);
		
		/*
		 * Missile Modifiers 
		 */
		
		/* to hit */
		List<MissileModifier> missileMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.MISSILE)
				.map(p -> (MissileModifier) p)
				.filter(p -> p.missileModifierType == MissileModifierType.TOHIT)
				.collect(Collectors.toList());
		int toHitMissile = missileMod.stream().mapToInt(p -> p.getModifier()).sum();
		//missileMod.stream().forEach(p -> output.format("MissileMod:%s\n", p.toString()));
		//output.flush();
		combatStats.setMissileToHitBonus(toHitMissile);
		combatStats.getMissileToHitFactors().addAll(missileMod);
		
		/* Damage */
		List<MissileModifier> missileDamageMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.MISSILE)
				.map(p -> (MissileModifier) p)
				.filter(p -> p.missileModifierType == MissileModifierType.DAMAGE)
				.collect(Collectors.toList());
		int missileDamageBonus = missileMod.stream().mapToInt(p -> p.getModifier()).sum();
		//missileDamageMod.stream().forEach(p -> output.format("Missile Damage:%s\n", p.toString()));
		//output.flush();
		combatStats.setMissileDamageBonus(missileDamageBonus + specializedDamageBonus);
		combatStats.getMissileDamageFactors().addAll(missileDamageMod);
		
		
		/*
		 * Initiative Modifiers 
		 */
		
		List<InitiativeModifier> initiativeMod = character.getActiveModifiers()
				.stream().filter(p -> p.getModifierType() == ModifierType.INITIATIVE)
				.map(p -> (InitiativeModifier) p)
				.collect(Collectors.toList());
		int initiativeBonus = initiativeMod.stream().mapToInt(p -> p.getModifier()).sum();
		//initiativeMod.stream().forEach(p -> output.format("InitiativeMod:%s\n", p.toString()));
		//output.flush();
		combatStats.setInitiativeModifier(initiativeBonus);
		combatStats.getInitiativeFactors().addAll(initiativeMod);
		
		//output.format("%s\n", combatStats.toString());
		
		/* TODO: Surprise should be moved to start of encounter - */
//		List<SurpriseModifier> surpriseMod = character.getActiveModifiers()
//				.stream().filter(p -> p.getModifierType() == ModifierType.SURPRISE)
//				.map(p -> (SurpriseModifier) p)
//				.collect(Collectors.toList());
		
		return character;
	}

	public static void main (String [] args) {
		Server application = new StubApp();
		String charID = "1476384327380019861205";
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
