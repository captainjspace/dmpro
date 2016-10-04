/**
 * 
 */
package dmpro.world;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dmpro.IModify;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.Modifier;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;
import dmpro.modifier.Modifier.ModifierType;

/**
 * @author joshualandman
 *
 */
public class Location implements IModify{
	
	//this is naturally recursive to a fault.
	//planet, continent, country, forest, dungeon, level, chamber - etc
	//any of these things can have some modifier that is broadcast to inhabitants
	//locations are containers of other locations, and overlapping.
	
	private String uniqueId = UUID.randomUUID().toString(); //not sure how I will use this yet - might be for create only.
	private int locationId = 1; //relative Id for stack of locations
	private double latitude; //surface
	private double longitude;
	private double altitude; //underdark or higher
	private double area; 
	private String locationName;
	private String description;

	

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
	
	public Location enterLocation(String locationName) {
		Location ref = new Location(locationName, this.locations);
		locations.put(this.locations.size()+1, ref);
		return ref;
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

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @return the environmentModifier
	 */
	public List<Modifier> getEnvironmentModifier() {
		return environmentModifier;
	}
	
	
public static void main(String[] args) {
		
		//test
		
		GeoFactory geo = GeoFactory.getGeoFactory();
		
		/*
		 * basic containers requiring little detail for context
		 */
		Location location = new Location("Greyhawk");
		location.enterLocation("Hellfurnaces");
		location.enterLocation("WesternSlope");
		
		/*
		 * location requiring detail;
		 */
		Location hall; 
		hall = location.enterLocation("Hall of the Fire Giant King");
		hall.setArea(35000);
		hall.setDescription("A massive keep built into a smoking mountain with a river of lava running through it! Home of King Snurre!");
		location.locations.put( hall.locationId, hall);
		
		location.enterLocation("Level 3");
		
		/*
		 * location requiring Encounter details.
		 */
		Location encounter;
		encounter = location.enterLocation("Room 16");
		AttributeModifier em = new AttributeModifier();
		em.modifierPriority = ModifierPriority.HIGH;
		em.modifierSource = ModifierSource.LOCATION;
		em.modifierType = ModifierType.ATTRIBUTE;
		em.setDescription("Strength Drain Spell");
		em.setBonus(-4);
		//em.setAttributeToModify(Strength.class);
		em.setAttributeType(AttributeType.STRENGTH);
		encounter.addModifier(em);
		location.locations.put(encounter.locationId, encounter);
		
		
		/*
		 * place location into standard universe, specific plane
		 */
		geo.getUniverse().getPlanes().get(PlaneName.PRIMEMATERIAL.ordinal()).setLocations(location.locations);
		
		/* for serialization */
		Type locationMapType = new TypeToken<Map<Integer,Location>>(){}.getType();
		
		/*TODO: does not belong here */
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(locationMapType, new GsonLocationAdapter());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		
		/* details of a specific location in the middle */
//		System.out.println(
//				gson.toJson(
//						geo.getUniverse()
//						.getPlanes()
//						.get(PlaneName.PRIMEMATERIAL.ordinal())
//						.getLocations()
//						.get(3)
//						)
//				);
		
		/* details of specific location from a specific location */
//		System.out.println(
//				gson.toJson(geo.getUniverse()
//				.getPlanes()
//				.get(PlaneName.PRIMEMATERIAL.ordinal())
//				.getLocations().get(1).getLocations().get(5)));
		
		/* detail of the current location */
		String json = gson.toJson(location);
		System.out.println(json);
		
		Location x = gson.fromJson(json, Location.class);
		System.out.println(gson.toJson(x));
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
 * @return the area
 */
public double getArea() {
	return area;
}

/**
 * @param area the area to set
 */
public void setArea(double area) {
	this.area = area;
}

/**
 * @return the description
 */
public String getDescription() {
	return description;
}

/**
 * @param description the description to set
 */
public void setDescription(String description) {
	this.description = description;
}

/**
 * @param latitude the latitude to set
 */
public void setLatitude(double latitude) {
	this.latitude = latitude;
}

/**
 * @param longitude the longitude to set
 */
public void setLongitude(double longitude) {
	this.longitude = longitude;
}

/**
 * @param altitude the altitude to set
 */
public void setAltitude(double altitude) {
	this.altitude = altitude;
}

/**
 * @return the uniqueId
 */
public String getUniqueId() {
	return uniqueId;
}

/**
 * @param uniqueId the uniqueId to set
 */
public void setUniqueId(String uniqueId) {
	this.uniqueId = uniqueId;
}

/**
 * @param environmentModifier the environmentModifier to set
 */
public void setEnvironmentModifier(List<Modifier> environmentModifier) {
	this.environmentModifier = environmentModifier;
}


}
