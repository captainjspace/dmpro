package dmpro.character.race;

public enum RaceType {
	HUMAN(1, "Human"),
	ELF(2,"Elf"),
	DWARF(3,"Dwarven"),
	HALFELF(4,"Half-Elf"),
	HALFORC(5,"Half-Orc"),
	HALFLING(6,"Halfling"),
	GNOME(7,"Gnome");
	
	int raceIndex;
	String raceName;
	
	RaceType(int raceIndex, String raceName) {
		this.raceIndex = raceIndex;
		this.raceName = raceName;
	}
	public int raceIndex() { return raceIndex; }
	public String raceName() { return raceName; }
	
	public static RaceType ByIndex (int raceIndex) {
		for (RaceType raceType: RaceType.values()) {
			if ( raceIndex == raceType.raceIndex )  return raceType; 
		}
		return null;
	}
}

