package dmpro.attributes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dmpro.attributes.Attribute.AttributeType;

public class AttributeLoader {
	
	private static final boolean DEBUG = false;
	static final String attributeDir="src/main/resources/data/attributes/";
	protected File file;
	private Attribute attribute;
	protected List<Attribute> attributeTable = new ArrayList<Attribute>();
    protected Scanner scan;
    protected FileReader reader;
    ;
    protected Gson gson= new GsonBuilder().setPrettyPrinting().create();
    
    public AttributeLoader() {}
    
    public AttributeLoader(Class<? extends Attribute> c,File f) {
    	try {
    		this.file = f;
			load(c);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public <T extends Attribute> List<Attribute> load(Class<? extends Attribute> c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		
		try {
			reader = new FileReader(file);
			scan   = new Scanner(reader);
			String [] fields;
			Field f = c.getDeclaredField("fieldCount");
			int i = f.getInt(f);
			
			while (scan.hasNextLine()) {
				fields = scan.nextLine().split("\t",i);		
				attribute =  c.asSubclass(c)
						.getConstructor(String[].class)
						.newInstance((Object)fields); 
				attributeTable.add(attribute);
			}
			if (DEBUG) {
				attributeTable.stream().forEach(a -> System.out.println(a.toString()));
				this.jsonDump(attributeTable);
			}
			
		} catch (FileNotFoundException fnf) {;
			System.err.println("Strength File: " + file + " Not Found!!!");;
		}
		return attributeTable;
	}
	

    public Attribute getRecord(int pickScore){
 		return attributeTable.get(pickScore -1); 
    }
    
	public void getRecord(String pickScoreStr){
    	int pickScore;
    	try {
    		pickScore = Integer.parseInt(pickScoreStr);
    	} catch (NumberFormatException e) {
    		e.printStackTrace();
    		System.out.println("Not an integer: " + pickScoreStr);
    		return;
    	}
    	
    	if (DEBUG) {
		attributeTable.stream()
		.filter( p -> p.abilityScore == pickScore)
		.forEach(a -> System.out.println(gson.toJson(a)));
    	}
    }

	public AttributeType getAttributeType(String attributeName) {
		AttributeType attributeType = null;
		for (AttributeType at : AttributeType.values()) {
			if (attributeName.matches(at.getRegex()))
				attributeType = at;
		}
		return attributeType;
	}

    public void jsonDump(List<? extends Attribute> attributeTable){
    	//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json=gson.toJson(attributeTable);
		System.out.println(json);
    }
    
}
