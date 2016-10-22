/**
 * 
 */
package dmpro.items;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 19, 2016
 */
public class EmptyItem extends Item {
	public EmptyItem() {
		this.itemType = ItemType.EMPTY;
		this.itemName = "Empty";
		this.description =" Empty Item Slot";
	}

}
