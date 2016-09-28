package dmpro.attributes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;


public class StrengthLoader extends AttributeLoader {
	//Strength strength;
	
	public StrengthLoader() {
		// TODO Auto-generated constructor stub
		super(Strength.class,new File(attributeDir + "strength.tsv"));
	}
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		StrengthLoader s = new StrengthLoader();
		try {
			s.load(Strength.class);
			s.attributeTable.stream().forEach(a -> System.out.println(a.toString()));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (args.length == 1 ) {
			int pickScore = Integer.parseInt(args[0]);
			float percentScore = Float.parseFloat(args[1]);		
			s.attributeTable.stream()
			.filter( p -> ((Strength) p).abilityScore == pickScore)
			.filter( p -> ((Strength) p).percentOverHuman-percentScore <.01)
			.forEach(a -> System.out.println(a.toString()));	
			
		}


	}

}
