package dmpro.game;

import java.util.List;

import dmpro.character.AdventureParty;

public class EncounterEngine {

	private Surprise surprise;
	private CombatLoop combatLoop;
	
	//package
	AdventureParty adventureParty;
	List<Monster> monster;
	Traps traps;
	Loot loot;
	
	public EncounterEngine() {
		// TODO generate planned or random encounter
	}

}
