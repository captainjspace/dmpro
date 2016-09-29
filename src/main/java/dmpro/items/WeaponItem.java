package dmpro.items;

import java.util.HashMap;
import java.util.Map;

import dmpro.utils.ParseUtils;

public class WeaponItem extends Item {
	
	public enum WeaponType {
		ONEHANDEDSWORD(1, "One Handed Swords"),
		TWOHANDEDSWORD(2, "The Venerable Two Handed Sword - Bane of all big beasts!"),
		DAGGER(3,"Daggers, Knives, Stilletto's for sneaking in the night or hurling at your enemy"),
		POLEARMCLASS(4, "Polearms for War!"),
		SPEAR(5, "Spears, Javelins for both Pole and Missile attacks!"),
		ONEHANDEDAXE(6, "One handed Axes - for chopping wood, and throwing on occasion"),
		TWOHANDEDAXE(7,"The Battle Axe - Crush crumble and chomp through Armor!"),
		HAMMER(8, "The War Hammer - Thrown or Smashed"),
		BOWCLASS(9, "Bows and Crossbows - no party can be without"),
		FLAIL(10, "Flails, and whips and all chainy things"),
		MACE(11, "Mace, Club and One-handed crushy stuff!"),
		STAFF(12, "No Mage should be without his trusty Staff!"),
		MISSILE(13,"Darts and Blowguns, Shurikens and Thrown Chickens!");
		
		int weaponTypeIndex;
		String weaponTypeDescription;
		
		WeaponType ( int weaponTypeIndex, String weaponTypeDescription) {
			this.weaponTypeIndex = weaponTypeIndex;
			this.weaponTypeDescription = weaponTypeDescription;
		}
		
		public int weaponTypeIndex() {
			return weaponTypeIndex;
		}
		public String weaponTypeDescription() {
			return weaponTypeDescription;
		}
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
	
	public static void main (String[] args) {
		for (WeaponType wt : WeaponType.values()) {
			System.out.format("\t%d  :  %s\n", 
					wt.weaponTypeIndex(), 
					wt.weaponTypeDescription());
		}
	}
}
