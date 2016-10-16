/**
 * 
 */
package dmpro.character;

import java.util.HashMap;
import java.util.Map;

import dmpro.items.Item;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 13, 2016
 * 
 * Defines items that have a max equipped capacity
 * 
 * TODO:  Put this to use.  Some items cannot be 
 * 
 */
public class SlottedItems {
	
	public enum EquipmentSlotKey {
		MAINHAND("Primary Hand Weapon"),
		OTHERHAND("Secondary Hand Weapon or Shield"),
		HEADGEAR("Helm, Circlet, Crown"),
		EARRINGS("Magic Earrings"),
		ARMOR("Armor or Robes"),
		OUTERWEAR("Cloaks"),
		FOOTWEAR("Boots, Slippers"),
		GAUNTLETS("Gloves or Gauntlets"),
		ANKLETS("Magic Anklets"),
		BRACELETS("Magic Bracelets or Bracers"),
		MAGICRINGMAINHAND("Magic Ring Primary Hand"),
		MAGICRINGOTHERHAND("Magic Ring Secondary Hand"),
		NECKLACE("Magic Necklace or Brooch");
		
		String slotDescription;
		
		private EquipmentSlotKey(String slotDescription) {
			this.slotDescription = slotDescription;
		}
		
		public String slotDescription() {
			return this.slotDescription;
		}
	}
	
	public Map<EquipmentSlotKey, Item> slots = new HashMap<EquipmentSlotKey, Item>();
	
}
