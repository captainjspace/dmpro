package dmpro.character.classes;

import java.util.ArrayList;
import java.util.List;

import dmpro.data.loaders.SpellsAllowedRecord;
import dmpro.spells.Spell;

public interface SpellCaster {

	SpellsAllowedRecord spellsAllowed = new SpellsAllowedRecord();
	List<Spell> spellBook = new ArrayList<Spell>();
	List<Spell> dailySpells = new ArrayList<Spell>();
	
	//It's not clear to me that defaults are valuable in an interface
	//these are effective final.
	//however it does allow for mild multiple inheritance which is cool
	public default void addToSpellBook(Spell spell) {
		spellBook.add(spell);
	}
	public default boolean addToDailySpells(Spell spell) {
		if (spellBook.stream()
				.anyMatch(p -> p.getSpellName().equals(spell.getSpellName()))) {
				spellBook.add(spell);
				return true;
		}
		else return false;
	}
    public default SpellsAllowedRecord getSpellsAllowed() {
    	return spellsAllowed;
    }
    //not allowed boo.
//    public default void setSpellsAllowedRecord(SpellsAllowedRecord spellsAllowedRecord) {
//    	spellsAllowed = spellsAllowedRecord;
//    }
}
