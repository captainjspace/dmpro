package dmpro.world;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import dmpro.IModify;
import dmpro.modifier.Modifier;

public class Universe implements IModify {

	private int universeId;
	private String universeName;
	public Universe(int universeId, String universeName) {
		this.universeId = universeId;
		this.universeName = universeName;
		addPlanes();
	}
	
	//Planes
	public Map<Integer,Plane> planes = new HashMap<Integer,Plane>();
	
	private void addPlanes() {
		for (PlaneName plane : PlaneName.values()) 
			planes.put(new Integer(plane.ordinal()),new Plane(plane.ordinal(),plane));
	}
	
	@Override
	public void addModifier(Modifier modifier) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the universeId
	 */
	public int getUniverseId() {
		return universeId;
	}

	/**
	 * @param universeId the universeId to set
	 */
	public void setUniverseId(int universeId) {
		this.universeId = universeId;
	}

	/**
	 * @return the universeName
	 */
	public String getUniverseName() {
		return universeName;
	}

	/**
	 * @param universeName the universeName to set
	 */
	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}

	/**
	 * @return the planes
	 */
	public Map<Integer, Plane> getPlanes() {
		return planes;
	}

}
