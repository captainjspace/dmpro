package dmpro.character.managementaction;

import dmpro.core.DungeonMasterProHandler;
import dmpro.core.Server;
import dmpro.core.StubApp;
import dmpro.data.loaders.ArmorRecord;
import dmpro.data.loaders.ArmorTableLoader;
import dmpro.data.loaders.WeaponItemLoader;
import dmpro.items.Item;
import dmpro.items.Item.ItemType;
import dmpro.items.WeaponItem;
import dmpro.items.WeaponItem.WeaponType;
import dmpro.items.CoinItem;
import dmpro.items.CoinItem.CoinType;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.stream.Collectors;

import dmpro.character.Character;

public class InitializeEquipment implements ManagementAction {
	private double afterValue;
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		List<Item> cart = new ArrayList<Item>();
		output.format("\n-----------Welcome to the Keep on the Borderlands %s------------\n", character.getName());
		output.format("First and foremost - you will need a weapon\nYout Currency:\n");


		Map<CoinType,Integer> coinMap = character.getInventory().stream()
		.filter( p -> p.getItemType() == ItemType.COINS)
		.map(p -> (CoinItem)p)
		.collect(Collectors.groupingBy(CoinItem::getCoinType, Collectors.summingInt(CoinItem::getItemCount)));
		
		List<CoinItem> coins = character.getInventory().stream()
				.filter( p -> p.getItemType() == ItemType.COINS)
				.map(p -> (CoinItem)p)
				.collect(Collectors.toList());
		
		output.format(" ---------- your coinnage ----------\n");
		coinMap.entrySet()
		.forEach(p -> output.format("\t%s : %d %s\n", p.getKey().getCoinName(), p.getValue().intValue(), p.getKey().getShortName()));
		output.flush();
		
		double totalValue = coins.stream().collect(Collectors.summingDouble(CoinItem::getItemValue));
		afterValue = totalValue;
		
		output.format("Total Value: %.2f\n", totalValue);
		
		
		/* shop weapons - tag Specialized, Proficient, non-proficient, not allowed */
		//.stream().collect(groupingBy( WeaponType.class, counting()));
		//Integer i = new Integer(1);
		Map<WeaponType, Long> proficiencies
		= character.getProficiencies().stream()
		.collect(Collectors.groupingBy(p -> (WeaponType) p,
				Collectors.counting()));
		proficiencies.entrySet().stream().forEach(p -> output.format("Proficiency: %s, %d\n",p.getKey(),p.getValue()));
		
		WeaponItemLoader weaponItems  = application.getReferenceDataSet().getWeaponItemLoader();

		output.format("Weapons you can afford \n");
		weaponItems.getWeapons().stream()
		.filter( p -> p.getItemValue() < totalValue)
		.forEach(p -> output.format("\t%s \t\t %d%s\n" , p.getItemName(), p.getItemValue() , p.getItemCurrency()));
		
		List<WeaponItem> weapons;
		while (true) {
			output.format("Enter a weapon name to purchase or .exit.>");
			output.flush();
			String weapon = input.nextLine();
			if (weapon.equals(DungeonMasterProHandler.EXIT)) break;
			weapons = weaponItems.searchWeaponItem(weapon); 
			if (weapons.size() > 1 ) {
				output.format("Please choose one:\n");
				weapons.stream().forEach(p -> output.format("\t%s \t\t %d\n" , p.getItemName(), p.getItemValue()));
			} else if (weapons.size() == 0) {
				output.format("No match for %s\n", weapon);
			} else {
				cart.add(weapons.get(0));
				output.format("%s added to cart\n%s\n", weapons.get(0).getItemName(),weapons.get(0).toString() );
				afterValue -= weapons.get(0).getItemValue();
			}
		}
		
		
		
		/* shop armor -- */
		
		ArmorTableLoader armorTableLoader  = application.getReferenceDataSet().getArmorTableLoader();
		output.format("Total Value: %.2f\n", afterValue);
		output.format("Armor you can afford \n");
		armorTableLoader.getArmorItems().stream()
		.filter( p -> p.getItemValue() < afterValue)
		.forEach(p -> output.format("\t%s \t\t %d%s\n" , p.getItemName(), p.getItemValue() , p.getItemCurrency()));
		
		List<ArmorRecord> armors;
		while (true) {
			output.format("Enter armor name to purchase or .exit.>");
			output.flush();
			String armor = input.nextLine();
			if (armor.equals(DungeonMasterProHandler.EXIT)) break;
			armors = armorTableLoader.searchArmorItem(armor); 
			if (armors.size() > 1 ) {
				output.format("Please choose one:\n");
				armors.stream().forEach(p -> output.format("\t%s \t\t %d\n" , p.getItemName(), p.getItemValue()));
			} else if (armors.size() == 0) {
				output.format("No match for %s\n", armor);
			} else {
				cart.add(armors.get(0));
				output.format("%s added to cart\n%s\n", armors.get(0).getItemName(), armors.get(0).toString());
				afterValue -= armors.get(0).getItemValue();
			}
		}
		/* shop supplies */
		output.format(" --- Thank you for shopping at the Keep ---\n --- Now Go Kill Stuff!!!\n");
		/* recalc gold */
		/* this process should actually remove all coins from inventory
		 * - a lock -
		 * then consolidate coins - BankService?
		 * - then subtract
		 * -- then read add - and unlock
		 */
		try {
			character.getInventory().addAll(cart);
			
			double totalCost = cart.stream().collect(Collectors.summingDouble(Item::getItemValue));
			output.format("Total Cost of Items: %.2f\nAdding to Inventory:\n", totalCost);
			cart.stream().forEach(p -> output.format("\t%s\n", p.getItemName()));
			
			/* got coins above */
			List<CoinItem> goldIndex = coins.stream()
					.filter( p -> p.getCoinType() == CoinType.GOLD)
					.collect(Collectors.toList());
			
			/* remove coins */
			character.setInventory( 
					character.getInventory().stream()
					.filter( p -> p.getItemType() != ItemType.COINS)
					.collect(Collectors.toList())
					);
			if ( goldIndex.size() == 1) {
				CoinItem gold = goldIndex.get(0);
				gold.setItemCount((int)Math.round(afterValue));
				character.addToInventory(gold);
			} else {
				//TODO: implement me 
			}
					
		} catch (Exception e) {
			//logger.log(Level.WARNING, "Cart processing failed");
		}
		/* add equipitems */
		character.addRequiredAction(CharacterManagementActions.EQUIPCHARACTER);
		/* reprocess modifiers */
		
		/* calc AC */
		
		/* calc combat */
		
		/* calc encumbrance */
		
		/* calc movement */
		
		return character;
	}

	public static void main (String [] args) {
		Server application = new StubApp();
		Character character = application.getCharacterService().loadCharacter("1475618677181019861205");
		InitializeEquipment initializeEquipment = new InitializeEquipment();
		Scanner input = new Scanner(System.in);
		Formatter output = new Formatter(System.out);
		initializeEquipment.execute(character, application, input, output);
	}
}
