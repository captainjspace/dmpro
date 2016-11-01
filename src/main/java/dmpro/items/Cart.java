/**
 * 
 */
package dmpro.items;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 31, 2016
 * Shopping cart of items
 */
public class Cart {
	List<Item> items = new ArrayList<Item>();

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}
	

}
