package dmpro.character.managementaction;

import dmpro.core.DungeonMasterProHandler;
import dmpro.core.Server;
import dmpro.core.StubApp;
import dmpro.data.loaders.ArmorRecord;
import dmpro.data.loaders.ArmorTableLoader;
import dmpro.data.loaders.ArmorType;
import dmpro.items.Item.ItemType;
import dmpro.items.WeaponItem;
import dmpro.items.WeaponItem.Size;

import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import dmpro.character.Character;
import dmpro.character.SlottedItems.EquipmentSlotKey;

public class EquipCharacter implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		output.format("--- It's time to Equip your character ---\nSelect a primary weapon:\n");

		/*
		 * Slots:
		 * Weapon -ONEHANDED + Shield, TWOHANDED*, 
		 * Disadvantaged Weapons:  (i.e, Left Handed, Dagger for a righty)
		 * Armor, Headgear, Gauntlets, Boots, Cloak
		 * Magic:  Head, Necklace/Brooch, Bracers, Robes, 2 Rings, 
		 * Carried Enchantment (auto-equip feature?)
		 */

		/* got two hands...*/
		boolean handsFull = false;
		
		/* get all weapons */
		List<WeaponItem> weapons = 
				character.getInventory().stream()
				.filter(p -> p.getItemType() == ItemType.WEAPON)
				.map(p -> (WeaponItem)p)
				.collect(Collectors.toList());

		/* display weapons */
		weapons.stream().forEach(p -> output.format("Weapon: %s - %s\tDamage vs S/M: %s, vs L:%s\n", 
				p.getWeaponType().name(),
				p.getItemName(), 
				p.getDamageMap().get(Size.M).toString(),
				p.getDamageMap().get(Size.L).toString()));
		output.flush();

		/* select weapon */
		while (true) {
			output.format("Choose your primary weapon or .exit.>");
			output.flush();
			String equip = input.nextLine();
			if (equip.equals(DungeonMasterProHandler.EXIT)) break;
			List<WeaponItem> wpns = weapons.stream().filter(p -> p.getItemName().toLowerCase().contains(equip.toLowerCase()))
					.collect(Collectors.toList());


			if (wpns.size() > 1 ) {
				output.format("Please choose one:\n");
				wpns.stream().forEach(p -> output.format("\t%s \t\t %d\n" , p.getItemName(), p.getItemValue()));
			} else if (wpns.size() == 0) {
				output.format("No match for %s\n", equip);
			} else {
				
				character.getEquippedItems().add(wpns.get(0));
				character.getSlottedItems().slots.put(EquipmentSlotKey.MAINHAND, wpns.get(0));
				output.format("Equipped: %s \n%s\n", 
						wpns.get(0).getItemName(), 
						wpns.get(0).toString());

			}
			
			/* check hands required */
			if (character.getEquippedItems().stream()
			.filter( p -> p.getItemType() == ItemType.WEAPON)
			.map(p -> (WeaponItem)p)
			.mapToInt(p -> p.getWeaponType().handsRequired()).sum() == 2) {
				handsFull = true;
				output.format("Your hands are full!\n");
				output.flush();
				break;
				
			}
			
		}

		/* get all armor */
		List<ArmorRecord> armorItems = 
				character.getInventory().stream()
				.filter(p -> p.getItemType() == ItemType.ARMOR)
				.map(p -> (ArmorRecord)p)
				.collect(Collectors.toList());

		/* display armor */
		armorItems.stream()
		.forEach(p -> output.format("Armor: %s - %s\tAC: %s, Base Move: %d\tAC Mod: %s for %d Frontal Attacks", 
				p.getArmorType().name(),
				p.getItemName(), 
				p.getArmorClass(),
				p.getArmorBaseMovement(),
				p.getAcModifier(),
				p.getNumAttacksDefended()));
		output.flush();

		while (true) {
			output.format("Choose your armor .exit.>");
			output.flush();
			String equip = input.nextLine();
			if (equip.equals(DungeonMasterProHandler.EXIT)) break;
			List<ArmorRecord> armor = armorItems.stream().filter(p -> p.getItemName().toLowerCase().contains(equip.toLowerCase()))
					.collect(Collectors.toList());

			if (armor.size() > 1 ) {
				output.format("Please choose one:\n");
				armor.stream().forEach(p -> output.format("\t%s \t\t %d\n" , p.getItemName(), p.getItemValue()));
			} else if (armor.size() == 0) {
				output.format("No match for %s\n", equip);
			} else {
				character.getEquippedItems().add(armor.get(0));
				output.format("Equipped: %s \n%s\n", 
						armor.get(0).getItemName(), 
						armor.get(0).toString());

			}
			
			/* get shields if any */
			List<ArmorRecord> shields = armorItems.stream()
					.filter(p -> p.getArmorType() == ArmorType.SHIELD)
					.collect(Collectors.toList());
			if ( (!handsFull) && ( shields.stream().count() > 0)) {
				shields.stream().forEach(p -> output.format("\t%s, %s", p.getItemName(), p.getAcModifier(), p.getNumAttacksDefended()));
			} else { 
				if (handsFull) {
					output.format("No hands free for a shield\n");
				} else {
					output.format("Using a two handed weapon prevents you from using a shield\n");
				}
				output.flush();
				break;
			}
		}
			
		character.addRequiredAction(CharacterManagementActions.ACCUMULATEMODIFIERS);
		character.addRequiredAction(CharacterManagementActions.UPDATECOMBATSTATS);
		return character;
	}

	public static void main (String [] args) {
		Server application = new StubApp();
		Character character = application.getCharacterService().loadCharacter("1475762108845019861205");
		EquipCharacter equip = new EquipCharacter();
		Scanner input = new Scanner(System.in);
		Formatter output = new Formatter(System.out);
		equip.execute(character, application, input, output);
	}
}
