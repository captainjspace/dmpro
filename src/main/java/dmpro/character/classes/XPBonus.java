package dmpro.character.classes;
/**
 * simple container for xp bonus
 * @author joshualandman
 *
 */
public class XPBonus {
	private String attributeName;
	private int minVal;
	
	public XPBonus(String attributeName, int minVal) {
		this.attributeName = attributeName;
		this.minVal = minVal;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public int getMinVal() {
		return minVal;
	}
	
}