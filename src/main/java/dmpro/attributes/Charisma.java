package dmpro.attributes;

public class Charisma extends Attribute {
	
	static final int fieldCount = 4;
	int maxHenchment;
	float loyaltyBase;
	float reactionAdjustment;
	int awePowerHitDice; //gods and demi-gods only, not mortals
	
	public Charisma(String[] fields) {
		attributeType = AttributeType.CHARISMA;
		attributeName = "Charisma";
		abilityScore = Integer.parseInt(fields[0].trim());
		this.maxHenchment = Integer.parseInt(fields[1].trim());
		this.loyaltyBase = Float.parseFloat(fields[2].trim());
		this.reactionAdjustment = Float.parseFloat(fields[3].trim());
	}
	
	public int getMaxHenchment() {
		return maxHenchment;
	}
	public void setMaxHenchment(int maxHenchment) {
		this.maxHenchment = maxHenchment;
	}
	public float getLoyaltyBase() {
		return loyaltyBase;
	}
	public void setLoyaltyBase(float loyaltyBase) {
		this.loyaltyBase = loyaltyBase;
	}
	public float getReactionAdjustment() {
		return reactionAdjustment;
	}
	public void setReactionAdjustment(float reactionAdjustment) {
		this.reactionAdjustment = reactionAdjustment;
	}
	public int getAwePowerHitDice() {
		return awePowerHitDice;
	}
	public void setAwePowerHitDice(int awePowerHitDice) {
		this.awePowerHitDice = awePowerHitDice;
	}

	@Override
	public String toString() {
		return "Charisma [maxHenchment=" + maxHenchment + ", loyaltyBase=" + loyaltyBase + ", reactionAdjustment="
				+ reactionAdjustment + "]";
	}
	
	
	
}
