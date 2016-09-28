package dmpro.attributes;

import java.io.File;

public class IntelligenceLoader extends AttributeLoader {

	
	public IntelligenceLoader() {
		super(Intelligence.class,new File (attributeDir + "intelligence.tsv"));
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntelligenceLoader intelligence = new IntelligenceLoader();
	}

}
