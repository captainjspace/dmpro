package dmpro.items;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import dmpro.character.Character;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Comparator;


public class TreasureItemViewer {

    private List <TreasureItem> t = new ArrayList<TreasureItem>();
    Character c=new Character();
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreasureItemViewer tlf = new TreasureItemViewer();
		tlf.load();
		for (String s : args) {
		  tlf.view(s);
		}
	}

	private String file="data/MasterTreasureList.tsv";
	

	private void view(String filter) {	
		/**
		t.sort(new Comparator<TreasureItem>() {
		@Override
		public int compare(TreasureItem t1, TreasureItem t2) {
			return t1.encumbranceType.compareTo(t2.encumbranceType);
		 }
		});
		*/
		//t.sort((TreasureItem t1, TreasureItem t2) -> t1.encumbranceType.compareTo(t2.encumbranceType));
		//t.sort((t1, t2) -> t1.encumbranceType.compareTo(t2.encumbranceType));
		//t.sort(Comparator.comparing(TreasureItem::encumbranceType.thenComparing(TreasureItem::unitValue)));
		Comparator<TreasureItem> a = (t1, t2) -> t1.encumbranceType.compareTo(t2.encumbranceType);
		Comparator<TreasureItem> b = (t1, t2) -> Float.compare(t1.goldValue,t2.goldValue);
		
		
		List <TreasureItem> ft = getTreasureItems().stream().filter(p -> p.recipient.equalsIgnoreCase(filter))
				.collect(Collectors.toList());
		ft.sort(a.thenComparing(b.reversed()));
		
		ft.stream().forEach(t -> System.out.println(t.toString()));
		
		ft.stream()
		.collect(Collectors.groupingBy(t -> t.encumbranceType, 
				Collectors.summingDouble(t -> t.goldValue)))
				.forEach((encumbranceType,sumGoldValue) -> System.out.println(encumbranceType + "\t" + sumGoldValue));
	}
	
	public void load()  {
		TreasureItem ti; 
		Scanner scan;
		

		try {

			scan = new Scanner(new FileReader(file));
			//scan.useDelimiter("\t|\r\n")
			scan.nextLine(); //skip headers
			
			while(scan.hasNextLine()){
				String[] el = scan.nextLine().split("\t",8);
				ti = new TreasureItem(el);	
				getTreasureItems().add(ti);
	
			}
			scan.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error Reading File");
			e.printStackTrace();
		} 
	}

	public List <TreasureItem> getTreasureItems() {
		return t;
	}

	public void setTreasureItems(List <TreasureItem> t) {
		this.t = t;
	}



}
