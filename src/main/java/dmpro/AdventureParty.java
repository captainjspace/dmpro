package dmpro;

import java.util.ArrayList;
import java.util.List;

import dmpro.character.Character;
import dmpro.modifier.Modifiable;
import dmpro.modifier.Modifier;
/**
 * Party contains Characters and is on an adventure module
 * Any items that are picked up are added to the party for later distribution
 * Party contents should be visible to players - for discussion and claiming.
 * Items in the party have not been processed for experience by default
 * items moved to a players equipment can be tagged.
 * DM clears the party's items when granting experience 
 * items in the party should be allocated to characters in totality at that time
 * experience is granted evenly generally even if items are not.
 */
public class AdventureParty implements Modifiable{

	List<Modifier> modifiers = new ArrayList<Modifier>();
    List<Character> party = new ArrayList<Character>();
    
    int partyId;
    String partyName;
    AdventureModule m = null;
    String location;

    public AdventureParty() {
    }
    
    public void createParty (String name) {
    	this.partyName = name;
    }
    
    public void addCharacterToParty (Character c) {
    	party.add(c);
    }
    
    public void removeCharacterFromParty (Character c) {
    	party.remove(c);
    }
    
    public void listParty() {
    	party.stream().forEach( p -> System.out.println(p.toString()));
    }
    
    public boolean loadParty(int id) {
    	return false;
    }

	@Override
	public void addModifier(Modifier modifier) {
		//prayer spells, protection from evil 10ft radius etc.
		this.modifiers.add(modifier);
	}
}
