package dmpro.core;

import dmpro.character.CharacterService;

public interface Server {
	public ReferenceDataSet getReferenceDataSet();
	public CharacterService getCharacterService();
}
