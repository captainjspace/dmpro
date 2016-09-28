package dmpro.game;

import java.util.List;

import dmpro.AdventureParty;
import dmpro.core.GameTimer;

public class CombatLoop implements Runnable {
	
	private GameTimer gameTimer;
	private InitiativeQueue initiativeQueue;
	private AdventureParty adventureParty;
	private Monster monster;
	private List<CombatAction> combatActions;
	
	public void run() {
		//while encounter exists
		//while monster is not dead
		//execute initiative
		//execute combat actions in queue
		//roll dice, decrement hit points
		//check morale
		//end combat
	}

}
