package dmpro.character.classes;

public class HitDieRecord {

	private int hitDieRoll;
	private int constitutionBonus = 0;
	// TODO Auto-generated constructor stub
	
	public HitDieRecord(int hitDieRoll, int constitutionBonus) {
		this.hitDieRoll = hitDieRoll;
		this.constitutionBonus =constitutionBonus;
	}
	
	public int getHitPoints() {
		return hitDieRoll + constitutionBonus;
	}
}
