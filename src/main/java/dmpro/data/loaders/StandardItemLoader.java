package dmpro.data.loaders;

public class StandardItemLoader extends TSVLoader {
	
	public StandardItemLoader () {
		super(StandardItemRecord.class,"standard-items.tsv");
	}

	public static void main(String[] args) {
		StandardItemLoader sil = new StandardItemLoader();
		sil.tsvTable.stream().forEach(item -> System.out.println(item.toString()));
	}

}
