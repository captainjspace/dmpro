package dmpro.data.loaders;


import java.util.NoSuchElementException;

import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.character.race.RaceType;
import dmpro.items.DamageRoll;

public class RaceClassAgeLoader extends TSVLoader {

	
	public RaceClassAgeLoader() {
		super(RaceClassAgeRecord.class, "race-age.tsv");
		// TODO Auto-generated constructor stub
	}

	public int getAge(RaceType raceType, CharacterClassType characterClassType) {
		RaceClassAgeRecord rCar = this.tsvTable.stream().map(p -> (RaceClassAgeRecord)p)
				.filter(p -> p.getRaceType() == raceType &&
						p.getCharacterClassType() == characterClassType).findFirst().get();
		DamageRoll dr = new DamageRoll(rCar.getAgeRoll());
		return dr.getDamageRoll();
	}
	
	//TODO: move to test
	public static void main (String[] args) {
		System.out.println("RaceClassAgeLoader");
		RaceClassAgeLoader rCal = new RaceClassAgeLoader();
		rCal.tsvTable.stream().forEach(p -> System.out.println( ((RaceClassAgeRecord)p).getAgeRoll() ) );
		
		for (RaceType r: RaceType.values()) {
			for (CharacterClassType cc: CharacterClassType.values()) {
				try {
					System.out.format("%s, %s Age: %d\n",  r,cc, rCal.getAge(r,cc));
				} catch (NoSuchElementException e) {
				System.out.format("%s, %s Age: No Record\n",  r,cc);
				}
			}
		}
	}
}
