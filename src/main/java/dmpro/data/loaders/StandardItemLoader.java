package dmpro.data.loaders;

import java.util.List;
import java.util.stream.Collectors;

public class StandardItemLoader extends TSVLoader {
	
	public StandardItemLoader () {
		super(StandardItemRecord.class,"standard-items.tsv");
	}
	
	public List<StandardItemRecord> getStandardItems() {
		return tsvTable.stream().map( p -> (StandardItemRecord) p).collect(Collectors.toList());
	}

	public static void main(String[] args) {
		StandardItemLoader sil = new StandardItemLoader();
		sil.tsvTable.stream().forEach(item -> System.out.println(item.toString()));
	}

}
