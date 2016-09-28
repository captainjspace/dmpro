package dmpro.character.classes;

import java.util.ArrayList;
import java.util.List;

import dmpro.spells.Spell;

public interface SpellCaster {
	//TODO: Move spells to Spell Casters...
	List<Spell> spellBook = new ArrayList<Spell>();
	List<Spell> dailySpells = new ArrayList<Spell>();
    //	SpellTableRecord

}
