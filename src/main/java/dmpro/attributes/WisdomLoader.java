package dmpro.attributes;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class WisdomLoader extends AttributeLoader {
    //Wisdom wisdom;
	public WisdomLoader() {
		// TODO Auto-generated constructor stub
		super(Wisdom.class,new File(attributeDir + "wisdom.tsv"));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WisdomLoader wl = new WisdomLoader();
	}

}
