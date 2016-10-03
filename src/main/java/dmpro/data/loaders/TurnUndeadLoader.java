package dmpro.data.loaders;

import java.util.Map;
import java.util.stream.Collectors;

public class TurnUndeadLoader extends TSVLoader {
	
	public TurnUndeadLoader() {
		super(TurnUndeadRecord.class, "turn-undead.tsv");
	}

	public Map<UndeadType, TurnUndeadRecord> getRecord(int level) {
		return tsvTable.stream()
				.map( p -> (TurnUndeadRecord)p)
				.filter(p -> p.getLevel() == level)
				.collect(Collectors.toMap(p -> p.getUndeadType(),v -> v));
	}
	
	public static void main(String[] args) {
		TurnUndeadLoader tul = new TurnUndeadLoader();
		tul.tsvTable.stream().forEach( p -> System.out.println(p.toString()));
		tul.getRecord(4).entrySet()
		.stream()
		.filter(p -> p.getValue().getToTurn() < 21)
		.sorted((t1,t2) -> Integer.compare(t1.getValue().getToTurn(),t2.getValue().getToTurn()))
		.forEach(e -> System.out.format("Level %d Cleric needs a %d to turn %s class undead\n",
				e.getValue().getLevel(), e.getValue().getToTurn(), e.getKey()));
	}

}
