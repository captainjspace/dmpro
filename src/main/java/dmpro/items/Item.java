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
	
	protected int itemId;
	protected String itemName;
	protected ItemType itemType; //coins, gems, weapon, armor, potion, scrolletc. enum?
	protected int itemCount;
	protected int itemValue; //gold piece value
	protected CoinType itemCurrency;
	protected int itemEncumbrance; //in gp
	protected boolean isMagic;
	protected boolean isTreasure;
	protected boolean isWeapon;
	protected boolean isProtection;
	protected String description;
	
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isMagic ? 1231 : 1237);
		result = prime * result + (isProtection ? 1231 : 1237);
		result = prime * result + (isTreasure ? 1231 : 1237);
		result = prime * result + (isWeapon ? 1231 : 1237);
		result = prime * result + itemCount;
		result = prime * result + ((itemCurrency == null) ? 0 : itemCurrency.hashCode());
		result = prime * result + itemEncumbrance;
		result = prime * result + itemId;
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + itemValue;
		result = prime * result + ((modifiers == null) ? 0 : modifiers.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (isMagic != other.isMagic) {
			return false;
		}
		if (isProtection != other.isProtection) {
			return false;
		}
		if (isTreasure != other.isTreasure) {
			return false;
		}
		if (isWeapon != other.isWeapon) {
			return false;
		}
		if (itemCount != other.itemCount) {
			return false;
		}
		if (itemCurrency != other.itemCurrency) {
			return false;
		}
		if (itemEncumbrance != other.itemEncumbrance) {
			return false;
		}
		if (itemId != other.itemId) {
			return false;
		}
		if (itemName == null) {
			if (other.itemName != null) {
				return false;
			}
		} else if (!itemName.equals(other.itemName)) {
			return false;
		}
		if (itemType != other.itemType) {
			return false;
		}
		if (itemValue != other.itemValue) {
			return false;
		}
		if (modifiers == null) {
			if (other.modifiers != null) {
				return false;
			}
		} else if (!modifiers.equals(other.modifiers)) {
			return false;
		}
		return true;
	}

}
