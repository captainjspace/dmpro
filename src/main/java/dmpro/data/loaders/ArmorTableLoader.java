package dmpro.data.loaders;

import java.util.List;
import java.util.stream.Collectors;

public class ArmorTableLoader extends TSVLoader {

	//List<ArmorRecord> armorItems;
	public ArmorTableLoader() {
		super(ArmorRecord.class, "base-armor.tsv");
	}
	
	public List<ArmorRecord> getArmorItems() {
		return tsvTable.stream()
				.map(p -> (ArmorRecord)p)
				.collect(Collectors.toList());
	}
	
	public List<ArmorRecord> searchArmorItem(String armorName) {
		return tsvTable.stream()
				.map(p -> (ArmorRecord)p)
				.filter(p -> p.getItemName().toLowerCase().contains(armorName.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public ArmorRecord getArmorItem(String armorName) {
		return tsvTable.stream()
				.map(p -> (ArmorRecord)p)
				.filter(p -> p.getItemName().toLowerCase().equals(armorName.toLowerCase()))
				.findFirst().get();
	}
	public static void main(String[] args) {
		ArmorTableLoader atl = new ArmorTableLoader();
		atl.tsvTable.stream()
		.forEach(a -> System.out.println(a.toString()));
	}

}
