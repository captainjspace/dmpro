package dmpro.data.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import java.util.stream.Collectors;

import dmpro.serializers.CharacterGsonService;

public class TSVLoader implements ResourceLoader {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	protected File file;
	protected List<TSVData> tsvTable = new ArrayList<TSVData>();
	private TSVData tsvData;
	protected Scanner scan;
	protected FileReader reader;
	protected String tablesDir = dataDirectory + "tables/";
	protected Gson gson = CharacterGsonService.getCharacterGson();
	protected String webTable = "not initialized"; //JSON data
	
	public TSVLoader(Class<? extends TSVData> clazz ,String fileName) {
		try {
			this.file = new File(tablesDir + fileName);
			load(clazz);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<? extends TSVData> load(Class<? extends TSVData> clazz) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {

		try {
			reader = new FileReader(file);
			scan   = new Scanner(reader);
			String [] fields;
			Field f = clazz.getDeclaredField("fieldCount"); 
			int i = f.getInt(f);
			scan.nextLine(); //skip header record
			while (scan.hasNextLine()) {
				fields = scan.nextLine().split("\t",i);
				tsvData =  clazz.asSubclass(clazz).getConstructor(String[].class).newInstance((Object)fields); 
				tsvTable.add(tsvData);
			}
			
			//tsvTable.stream().forEach(a -> System.out.println(a.toString()));
		
		} catch (FileNotFoundException fnf) {;
			logger.log(Level.INFO, "Where is that file " + file, fnf);
		} finally {
			scan.close();
		}
		webTable = gson.toJson(tsvTable.stream().map(p -> clazz.asSubclass(clazz).cast(p)).collect(Collectors.toList()));
		return tsvTable;
	}
	
    public String getWebTable() {
    	return webTable;
    }
}
