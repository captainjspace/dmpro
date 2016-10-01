package dmpro.spells;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dmpro.data.loaders.ResourceLoader;

public class SpellLibrary implements ResourceLoader{
	
	public enum SpellResponseType {
		SPELL,
		TEXT,
		JSON;
	}
	
	List<Spell> spellLibrary = new ArrayList<Spell>();
	Gson gson= new GsonBuilder().setPrettyPrinting().create();
	File file;
	
	String spellDir = dataDirectory + "spells/";
	
	public SpellLibrary() {
		// TODO Auto-generated constructor stub
		file = new File(spellDir + "spells.json");
		loadSpells();
	}
	public static SpellLibrary getSpellLibrary() {
		SpellLibrary spellLibrary = new SpellLibrary();
		return spellLibrary;
	}
	
	public Spell getSpell(int id) {
		Spell spell = new Spell();
		return spell;
	}

	public List<Spell> getSpellsByClassAndLevel(String className, int level) {
		return spellLibrary.stream()
				.filter(s -> s.getSpellAbilityClassName().toLowerCase().equals(className.toLowerCase()))
				.filter(s -> s.getSpellAbilityClassLevel() == level)
				.collect(Collectors.toList());
	}
	
	public List<Spell> getSpellsByClass(String className) {
		return spellLibrary.stream()
				.filter(s -> s.getSpellAbilityClassName().toLowerCase().equals(className.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public List<Spell> searchSpell(String spellName) {
		return spellLibrary.stream()
				.filter(s -> s.getSpellName().toLowerCase().contains(spellName.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public Spell getSpell(String name) {
		Spell spell=null;
		try {
			spell = spellLibrary.stream().filter(a -> a.spellName.equalsIgnoreCase(name)).findFirst().get();
		}
		catch (NoSuchElementException e) {
			
		}
		return spell;
	}
	
	public void loadSpells() {
		try {
			FileReader r = new FileReader(file);
			Type spellLibraryType = new TypeToken<ArrayList<Spell>>(){}.getType();
			spellLibrary = gson.fromJson(r, spellLibraryType);
			
		} catch (IOException io) {System.err.println("File not Found: " + file);}		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpellLibrary s = new SpellLibrary();
		//s.spellLibrary.stream().forEach( a -> System.out.println(a.spellId + ":" + a.spellName));
		Spell sp =  s.getSpell("fireball");
		System.out.println(sp.toString());
	}

}
