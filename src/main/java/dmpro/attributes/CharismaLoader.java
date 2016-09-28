package dmpro.attributes;

import java.io.File;

public class CharismaLoader extends AttributeLoader {

	public CharismaLoader() {
		super(Charisma.class,new File(attributeDir + "charisma.tsv"));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CharismaLoader c = new CharismaLoader();
		if (args.length >= 1) c.getRecord(args[0]);
		

	}

}
