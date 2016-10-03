package dmpro.data.loaders;

import java.util.NoSuchElementException;

import dmpro.character.race.RaceType;
import dmpro.items.DamageRoll;

public class RaceSizeLoader extends TSVLoader {
	
	public RaceSizeLoader() {
		super(RaceSizeRecord.class, "race-size.tsv");
	}

	public int getHeight(RaceType raceType) {
		RaceSizeRecord rHeight = this.tsvTable.stream()
				.map( p -> (RaceSizeRecord)p)
				.filter(p -> p.getRaceType() == raceType)
				.findFirst().get();
		DamageRoll dr = new DamageRoll(rHeight.getHeightRoll());
		return dr.getDamageRoll();
	}
	public int getWeight(RaceType raceType) {
		RaceSizeRecord rHeight = this.tsvTable.stream().map(p -> (RaceSizeRecord)p)
				.filter(p -> p.getRaceType() == raceType)
				.findFirst().get();
		DamageRoll dr = new DamageRoll(rHeight.getWeightRoll());
		return dr.getDamageRoll();
	}
	
	public static void main(String[] args) {
		RaceSizeLoader rsl = new RaceSizeLoader();
		for (RaceType r: RaceType.values()) {
			try {
				System.out.format("Race:%s\t Height:%d\t Weight%d\n", r, rsl.getHeight(r), rsl.getWeight(r));
			} catch (NoSuchElementException e) {
				System.out.format("Race:%s has no size record\n", r);
			}
		}
		

	}

}
