package dmpro.attributes;

public class Constitution extends Attribute {
	
	static final int fieldCount = 7;
	
	int hitPointAdjustment;
	int fighterHPAdjustment;
	float systemShock;
	float ressurectionSurvival;
	int poisonSaveAdjustment;
	int regenerationRate;  //1 pt per "x" turns
	
	public Constitution (String[] fields) {
		attributeType = AttributeType.CONSTITUTION;
	  attributeName = "Constitution";
	  
	  abilityScore = Integer.parseInt(fields[0].trim());
	  this.hitPointAdjustment = Integer.parseInt(fields[1].trim());
	  this.fighterHPAdjustment = Integer.parseInt(fields[2].trim());
	  this.systemShock = Float.parseFloat(fields[3].trim());
	  this.ressurectionSurvival = Float.parseFloat(fields[4].trim());
	  this.poisonSaveAdjustment = Integer.parseInt(fields[5].trim());
	  if (!dashCheck(fields[6]))
	    this.regenerationRate = Integer.parseInt(fields[6].trim());
	  
	}
	
	public int getHitPointAdjustment() {
		return hitPointAdjustment;
	}
	public void setHitPointAdjustment(int hitPointAdjustment) {
		this.hitPointAdjustment = hitPointAdjustment;
	}
	public float getSystemShock() {
		return systemShock;
	}
	public void setSystemShock(float systemShock) {
		this.systemShock = systemShock;
	}
	public float getRessurectionSurvival() {
		return ressurectionSurvival;
	}
	public void setRessurectionSurvival(float ressurectionSurvival) {
		this.ressurectionSurvival = ressurectionSurvival;
	}
	public int getPoisonSaveAdjustment() {
		return poisonSaveAdjustment;
	}
	public void setPoisonSaveAdjustment(int poisonSaveAdjustment) {
		this.poisonSaveAdjustment = poisonSaveAdjustment;
	}
	public int getRegenerationRate() {
		return regenerationRate;
	}
	public void setRegenerationRate(int regenerationRate) {
		this.regenerationRate = regenerationRate;
	}

	public int getFighterHPAdjustment() {
		return fighterHPAdjustment;
	}

	public void setFighterHPAdjustment(int fighterHPAdjustment) {
		this.fighterHPAdjustment = fighterHPAdjustment;
	}

	@Override
	public String toString() {
		return "Constitution [hitPointAdjustment=" + hitPointAdjustment + ", fighterHPAdjustment=" + fighterHPAdjustment
				+ ", systemShock=" + systemShock + ", ressurectionSurvival=" + ressurectionSurvival
				+ ", poisonSaveAdjustment=" + poisonSaveAdjustment + ", regenerationRate=" + regenerationRate + "]";
	}
	
	
}

