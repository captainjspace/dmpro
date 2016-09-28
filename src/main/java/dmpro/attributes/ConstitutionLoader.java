package dmpro.attributes;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class ConstitutionLoader extends AttributeLoader {

	public ConstitutionLoader() {
		// TODO Auto-generated constructor stub
		super(Constitution.class,new File(attributeDir + "constitution.tsv"));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConstitutionLoader c = new ConstitutionLoader();
		try {
			c.load(Constitution.class);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
