package dmpro.data.loaders;



import dmpro.items.CoinItem.CoinType;
import dmpro.items.Item;
//import dmpro.items.Item.ItemType;
import dmpro.modifier.ArmorClassModifier;
import dmpro.modifier.ArmorClassModifier.ArmorClassModifierType;
//import dmpro.modifier.Modifier;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;

public class ArmorRecord extends Item implements TSVData {

	static final int fieldCount = 12;
	//Type	Item	Cost	Currency	Weight	Base Movement	Fixed AC	Modified AC	# of Attacks	Damage Absorb	HP	Repair Cost
	//private ItemType itemType = ItemType.ARMOR;
	private ArmorType armorType;
	//private String itemName;
	//private int itemValue;
	//private CoinType itemCurrency = CoinType.GOLD;
	//private int itemEncumbrance;
	private int armorBaseMovement;
	private int armorClass;
	private int acModifier;
	private int numAttacksDefended;
	private int damageAbsorbedPerDie;
	private int armorHitPoints;
	private int repairCost;
	//private List<ArmorClassModifier> armorClassmodifiers = new ArrayList<ArmorClassModifier>();

	public ArmorRecord(String [] fields) {
		itemType = ItemType.ARMOR;
		armorType = ArmorType.valueOf(fields[0]); //distinguish between ARMOR, SHIELD
		setItemName(fields[1]);
		setItemValue(Integer.parseInt(fields[2]));
		itemCurrency= CoinType.valueOf(fields[3]);
		setItemEncumbrance(Integer.parseInt(fields[4]));
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
	 * @return the armorEncumbrance
	 */
	public int getArmorEncumbrance() {
		return getItemEncumbrance();
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
		return "ArmorRecord [" + (getItemType() != null ? "itemType=" + getItemType() + ", " : "")
				+ (armorType != null ? "armorType=" + armorType + ", " : "")
				+ (getItemName() != null ? "itemName=" + getItemName() + ", " : "") + "itemValue=" + getItemValue() + ", "
				+ (itemCurrency != null ? "itemCurrency=" + itemCurrency + ", " : "") + "itemEncumbrance="
				+ getItemEncumbrance() + ", armorBaseMovement=" + armorBaseMovement + ", armorClass=" + armorClass
				+ ", acModifier=" + acModifier + ", numAttacksDefended=" + numAttacksDefended
				+ ", damageAbsorbedPerDie=" + damageAbsorbedPerDie + ", armorHitPoints=" + armorHitPoints
				+ ", repairCost=" + repairCost + ", " + (modifiers != null ? "modifiers=" + modifiers : "") + "]";
	}

}

