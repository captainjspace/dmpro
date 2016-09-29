package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.items.WeaponItem.WeaponType;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;
import dmpro.character.classes.CharacterClass;

public class InitializeProficiencies implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		int initialProficiencySlots = 0;
		for (CharacterClass characterClass : character.getClasses().values()) {
			initialProficiencySlots += characterClass.getStartingProficiencies();
		}
		output.format("\n Good Sirs and Ladies - you have arrived at weapons training camp\n" +
		"Please select some weapons type to train in!\nYou have %d proficiency slots\n", initialProficiencySlots);
		for ( WeaponType weaponType : WeaponType.values() ) {
			output.format( "\t%d : %s\n", 
					weaponType.weaponTypeIndex(), 
					weaponType.weaponTypeDescription());
		}
		
		//TODO: Allow fighers to specialize (Two slots)
		//		Deny specialization to other classes.
		
		while (initialProficiencySlots > 0) {
			output.format("\nYou have %d slots remaining.  Select a number:>", initialProficiencySlots);
			output.flush();
			try {
				int index = input.nextInt();
				WeaponType proficiency = null;
				for (WeaponType weaponType : WeaponType.values()) {
					if (weaponType.weaponTypeIndex() == index) {
						proficiency = weaponType;
						break;
					} else continue;
				}
				if (proficiency != null) {
					character.addProficiency(proficiency);
					initialProficiencySlots--;
				} else {
					throw new Exception("Looks like tat Number is out of range!");
				}
			} catch (Exception e) {
				output.format("That did not work!!! " + e.getMessage());
			}
		}
		output.format("Congratulations - you have completed basic training!\n");
		character.getProficiencies().stream().forEach(w -> output.format("\t%s\n", w.toString()));
		output.flush();
		return character;
	}

}
