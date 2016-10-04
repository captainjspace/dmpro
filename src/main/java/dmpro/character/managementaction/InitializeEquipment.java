package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.items.Item;
import dmpro.items.Item.ItemType;
import dmpro.items.CoinItem;
import dmpro.items.CoinItem.CoinType;

import java.util.Formatter;
import java.util.Scanner;
import java.util.stream.Collectors;

import dmpro.character.Character;

public class InitializeEquipment implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		
		output.format("\n-----------Welcome to the Keep on the Borderlands %s------------\n", character.getName());
		output.format("First and foremost - you will need a weapon\nYout Currency:\n");
		
		
			character.getInventory().stream()
			.filter( p -> p.getItemType() == ItemType.COINS)
			.map(p -> (CoinItem)p)
			.collect(Collectors.groupingBy(CoinItem::getCoinType, Collectors.summingInt(CoinItem::getItemCount))).entrySet()
			.forEach(p -> output.format("\t%s : %d %s\n", p.getKey().getCoinName(), p.getValue().intValue(), p.getKey().getShortName()));
		output.flush();
			
		
		return character;
	}

}
