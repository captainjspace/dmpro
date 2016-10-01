package dmpro.character.managementaction;

import dmpro.core.Server;
import dmpro.spells.Spell;
import dmpro.spells.SpellLibrary;

import java.util.Formatter;

import java.util.Scanner;



import dmpro.character.Character;

public class InitializeSpellBook implements ManagementAction {

	private SpellLibrary spellLibrary;
	@Override
	public Character execute(Character character, Server application, Scanner input, Formatter output) {
		/*TODO: this really is for Magic-User and Illusionist - not Cleric or Druid.
		  TODO: spellbook should be attached to the characterclass - not the character since it's possible to have 
		 		multiple spell caster classes and multiple spell types i think.
		  TODO: Also this should be a bit more of a dialog to VIEW spells and SELECT spells.
		 */
		spellLibrary = application.getReferenceDataSet().getSpellLibrary();
		String welcome = application.getReferenceDataSet().getAsciiArt().art.get("SpellLibraryWelcome");
		
		output.format(welcome, character.getPrefix(), character.getFirstName(), character.getLastName());

		spellLibrary.getSpellsByClassAndLevel("MAGIC-USER", 1)
					.stream()
					.forEach(p -> output.format("%s\n", p.getSpellName()));

		int initialSpellCount = 4;
		//TODO : stop this from reading the first line.
		while (initialSpellCount > 0) {
			output.format("You have %d spells remaining\nEnter Spell Name::", initialSpellCount);
			output.flush();
			String spellName = input.nextLine();
			Spell spell = spellLibrary.getSpell(spellName);
			if (spell != null) {
				character.getSpellBook().add(spell);
				initialSpellCount--;
			} else {
				output.format("Hmmm I could not find %s",  spell);
				continue;
			}
		}
		character.getSpellBook().add(spellLibrary.getSpell("Read Magic"));
		character.getSpellBook().stream().forEach(sp -> output.format("\t%s\n", sp.getSpellName()));
		return character;
	}
}
