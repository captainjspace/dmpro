/**
 * 
 */
package dmpro.data.loaders;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Nov 10, 2016
 */
public class RaceThiefAbilityLoader extends TSVLoader implements ResourceLoader {

	public RaceThiefAbilityLoader () {
		super(RaceThiefAbilityRecord.class, "race-thief-abilities.tsv");
	}
	
	public static void main (String[] args) {
		RaceThiefAbilityLoader rat = new RaceThiefAbilityLoader();
		System.out.println(rat.getWebTable());
	}
}
