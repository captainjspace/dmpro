package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.items.CoinItem;
import dmpro.items.CoinItem.CoinType;
import dmpro.character.Character;
import dmpro.character.classes.CharacterClass;

/**
 * Initialize Character Actions
 * <ol>
 *   <li> Call Modifier Engine, XPProcessor (or maybe leave CharacteService</li>
 *   <li> Initialize Gold and Gear from class(es) [spellbook, thieves tools, holy symbol, etc.] </li>
 *   <li> Run Shop@Keep interaction </li>
 * </ol>
 * @author joshualandman
 *
 */

public class InitializeCharacter implements ManagementAction {

	@Override
	public Character execute(Character character, Server application) {
		int goldCoins = 0;
		for (CharacterClass characterClass : character.getClasses().values()) {
			goldCoins += characterClass.getInitialGold();
		}
		character.addToInventory(new CoinItem(CoinType.GOLD, goldCoins));
		return character;
	}

}
