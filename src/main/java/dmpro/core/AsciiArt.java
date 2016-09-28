package dmpro.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AsciiArt {

	public Map<String,String> art = new HashMap<String, String>();
	public AsciiArt() {
		load();
	}
	
	public void load() {
		try {
			String s = new String(Files.readAllBytes(Paths.get("./data/ascii-art/fighter")));
			art.put("fighter",s);
			art.put("help", new String(Files.readAllBytes(Paths.get("./data/ascii-art/help-file"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
