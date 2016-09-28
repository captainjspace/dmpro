package dmpro.items;

import dmpro.modifier.Modifiable;
import dmpro.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Modifiable {
	
	public enum ItemType {
		WEAPON,
		TREASURE,
		MAGIC,
		STANDARD,
		COINS,
		GEMS,
		JEWELRY;
	}

	public List<Modifier> modifiers = new ArrayList<Modifier>();
	
	int itemId;
	String itemName;
	ItemType itemType; //coins, gems, weapon, armor, potion, scrolletc. enum?
	int itemValue; //gold piece value
	String itemCurrency;
	int weight; //in gp
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
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
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
				+ weight + "]";
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
	public String getItemCurrency() {
		return itemCurrency;
	}

	/**
	 * @param itemCurrency the itemCurrency to set
	 */
	public void setItemCurrency(String itemCurrency) {
		this.itemCurrency = itemCurrency;
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

	
  
  
  
}
