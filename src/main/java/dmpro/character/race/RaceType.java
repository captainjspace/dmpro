package dmpro.character.race;

public enum RaceType {
	HUMAN(1, "Human", Human.class),
	ELF(2,"Elf", Elf.class),
	DWARF(3,"Dwarven", Dwarf.class),
	HALFELF(4,"Half-Elf", HalfElf.class),
	HALFORC(5,"Half-Orc", HalfOrc.class),
	HALFLING(6,"Halfling", Halfling.class),
	GNOME(7,"Gnome", Gnome.class);
	
	int raceIndex;
	String raceName;
	Class<? extends Race> race;
	
	RaceType(int raceIndex, String raceName, Class<? extends Race> race) {
		this.raceIndex = raceIndex;
		this.raceName = raceName;
		this.race = race;
	}
	public int raceIndex() { return raceIndex; }
	public String raceName() { return raceName; }
	public Race newRace() { 
		Race created = null;
		try {
			created = race.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return created;
	}
	
	public static RaceType ByIndex (int raceIndex) {
		for (RaceType raceType: RaceType.values()) {
			if ( raceIndex == raceType.raceIndex )  return raceType; 
		}
		return null;
	}
	
	public static void main (String [] args) {
		RaceType rt = RaceType.HUMAN;
		Race r = rt.newRace();
		System.out.println(r.getRaceType());
	}
}

