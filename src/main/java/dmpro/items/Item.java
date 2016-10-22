package dmpro.items;

import dmpro.items.CoinItem.CoinType;
import dmpro.modifier.Modifiable;
import dmpro.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Comparable<Item>, Modifiable {
	
	public enum ItemType {
		WEAPON,
		ARMOR,
		SHIELD,
		TREASURE,
		MAGIC,
		STANDARD,
		COINS,
		GEMS,
		JEWELRY,
		EMPTY;
	}

	public List<Modifier> modifiers = new ArrayList<Modifier>();
	
	int itemId;
	String itemName;
	protected ItemType itemType; //coins, gems, weapon, armor, potion, scrolletc. enum?
	int itemCount;
	int itemValue; //gold piece value
	protected CoinType itemCurrency;
	int itemEncumbrance; //in gp
	boolean isMagic;
	boolean isTreasure;
	boolean isWeapon;
	boolean isProtection;
	String description;
	
	
	public boolean isMagic() {
		return isMagic;
	}
	
	public boolean isTreasure() {
		return isTreasure;
	}
	
	public boolean isWeapon() {
		return isWeapon;
	}
	
	public boolean isProtection() {
		return isProtection;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public int getItemValue() {
		return itemValue;
	}
	public void setItemValue(int itemValue) {
		this.itemValue = itemValue;
	}
	public int getItemEncumbrance() {
		return itemEncumbrance;
	}
	public void setItemEncumbrance(int weight) {
		this.itemEncumbrance = weight;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setMagic(boolean isMagic) {
		this.isMagic = isMagic;
	}

	public void setTreasure(boolean isTreasure) {
		this.isTreasure = isTreasure;
	}

	public void setWeapon(boolean isWeapon) {
		this.isWeapon = isWeapon;
	}

	public void setProtection(boolean isProtection) {
		this.isProtection = isProtection;
	}

	@Override
	public String toString() {
		return "Item [itemName=" + itemName + ", itemType=" + itemType + ", itemValue=" + itemValue + ", weight="
				+ itemEncumbrance + "]";
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the itemCurrency
	 */
	public CoinType getItemCurrency() {
		return itemCurrency;
	}

	/**
	 * @param itemCurrency the itemCurrency to set
	 */
	public void setItemCurrency(String itemCurrency) {
		try {
		this.itemCurrency = CoinType.valueOf(itemCurrency);
		} catch (IllegalArgumentException e) {
			this.itemCurrency = CoinType.GOLD.getByShortName(itemCurrency);
		}
	}

	public void setItemCurrency(CoinType coinType) {
		this.itemCurrency = coinType;
	}
	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the itemType
	 */
	public ItemType getItemType() {
		return itemType;
	}

	/* (non-Javadoc)
	 * @see dmpro.Modifiable#addModifier(dmpro.Modifier)
	 */
	@Override
	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}

	/**
	 * @return the modifiers
	 */
	public List<Modifier> getModifiers() {
		return modifiers;
	}

	/**
	 * @param modifiers the modifiers to set
	 */
	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	/**
	 * @return the count
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount the count to set
	 */
	public void setCount(int itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	/**
	 * @param item item to compare name(alpha) to this one
	 */
	public int compareTo(Item item) {
		return this.itemName.toLowerCase().compareTo(item.itemName.toLowerCase());
	}

}
