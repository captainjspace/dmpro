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



}
