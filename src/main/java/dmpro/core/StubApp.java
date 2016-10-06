package dmpro.core;

import dmpro.character.CharacterService;
/**
 * For testing 
 * @author joshualandman
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
