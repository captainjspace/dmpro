package dmpro.spells;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestSpellLibraryFromJson {

	public static void main (String[] args) {
		String file = "data/spells/spells.json";
		SpellLibrary sl = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			FileReader r = new FileReader(file);
			//need to cast to List<of type>
			sl = gson.fromJson(r, SpellLibrary.class);
		} catch (IOException io) {
			System.err.println("File not Found: " + file);
		}
		
	}

}