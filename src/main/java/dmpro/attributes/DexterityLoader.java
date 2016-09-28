package dmpro.attributes;

import java.io.File;

public class DexterityLoader extends AttributeLoader {

	public DexterityLoader() {
		// TODO Auto-generated constructor stub
		super(Dexterity.class,new File(attributeDir + "dexterity.tsv"));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DexterityLoader d = new DexterityLoader();
		d.attributeTable.stream().forEach( a -> System.out.println(a.toString()));

	}

}
