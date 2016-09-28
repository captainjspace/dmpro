package dmpro.spells;

public enum Units {
 segments(.1),
 segment(.1),
 rounds(1),
 round(1), 
 turns(10),
 turn(1),
 hour(60),
 hours(60),
 day(24*60),
 days(24*60), 
 recipient(0), // some spells use recipients - sort this out later.
 seed(0),
 radius(0),
 mile(0),
 usage(0);
	
	private double u;
	
	Units(double u) {
		this.u = u;
	}
	public double getUnitMultiplier() {
		return this.u;
	}
}
