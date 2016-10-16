package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.items.CoinItem;
import dmpro.items.CoinItem.CoinType;

import java.util.Formatter;
import java.util.Scanner;

import dmpro.character.Character;
import dmpro.character.classes.CharacterClass;

/**
 * Initialize Character Actions
 * <ol>
 *   <li> Call Modifier Engine, XPProcessor (or maybe leave CharacteService</li>
 *   <li> Initialize Gold and Gear from class(es) [spellbook, thieves tools, holy symbol, etc.] </li>
 *   <li> Run Shop@Keep interaction </li>
 * </ol>
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */

public class InitializeCharacter implements ManagementAction {

	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		int goldCoins = 0;
		for (CharacterClass characterClass : character.getClasses().values()) {
			goldCoins += characterClass.getInitialGold();
		}
		character.addToInventory(new CoinItem(CoinType.GOLD, goldCoins));
		output.format("\nYou lucky adventurer! You've got %d to spend\n>", goldCoins);
		output.flush();
		return character;
	}

}
