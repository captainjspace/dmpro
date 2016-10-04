package dmpro.data.loaders;

import dmpro.items.CoinItem.CoinType;
import dmpro.items.Item.ItemType;

public class StandardItemRecord implements TSVData {

	//ITEM	FREQUENCY	TYPE	ItemType	ENCUMBRANCE	COST	ItemValue	Cointype	SOURCE	p. #
	static final int fieldCount = 10;
	
	private String itemName;
	private ItemType itemType = ItemType.STANDARD;
	private ItemFrequencyType itemFrequencyType;
	private StandardItemType standardItemType;
	private int itemEncumbrance;
	private int itemValue;
	private CoinType coinType = CoinType.GOLD;
	
	public StandardItemRecord(String [] fields) {
		itemName = fields[0];
		itemFrequencyType = ItemFrequencyType.valueOf(fields[1].toUpperCase().replaceAll(" ",""));
		//skip
		standardItemType = StandardItemType.valueOf(fields[3]);
		itemEncumbrance = Integer.parseInt(fields[4]);
		itemValue = Integer.parseInt(fields[6]);
		coinType = coinType.getByShortName(fields[7]);
	}

	/**
	 * @return the fieldcount
	 */
	public static int getFieldcount() {
		return fieldCount;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @return the standardItemType
	 */
	public StandardItemType getStandardItemType() {
		return standardItemType;
	}

	/**
	 * @return the itemEncumbrance
	 */
	public int getItemEncumbrance() {
		return itemEncumbrance;
	}

	/**
	 * @return the itemValue
	 */
	public int getItemValue() {
		return itemValue;
	}

	/**
	 * @return the coinType
	 */
	public CoinType getCoinType() {
		return coinType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StandardItemRecord [" + (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "")
				+ (itemFrequencyType != null ? "itemFrequencyType=" + itemFrequencyType + ", " : "")
				+ (standardItemType != null ? "standardItemType=" + standardItemType + ", " : "") + "itemEncumbrance="
				+ itemEncumbrance + ", itemValue=" + itemValue + ", " + (coinType != null ? "coinType=" + coinType : "")
				+ "]";
	}

	/**
	 * @return the itemFrequencyType
	 */
	public ItemFrequencyType getItemFrequencyType() {
		return itemFrequencyType;
	}

	/**
	 * @return the itemType
	 */
	public ItemType getItemType() {
		return itemType;
	}
	
	}
