package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.items.Cart;
import dmpro.items.CoinItem;
import dmpro.items.CoinItem.CoinType;

import java.util.Formatter;
import java.util.Map;
import java.util.Scanner;

import dmpro.character.Character;

public class SpendGold implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		// TODO Auto-generated method stub
		return character;
	}

	public Character execute(Character character, Cart cart) {
		Map<CoinType,CoinItem> coinMap = character.getCoinnage();
		return character;
	}
}
