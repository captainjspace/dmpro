package dmpro.game;

import java.util.List;

import dmpro.AdventureParty;
import dmpro.items.Item;

public interface Loot {
	List<Item> search();
	void addToParty(AdventureParty adevntureParty);
}
