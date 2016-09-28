package dmpro.modifier;

/**
 * Modifier.java interface
 * 
 * Basically any value in the game that can be modified should support this interface and have a List<Modifier>
 * that can be loaded
 * 
 * @author joshualandman
 *
 */
public interface Modifiable {

	/** I really wish I could do this in an interface
	 *  -- requiring implementers to have the object that the method requires 
	 * List of modifiers
	 */
	void addModifier(Modifier modifier);
	
}
