package dmpro.data.loaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import dmpro.items.Item;
import dmpro.serializers.CharacterGsonService;

public class StandardItemLoader extends TSVLoader {
	
	public StandardItemLoader () {
		super(StandardItemRecord.class,"standard-items.tsv");
	}
	
	public List<StandardItemRecord> getStandardItems() {
		return tsvTable.stream().map( p -> (StandardItemRecord) p).collect(Collectors.toList());
	}
	
	public List<Item> getAsStandardItems() {
		return tsvTable.stream().map( p -> (Item) p).collect(Collectors.toList());
	}
	public static void main(String[] args) {
		//TODO:  Understand why Items subtypes Gson works while in character, vs not.
		StandardItemLoader sil = new StandardItemLoader();
		dmpro.character.Character c= new dmpro.character.Character();
		sil.getStandardItems().stream().forEach( p -> c.addToInventory(p));	
		System.out.println(CharacterGsonService.getCharacterGson().toJson(sil.getStandardItems()));
	}

}
