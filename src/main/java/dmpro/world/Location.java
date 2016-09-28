/**
 * 
 */
package dmpro.world;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dmpro.IModify;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.Modifier;

/**
 * @author joshualandman
 *
 */
public class Location implements IModify{
	
	//this is naturally recursive to a fault.
	//planet, continent, country, forest, dungeon, level, chamber - etc
	//any of these things can have some modifier that is broadcast to inhabitants
	
	int locationId = 1;
	String locationName;
	private Map<Integer, Location> locations= new HashMap<Integer,Location>();
	private List<Modifier> environmentModifier = new ArrayList<Modifier>();
	
	public Location() {};
	
	public Location(String locationName) {
		this.locationId = this.locations.size()+1;
		this.locationName = locationName;
		this.locations.putAll(locations);
		this.locations.put(this.locationId, this);
	}
;
	public Location(String locationName, Map<Integer, Location> locations) {
		this.locationName = locationName;
		this.locations.putAll(locations);
		this.locationId = this.locations.size()+1;
		this.locations.put(this.locationId, this);
	}

	public void exitLocation(int locationId) {
		this.locations = this.locations.entrySet()
		.stream()
		.filter(k -> k.getKey().intValue() < locationId)
		.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));	
	}
	
	public void enterLocation(String locationName) {
		locations.put(this.locations.size()+1, new Location(locationName, this.locations));
	}

	@Override
	public void addModifier(Modifier modifier) {
		// TODO Modifiers injected at game time or by configuration/load
		this.environmentModifier.add(modifier);
	}

	/**
	 * @return the locationId
	 */
	public int getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
	 * Static testing
	 * @param args
	 */
	public static void main(String[] args) {
		
		//test
		
		GeoFactory geo = GeoFactory.getGeoFactory();
		
		Location location = new Location("Greyhawk");
		location.enterLocation("Hellfurnaces");
		location.enterLocation("Hall of the Fire Giant King");
		location.enterLocation("Level 3");
		location.enterLocation("Room 16");
		
		geo.getUniverse().getPlanes().get(PlaneName.PRIMEMATERIAL.ordinal()).setLocations(location.locations);
		
		Type locationMapType = new TypeToken<Map<Integer,Location>>(){}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(locationMapType, new GsonLocationAdapter());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		System.out.println(
				gson.toJson(
						geo.getUniverse()
						.getPlanes()
						.get(PlaneName.PRIMEMATERIAL.ordinal())
						.getLocations()
						.get(3)
						)
				);
		
//		System.out.println(
//				gson.toJson(geo.getUniverse()
//				.getPlanes()
//				.get(PlaneName.PRIMEMATERIAL.ordinal())
//				.getLocations().get(1).getLocations().get(5)));
//		
//		
		
//		System.out.println(location.getLocations().size());
//		geo.getUniverse().getPlanes().get(PlaneName.PRIMEMATERIAL.ordinal()).getLocations()
//		.entrySet()
//		.stream()
//		.forEach(p -> System.out.format("Key %d\tName %s\n", p.getKey(), p.getValue().getLocationName()));
//		
//		GsonBuilder gsonBuilder = new GsonBuilder();
//		Gson gson = gsonBuilder.setPrettyPrinting().create();
//		System.out.println(gson.toJson(location));
//		
		location.getLocations()
		.entrySet()
		.stream()
		.forEach(p -> System.out.format("Key %d\tName %s\n", p.getKey(), p.getValue().getLocationName()));
		
//		location.exitLocation(4);
//		
//		System.out.println("post pop");
//		location.getLocations()
//		.entrySet()
//		.stream()
//		.forEach(p -> System.out.format("Key %d\tName %s\n", p.getKey(), p.getValue().getLocationName()));
//		
	}

	/**
	 * @return the environmentModifier
	 */
	public List<Modifier> getEnvironmentModifiers() {
		return environmentModifier;
	}

	/**
	 * @param environmentModifier the environmentModifier to set
	 */
	public void setEnvironmentModifiers(List<Modifier> environmentModifier) {
		this.environmentModifier = environmentModifier;
	}
}
