package dmpro.items;

import java.util.HashMap;
import java.util.Map;

import dmpro.utils.ParseUtils;

public class WeaponItem extends Item {
	public enum WeaponType {
		ONEHANDEDSWORD,
		TWOHANDEDSWORD,
		DAGGER,
		POLEARMCLASS,
		SPEAR,
		ONEHANDEDAXE,
		TWOHANDEDAXE,
		HAMMER,
		BOWCLASS,
		FLAIL,
		MACE,
		STAFF,
		MISSILE;
	}
	
	public enum Size {S,M,L;}
	WeaponType weaponType;
	Map <Size, DamageRoll>damageMap = new HashMap<Size,DamageRoll>();
	float length;
	float spaceRequired;
	int speedFactor; //segment of round impact is made.
	//ArmorClass adjustments????
	float fireRate;
	MissileRange range;

	public WeaponItem() {
		this.setMagic(false);
		this.setWeapon(true);
		this.setItemType(ItemType.WEAPON);
	}
	
	public WeaponItem(String[] fields) {
		this();
		this.itemName=fields[0];
		this.weaponType=WeaponType.valueOf(fields[1]);
		this.weight=Integer.parseInt(fields[4]);
		this.itemValue = Integer.parseInt(fields[2].split(" ")[0]);
		this.itemCurrency = fields[2].split(" ")[1];
		if (this.weaponType != WeaponType.MISSILE) this.speedFactor=Integer.parseInt(fields[7]);
		else this.fireRate=ParseUtils.expressionToFloat(fields[7]);
		this.damageMap.put(Size.S, new DamageRoll(fields[8]));
		this.damageMap.put(Size.M, new DamageRoll(fields[8]));
		this.damageMap.put(Size.L, new DamageRoll(fields[9]));
		if (this.weaponType == WeaponType.MISSILE || this.weaponType == WeaponType.BOWCLASS)
			this.range = new MissileRange (fields[10],fields[11], fields[12]);
		
	}

	/**
	 * @return the weaponType
	 */
	public WeaponType getWeaponType() {
		return weaponType;
	}

	/**
	 * @param weaponType the weaponType to set
	 */
	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	/**
	 * @return the damageMap
	 */
	public Map<Size, DamageRoll> getDamageMap() {
		return damageMap;
	}

	/**
	 * @param damageMap the damageMap to set
	 */
	public void setDamageMap(Map<Size, DamageRoll> damageMap) {
		this.damageMap = damageMap;
	}

	/**
	 * @return the length
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * @return the spaceRequired
	 */
	public float getSpaceRequired() {
		return spaceRequired;
	}

	/**
	 * @param spaceRequired the spaceRequired to set
	 */
	public void setSpaceRequired(float spaceRequired) {
		this.spaceRequired = spaceRequired;
	}

	/**
	 * @return the speedFactor
	 */
	public int getSpeedFactor() {
		return speedFactor;
	}

	/**
	 * @param speedFactor the speedFactor to set
	 */
	public void setSpeedFactor(int speedFactor) {
		this.speedFactor = speedFactor;
	}

	/**
	 * @return the fireRate
	 */
	public float getFireRate() {
		return fireRate;
	}

	/**
	 * @param fireRate the fireRate to set
	 */
	public void setFireRate(float fireRate) {
		this.fireRate = fireRate;
	}

	/**
	 * @return the range
	 */
	public MissileRange getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(MissileRange range) {
		this.range = range;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeaponItem [" + (weaponType != null ? "weaponType=" + weaponType + ", " : "")
				+ (damageMap != null ? "damageMap=" + damageMap + ", " : "") + "length=" + length + ", spaceRequired="
				+ spaceRequired + ", speedFactor=" + speedFactor + ", fireRate=" + fireRate + ", "
				+ (range != null ? "range=" + range + ", " : "") + "itemId=" + itemId + ", "
				+ (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "") + "itemValue=" + itemValue + ", itemCurrency="
				+ itemCurrency + ", weight=" + weight + ", isMagic=" + isMagic + ", isTreasure=" + isTreasure
				+ ", isWeapon=" + isWeapon + ", isProtection=" + isProtection + ", "
				+ (description != null ? "description=" + description : "") + "]";
	}
}
