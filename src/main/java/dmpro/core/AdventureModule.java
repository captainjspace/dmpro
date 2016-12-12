package dmpro.core;

import java.util.HashMap;
import java.util.Map;

import dmpro.world.Location;

public abstract class AdventureModule {
	/**
	 * This is like a whole lot of open space right now...
	 * can't even think about it until the basics are done
	 */
	long moduleId;
	String author;
	int levelLow;
	int levelHigh;
	int expectedNumberOfCharacters;
	
	String moduleName;
	
	String backStory;
	
	Location location;
	
	/* name, path - for media files audio, text, video 
	 *
	 **/
	
	Map<String, String> mediaMap = new HashMap<String, String>();  
	

}
