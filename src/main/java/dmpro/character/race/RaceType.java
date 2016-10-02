package dmpro.character.race;

public enum RaceType {
	HUMAN(1, "Human", new Human()),
	ELF(2,"Elf", new Elf()),
	DWARF(3,"Dwarven", new Dwarf()),
	HALFELF(4,"Half-Elf", new HalfElf()),
	HALFORC(5,"Half-Orc", new HalfOrc()),
	HALFLING(6,"Halfling", new Halfling()),
	GNOME(7,"Gnome", new Gnome());
	
	int raceIndex;
	String raceName;
	Race race;
	
	RaceType(int raceIndex, String raceName, Race race) {
		this.raceIndex = raceIndex;
		this.raceName = raceName;
		this.race = race;
	}
	public int raceIndex() { return raceIndex; }
	public String raceName() { return raceName; }
	public Race newRace() { return race;}
	
	public static RaceType ByIndex (int raceIndex) {
		for (RaceType raceType: RaceType.values()) {
			if ( raceIndex == raceType.raceIndex )  return raceType; 
		}
		return null;
	}
}

