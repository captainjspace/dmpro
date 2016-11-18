package dmpro.character.managementaction;

import dmpro.core.ReferenceDataSet;
import dmpro.core.Server;
import dmpro.items.CoinItem;
import dmpro.items.CoinItem.CoinType;

import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;


import dmpro.character.Character;
import dmpro.character.CharacterService;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.CharacterClassType;


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
	/**
	 * initial implementation for socket based client
	 */
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
	
	/**
	 * Initialize Character Level, Modifiers and Starting Coins for Rest API
	 * TODO: double save unnecessary
	 * @param character
	 * @return
	 */
	public Character execute(Character character, Server application) {
		
		CharacterService characterService = application.getCharacterService();
		ReferenceDataSet referenceDataSet = application.getReferenceDataSet();
		CharacterClassType cct;
		try {
			cct = character.getClasses().keySet().stream().findFirst().get();
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Character Class Not Assigned");
		}
		
		character.setAge(referenceDataSet.getRaceClassAgeLoader().getAge(character.getRace().getRaceType(),
																 cct));
		character.setHeight(referenceDataSet.getRaceSizeLoader().getHeight(character.getRace().getRaceType()));
		character.setWeight(referenceDataSet.getRaceSizeLoader().getWeight(character.getRace().getRaceType()));
		
		character = characterService.initCharacter(character);
		//additional management actions 
		character = CharacterManagementActions.UPDATESAVINGTHROWS.getManagementAction().execute(character, application);
		character = CharacterManagementActions.UPDATECOMBATSTATS.getManagementAction().execute(character, application, null, null);
		
		int goldCoins = 0;
		for (CharacterClass characterClass : character.getClasses().values()) {
			goldCoins += characterClass.getInitialGold();
		}
		character.addToInventory(new CoinItem(CoinType.GOLD, goldCoins));
		character.addToInventory(new CoinItem(CoinType.SILVER, goldCoins));
		character.addToInventory(new CoinItem(CoinType.COPPER, goldCoins));
		
		character = characterService.saveCharacter(character);
		return character;
	}

}
