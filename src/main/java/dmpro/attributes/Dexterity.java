package dmpro.attributes;

public class Dexterity extends Attribute {
	
	static final int fieldCount = 4;
	
	int initiativeAdjustment;
	int missileAttackAdjustment;
	int defensiveAdjustment;
	
	public Dexterity(String[] fields) {
		attributeType = AttributeType.DEXTERITY;
		attributeName = "Dexterity";
		abilityScore = Integer.parseInt(fields[0].trim());
		initiativeAdjustment = Integer.parseInt(fields[1].trim());
		missileAttackAdjustment = initiativeAdjustment;
		defensiveAdjustment = Integer.parseInt(fields[3].trim());
	}
	
	//refactor
	//Thieves Adjustments
	float pickPockets;
	float openLocks;
	float locateRemoveTraps;
	float movingSilently;
	float hidingInShadows;

	@Override
	public String toString() {
		return "Dexterity [initiativeAdjustment=" + initiativeAdjustment + ", missileAttackAdjustment="
				+ missileAttackAdjustment + ", defensiveAdjustment=" + defensiveAdjustment + "]";
	}

	public int getDefensiveAdjustment() {
		return defensiveAdjustment;
	}

	public void setDefensiveAdjustment(int defensiveAdjustment) {
		this.defensiveAdjustment = defensiveAdjustment;
	}

	public float getPickPockets() {
		return pickPockets;
	}

	public void setPickPockets(float pickPockets) {
		this.pickPockets = pickPockets;
	}

	public float getOpenLocks() {
		return openLocks;
	}

	public void setOpenLocks(float openLocks) {
		this.openLocks = openLocks;
	}

	public float getLocateRemoveTraps() {
		return locateRemoveTraps;
	}

	public void setLocateRemoveTraps(float locateRemoveTraps) {
		this.locateRemoveTraps = locateRemoveTraps;
	}

	public float getMovingSilently() {
		return movingSilently;
	}

	public void setMovingSilently(float movingSilently) {
		this.movingSilently = movingSilently;
	}

	public float getHidingInShadows() {
		return hidingInShadows;
	}

	public void setHidingInShadows(float hidingInShadows) {
		this.hidingInShadows = hidingInShadows;
	}

	public static int getFieldcount() {
		return fieldCount;
	}

	public int getInitiativeAdjustment() {
		return initiativeAdjustment;
	}

	public void setInitiativeAdjustment(int initiativeAdjustment) {
		this.initiativeAdjustment = initiativeAdjustment;
	}

	public int getMissileAttackAdjustment() {
		return missileAttackAdjustment;
	}

	public void setMissileAttackAdjustment(int missileAttackAdjustment) {
		this.missileAttackAdjustment = missileAttackAdjustment;
	}
	
	
	
}
