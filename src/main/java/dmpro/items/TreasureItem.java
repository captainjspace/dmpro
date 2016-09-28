package dmpro.items;

public class TreasureItem extends Item {
	//Treasure Item	Encumberance Type	Count	
	//Unit Value Gold	Gold/XP Value	Encumberance	Recipient	Traded Value
	private String[] fields = {"Item","Type","Count","UnitValue","GP Value","Encumbrance","Owner", "Traded Value"};
	
	public String treasureItem;
	public String encumbranceType;
	public int count;
	public float unitValueGold;
	public float goldValue;
	public float encumbrance;
	public String recipient;
	public float tradedValue;
	
	
	
    public String getTreasureItem() {
		return treasureItem;
	}

	public void setTreasureItem(String treasureItem) {
		this.treasureItem = treasureItem;
	}

	public String getEncumbranceType() {
		return encumbranceType;
	}

	public void setEncumbranceType(String encumbranceType) {
		this.encumbranceType = encumbranceType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getUnitValueGold() {
		return unitValueGold;
	}

	public void setUnitValueGold(float unitValueGold) {
		this.unitValueGold = unitValueGold;
	}

	public float getGoldValue() {
		return goldValue;
	}

	public void setGoldValue(float goldValue) {
		this.goldValue = goldValue;
	}

	public float getEncumbrance() {
		return encumbrance;
	}

	public void setEncumbrance(float encumbrance) {
		this.encumbrance = encumbrance;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public float getTradedValue() {
		return tradedValue;
	}

	public void setTradedValue(float tradedValue) {
		this.tradedValue = tradedValue;
	}

  public TreasureItem() {}
  
  public TreasureItem(String[] el) {
	    treasureItem=el[0];
		encumbranceType=el[1];
		count=Integer.parseInt(el[2].replaceAll(",", ""));
		unitValueGold=Float.parseFloat(el[3]);
		goldValue=Float.parseFloat(el[4]);
		encumbrance=Float.parseFloat(el[5]);
		recipient=el[6];
		try {
		  tradedValue=Float.parseFloat(el[7]);
		} catch (NumberFormatException n) {
		  System.out.println("ERROR RECORD " + this.toString());
		  tradedValue=0.0f;
		}
  }
  
  public String toString() {
	StringBuffer sb = new StringBuffer();
	String[] values = { treasureItem, encumbranceType, String.valueOf(count), String.valueOf(unitValueGold), 
			String.valueOf(goldValue),
			String.valueOf(encumbrance),recipient,String.valueOf(tradedValue) };
	
	for (int i=0; i<=fields.length-1;i++) {
		sb.append("\t").append(i).append("-").append(fields[i]).append("---").append(values[i]).append("\n");
	}	
	//return sb.toString();
	return String.join("\t", values);
	
  }
}
