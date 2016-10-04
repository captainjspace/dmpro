package dmpro.data.loaders;

import java.util.ArrayList;
import java.util.List;

import dmpro.items.CoinItem.CoinType;
import dmpro.items.Item.ItemType;
import dmpro.modifier.ArmorClassModifier;
import dmpro.modifier.ArmorClassModifier.ArmorClassModifierType;
import dmpro.modifier.Modifier;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;

public class ArmorRecord implements TSVData {

	static final int fieldCount = 12;
	//Type	Item	Cost	Currency	Weight	Base Movement	Fixed AC	Modified AC	# of Attacks	Damage Absorb	HP	Repair Cost
	private ItemType itemType = ItemType.ARMOR;
	private ArmorType armorType;
	private String itemName;
	private int itemValue;
	private CoinType itemCurrency = CoinType.GOLD;
	private int itemEncumbrance;
	private int armorBaseMovement;
	private int armorClass;
	private int acModifier;
	private int numAttacksDefended;
	private int damageAbsorbedPerDie;
	private int armorHitPoints;
	private int repairCost;
	private List<ArmorClassModifier> modifiers = new ArrayList<ArmorClassModifier>();

	public ArmorRecord(String [] fields) {
		armorType = ArmorType.valueOf(fields[0]);
		itemName = fields[1];
		itemValue = Integer.parseInt(fields[2]);
		itemCurrency= CoinType.valueOf(fields[3]);
		itemEncumbrance = Integer.parseInt(fields[4]);
		if (armorType == ArmorType.ARMOR) {
			armorBaseMovement = Integer.parseInt(fields[5]);
			armorClass = Integer.parseInt(fields[6]);
			ArmorClassModifier armorModifier = new ArmorClassModifier();
			armorModifier.modifierPriority = ModifierPriority.HIGH;
			armorModifier.modifierSource = ModifierSource.ARMOR;
			armorModifier.armorClassModifierType = ArmorClassModifierType.ALL;
			armorModifier.setModifier(armorClass);
			armorModifier.setDescription("Standard Armor");
			modifiers.add(armorModifier);
			try {
				damageAbsorbedPerDie = Integer.parseInt(fields[9]);
				armorHitPoints = Integer.parseInt(fields[10]);
				repairCost = Integer.parseInt(fields[11]);
				ArmorClassModifier damageModifier = new ArmorClassModifier();
				damageModifier.modifierPriority = ModifierPriority.HIGH;
				damageModifier.modifierSource = ModifierSource.ARMOR;
				damageModifier.armorClassModifierType = ArmorClassModifierType.ALL;
				damageModifier.setModifier(armorHitPoints);
				damageModifier.setDescription("Field/Full Plate Armor Damage Aborption");
				modifiers.add(damageModifier);
				
			} catch (NumberFormatException e) {
				//no action only exists for fields plate and full plate
			}
		} else {
			//SHIELDS
			acModifier = Integer.parseInt(fields[7]);
			numAttacksDefended = Integer.parseInt(fields[8]);
			ArmorClassModifier shieldModifier = new ArmorClassModifier();
			shieldModifier.modifierPriority = ModifierPriority.HIGH;
			shieldModifier.modifierSource = ModifierSource.ARMOR;
			shieldModifier.armorClassModifierType = ArmorClassModifierType.FRONT;
			shieldModifier.setModifier(acModifier);
			shieldModifier.setDescription("Standard Shield");
			modifiers.add(shieldModifier);
		}

	}

	/**
	 * @return the armorType
	 */
	public ArmorType getArmorType() {
		return armorType;
	}

	/**
	 * @return the armorName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @return the armorCost
	 */
	public int getItemValue() {
		return itemValue;
	}

	/**
	 * @return the armorEncumbrance
	 */
	public int getArmorEncumbrance() {
		return itemEncumbrance;
	}

	/**
	 * @return the armorBaseMovement
	 */
	public int getArmorBaseMovement() {
		return armorBaseMovement;
	}

	/**
	 * @return the armorClass
	 */
	public int getArmorClass() {
		return armorClass;
	}

	/**
	 * @return the acModifier
	 */
	public int getAcModifier() {
		return acModifier;
	}

	/**
	 * @return the numAttacksDefended
	 */
	public int getNumAttacksDefended() {
		return numAttacksDefended;
	}

	/**
	 * @return the damageAbsorbedPerDie
	 */
	public int getDamageAbsorbedPerDie() {
		return damageAbsorbedPerDie;
	}

	/**
	 * @return the armorHitPoints
	 */
	public int getArmorHitPoints() {
		return armorHitPoints;
	}

	/**
	 * @return the repairCost
	 */
	public int getRepairCost() {
		return repairCost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArmorRecord [" + (itemType != null ? "itemType=" + itemType + ", " : "")
				+ (armorType != null ? "armorType=" + armorType + ", " : "")
				+ (itemName != null ? "itemName=" + itemName + ", " : "") + "itemValue=" + itemValue + ", "
				+ (itemCurrency != null ? "itemCurrency=" + itemCurrency + ", " : "") + "itemEncumbrance="
				+ itemEncumbrance + ", armorBaseMovement=" + armorBaseMovement + ", armorClass=" + armorClass
				+ ", acModifier=" + acModifier + ", numAttacksDefended=" + numAttacksDefended
				+ ", damageAbsorbedPerDie=" + damageAbsorbedPerDie + ", armorHitPoints=" + armorHitPoints
				+ ", repairCost=" + repairCost + ", " + (modifiers != null ? "modifiers=" + modifiers : "") + "]";
	}

	/**
	 * @return the itemType
	 */
	public ItemType getItemType() {
		return itemType;
	}

	/**
	 * @return the itemCurrency
	 */
	public CoinType getItemCurrency() {
		return itemCurrency;
	}

	/**
	 * @return the itemEncumbrance
	 */
	public int getItemEncumbrance() {
		return itemEncumbrance;
	}

}

