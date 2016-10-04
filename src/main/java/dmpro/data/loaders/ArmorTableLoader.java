package dmpro.data.loaders;

public class ArmorTableLoader extends TSVLoader {

	public ArmorTableLoader() {
		super(ArmorRecord.class, "base-armor.tsv");
	}
	
	public static void main(String[] args) {
		ArmorTableLoader atl = new ArmorTableLoader();
		atl.tsvTable.stream()
		.forEach(a -> System.out.println(a.toString()));
		

	}

}
