package dmpro.core;

import dmpro.character.CharacterService;
/**
 * For testing 
 * @author joshualandman
 *
 */
public class StubApp implements Server {
	private ReferenceDataSet referenceDataSet = new ReferenceDataSet();
	//private CharacterService characterService = new CharacterService();

	public StubApp() {
		// TODO Auto-generated constructor stub
	}
	
	public ReferenceDataSet getReferenceDataSet() {
		return this.referenceDataSet;
	}

}
