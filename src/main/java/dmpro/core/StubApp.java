package dmpro.core;

import dmpro.character.CharacterService;
/**
 * For testing 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */
public class StubApp implements Server {
	private ReferenceDataSet referenceDataSet = new ReferenceDataSet();
	private CharacterService characterService = new CharacterService();

	public StubApp() {
		referenceDataSet.run();
	}
	
	public ReferenceDataSet getReferenceDataSet() {
		return this.referenceDataSet;
	}

	/* (non-Javadoc)
	 * @see dmpro.core.Server#getCharacterService()
	 */
	@Override
	public CharacterService getCharacterService() {
		return this.characterService;
	}


}
