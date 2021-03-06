package dmpro.data.loaders;

import dmpro.items.CoinItem.CoinType;
import dmpro.items.Item;


public class StandardItemRecord extends Item implements TSVData {

	//ITEM	FREQUENCY	TYPE	ItemType	ENCUMBRANCE	COST	ItemValue	Cointype	SOURCE	p. #
	static final int fieldCount = 10;

	//private String itemName;
	//private ItemType itemType = ItemType.STANDARD;
	private ItemFrequencyType itemFrequencyType;
	private StandardItemType standardItemType;
	//private int itemEncumbrance;
	//private int itemValue;
	//private CoinType coinType = CoinType.GOLD;

	@SuppressWarnings("static-access")
	public StandardItemRecord(String [] fields) {
		itemName = fields[0];
		itemType = ItemType.STANDARD;
		itemFrequencyType = ItemFrequencyType.valueOf(fields[1].toUpperCase().replaceAll(" ",""));
		//skip
		standardItemType = StandardItemType.valueOf(fields[3]);
		itemEncumbrance = Integer.parseInt(fields[4]);
		itemValue = Integer.parseInt(fields[6]);
		itemCurrency = CoinType.getByShortName(fields[7]);
	}

	/**
	 * @return the fieldcount
	 */
	public static int getFieldcount() {
		return fieldCount;
	}


	/**
	 * @return the standardItemType
	 */
	public StandardItemType getStandardItemType() {
		return standardItemType;
	}

	/**
	 * @return the itemFrequencyType
	 */
	public ItemFrequencyType getItemFrequencyType() {
		return itemFrequencyType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((itemFrequencyType == null) ? 0 : itemFrequencyType.hashCode());
		result = prime * result + ((standardItemType == null) ? 0 : standardItemType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof StandardItemRecord)) {
			return false;
		}
		StandardItemRecord other = (StandardItemRecord) obj;
		if (itemFrequencyType != other.itemFrequencyType) {
			return false;
		}
		if (standardItemType != other.standardItemType) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StandardItemRecord ["
				+ (itemFrequencyType != null ? "itemFrequencyType=" + itemFrequencyType + ", " : "")
				+ (standardItemType != null ? "standardItemType=" + standardItemType + ", " : "")
				+ (modifiers != null ? "modifiers=" + modifiers + ", " : "") + "itemId=" + itemId + ", "
				+ (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "") + "itemCount=" + itemCount + ", itemValue="
				+ itemValue + ", " + (itemCurrency != null ? "itemCurrency=" + itemCurrency + ", " : "")
				+ "itemEncumbrance=" + itemEncumbrance + ", isMagic=" + isMagic + ", isTreasure=" + isTreasure
				+ ", isWeapon=" + isWeapon + ", isProtection=" + isProtection + ", "
				+ (description != null ? "description=" + description : "") + "]";
	}



}
