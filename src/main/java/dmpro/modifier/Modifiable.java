package dmpro.modifier;

/**
 * Modifier.java interface
 * 
 * Basically any value in the game that can be modified should support this interface <br>
 * and have a List &lt;Modifier&gt; that can be loaded
 * 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */
public interface Modifiable {

	/** I really wish I could do this in an interface
	 *  -- requiring implementers to have the object that the method requires 
	 * List of modifiers
	 */
	
	/**
	 * @param modifier modifier to add
	 */
	void addModifier(Modifier modifier);
	
}
