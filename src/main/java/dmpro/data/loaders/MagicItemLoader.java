package dmpro.data.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dmpro.items.GSonItemAdapter;
import dmpro.items.GSonModifierAdapter;
import dmpro.items.Item;
import dmpro.items.MagicItem;
import dmpro.modifier.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MagicItemLoader implements ResourceLoader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static final String magicDir = dataDirectory + "magic-items";
	
	private List<MagicItem> magicItems = new ArrayList<MagicItem>();

	public MagicItem getMagicItem(String magicItemName) {
		MagicItem magicItem = null;
		try {
			magicItem 
			= magicItems.stream().filter(p -> p.getItemName().equalsIgnoreCase(magicItemName)).findFirst().get();
			
		} catch (NoSuchElementException e) {
			logger.log(Level.WARNING, "No item found for " + magicItemName, e);
		}
		return magicItem;
	}
	public MagicItemLoader() {
		load();
	};

	public void load() {
		List<Path> magicItemFiles = null;
		
		try {
			magicItemFiles = listSourceFiles(FileSystems.getDefault().getPath(magicDir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (magicItemFiles == null) {
			logger.log(Level.WARNING, "No Magic itemsfounds in " + magicDir);
			//throw error???
			return;
		}
		
		MagicItem magicItem = null;
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(Item.class, new GSonItemAdapter())
				.registerTypeAdapter(Modifier.class, new GSonModifierAdapter())
				.create();
		BufferedReader reader;
		for (Path path : magicItemFiles) {
			magicItem = null;
			try {
				
				reader = Files.newBufferedReader(path);
				magicItem = gson.fromJson(reader, MagicItem.class);
				magicItems.add(magicItem);
			} catch (IOException io) {
				logger.log(Level.WARNING, "File not Found: " + path,io);
			} catch (Exception e) {
				logger.log(Level.WARNING, "Gson error?",e);
			}
			System.out.println(magicItem.toString());
			System.out.println(gson.toJson(magicItem));
		}
	}

	private List<Path> listSourceFiles(Path dir) throws IOException {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
			for (Path entry: stream) {
				result.add(entry);
			}
		} catch (DirectoryIteratorException ex) {
			// I/O error encounted during the iteration, the cause is an IOException
			throw ex.getCause();
		}
		return result;
	}

	
	public static void main (String[] args) {
		System.out.println("MagicItemLoader");
		MagicItemLoader magicItemLoader = new MagicItemLoader();
		magicItemLoader.load();
		//System.out.println(magicItemLoader.getMagicItem("ring of dexterity").toString());
	}

}