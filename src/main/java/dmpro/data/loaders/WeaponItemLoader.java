package dmpro.data.loaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dmpro.items.ItemGsonExclusion;
import dmpro.items.WeaponItem;

public class WeaponItemLoader implements ResourceLoader {
	
	List<WeaponItem> weapons = new ArrayList<WeaponItem>();
	
	private final String weaponFile = dataDirectory + "tables/weapons-table.tsv";
	private final int fieldCount = 14;
	
	public WeaponItemLoader () {
		load();
	}
	
	public void load() {
		FileReader reader=null;
		Scanner scanner;
		
		try {
			reader = new FileReader(weaponFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.printf("WeaponItemLoader: File Not found %s\n", weaponFile);
		}
		scanner = new Scanner(reader);
		scanner.nextLine();
		
		while (scanner.hasNextLine())
			weapons.add( new WeaponItem(scanner.nextLine().split("\t",fieldCount)));
		
		scanner.close();
	}

	public List<WeaponItem> searchWeaponItem(String weaponName) {
		List<WeaponItem> weaponSearch = new ArrayList<WeaponItem>();
		weaponSearch = weapons.stream()
				.filter(p -> p.getItemName().toLowerCase().trim().contains(weaponName.toLowerCase()))
				.collect(Collectors.toList());
		return weaponSearch;
	}
	
	public WeaponItem getWeaponItem(String weaponName) {
		System.out.println(weaponName);
		WeaponItem weapon = null;
		try {
		  weapon = weapons.stream().filter(p -> p.getItemName().toLowerCase().trim().equals(weaponName.toLowerCase()))
				  .findFirst().get();
		} catch (NoSuchElementException e) {
			System.out.printf("Could not find a listing for %s\n", weaponName);
			weapons.stream().filter(p -> p.getItemName().toLowerCase().contains(weaponName.toLowerCase()))
			.forEach(w -> System.out.println(w.getItemName()));
		}
		return weapon;
	}
	
	//test hacks
	public static void main (String[] args) {
		WeaponItemLoader weaponItemLoader = new WeaponItemLoader();
		System.out.println("------------\nList test\n------------");
		weaponItemLoader.getWeapons().stream().forEach(p -> System.out.println(p.getItemName()));
		System.out.println("------------\nSearch test\n---------");
		weaponItemLoader.searchWeaponItem("sword").stream().forEach(p -> System.out.println(p.getItemName()));
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.setExclusionStrategies(new ItemGsonExclusion())
				.create();
		//String weaponsJson = gson.toJson(wil.weapons);
		//System.out.println(weaponsJson);
		System.out.println("-----------------------Welcome to the Armory!  The is the Weapons Utility -----------------");
		weaponItemLoader.getWeapons().stream().forEach(p -> System.out.printf("%s\n", p.getItemName()));
		
		Scanner scan = new Scanner(System.in);
		WeaponItem weapon;
		while (true) {
			System.out.println("\nEnter a weapon Name or .exit.:" );
			String s = scan.nextLine();
			if (s.equals(".exit.")) break;
			weapon = weaponItemLoader.getWeaponItem(s);
			if ( weapon != null ) System.out.println(gson.toJson(weapon));
		}
		scan.close();
		
	}

	/**
	 * @return the weapons
	 */
	public List<WeaponItem> getWeapons() {
		return weapons;
	}

	/**
	 * @param weapons the weapons to set
	 */
	public void setWeapons(List<WeaponItem> weapons) {
		this.weapons = weapons;
	}

	/**
	 * @return the weaponFile
	 */
	public String getWeaponFile() {
		return weaponFile;
	}

	/**
	 * @return the fieldCount
	 */
	public int getFieldCount() {
		return fieldCount;
	}
}
