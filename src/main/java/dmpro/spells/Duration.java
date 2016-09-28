package dmpro.spells;

public class Duration {
	//does this need to be it's own class -- probably not only one duration per spell
	boolean special;
	boolean permanent;
	int durationBase;
	int durationModifier;
	int duration = durationBase + durationModifier;
}
