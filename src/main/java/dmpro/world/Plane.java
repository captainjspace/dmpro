/**
 * 
 */
package dmpro.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dmpro.IModify;
import dmpro.modifier.Modifier;

/**
 * @author joshualandman
 *
 */
public class Plane implements IModify {
	
	int planeId; //internal identifer
	PlaneName planeName; //perhaps unnecessary as their really is just one of each...
	String planeDescription;
	int planeNumber=1;  //i.e., 436th plane of the Abyss, default 1
	List<Modifier> modifiers = new ArrayList<Modifier>();
	
	public Plane(int planeId, PlaneName planeName) {
		this.planeId = planeId;
		this.planeName = planeName;
	}
	
	public Plane(int planeId, PlaneName planeName, int planeNumber) {
		this(planeId, planeName);
		this.planeNumber = planeNumber;
	}
	
	Map<Integer, Location> locations = new HashMap<Integer,Location>();
	/* (non-Javadoc)
	 * @see dmpro.IModify#addModifier(dmpro.Modifier)
	 */
	@Override
	public void addModifier(Modifier modifier) {
		this.modifiers.add(modifier);
	}

	/**
	 * @return the planeId
	 */
	public int getPlaneId() {
		return planeId;
	}

	/**
	 * @param planeId the planeId to set
	 */
	public void setPlaneId(int planeId) {
		this.planeId = planeId;
	}

	/**
	 * @return the planeName
	 */
	public PlaneName getPlaneName() {
		return planeName;
	}

	/**
	 * @param planeName the planeName to set
	 */
	public void setPlaneName(PlaneName planeName) {
		this.planeName = planeName;
	}

	/**
	 * @return the planeDescription
	 */
	public String getPlaneDescription() {
		return planeDescription;
	}

	/**
	 * @param planeDescription the planeDescription to set
	 */
	public void setPlaneDescription(String planeDescription) {
		this.planeDescription = planeDescription;
	}

	/**
	 * @return the planeNumber
	 */
	public int getPlaneNumber() {
		return planeNumber;
	}

	/**
	 * @param planeNumber the planeNumber to set
	 */
	public void setPlaneNumber(int planeNumber) {
		this.planeNumber = planeNumber;
	}

	/**
	 * @return the locations
	 */
	public Map<Integer, Location> getLocations() {
		return locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void setLocations(Map<Integer, Location> locations) {
		this.locations = locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void addLocation(Location location) {
		int nextId = this.locations.size()+1;
		this.locations.put(nextId, location);

	}
}
