package dmpro.utils;

public enum Dice {
  d1(1),
  d2(2),
  d3(3),
  d4(4),
  d6(6),
  d8(8),
  d10(10),
  d12(12),
  d20(20),
  d100(100), 
  d24(24); //for random hours in a day...
	
	private int sides;
	
	Dice(int sides) {
		this.sides = sides;
	}
	public int sides() {
		return sides;
	}
	
}
