package dmpro.world;
/**
 * A world instance maker :)
 * @author joshualandman
 *
 * TODO: create a world instance maker...
 */

public class GeoFactory {
	
	private Universe universe;
	
	private GeoFactory() {
		this.universe = new Universe(1, "The First Universe");
	}
	
	public static GeoFactory getGeoFactory() {
		return new GeoFactory();
	}

	/**
	 * @return the universe
	 */
	public Universe getUniverse() {
		return universe;
	}

	/**
	 * @param universe the universe to set
	 */
	public void setUniverse(Universe universe) {
		this.universe = universe;
	}
	
	
}
