package dmpro.character;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

import dmpro.utils.Dice;
import dmpro.utils.Die;

/**
 * AttributeRoller.java
 * The class uses the die class and sets up a 6 sided die for best 3 our of 4 rolls
 * It also sets up a simple int array for 6 values to be allocated by the PC.
 * NPC can allocate in order.
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */

public class AttributeRoller extends Die {
	public AttributeRoller() {
		setDieType(Dice.d6);
	}

	/**
	 * Simple implementation of 3 best of 4 roll for character generation.
	 * no viable reason to custom sort here...
	 * @return int
	 */
	public int attributeRoll() {
		int attRoll=0;
		int[] rolls = { roll(), roll(), roll(), roll() };
		java.util.Arrays.sort(rolls);
//		for (int i=0;i<=rolls.length-1;i++) 
//			System.out.format("-%d-", rolls[i]);
		for (int i=1;i<=3;i++) {
			attRoll+=rolls[i];
		}
		return attRoll;
	}
	/** 
	 * attributeRolls returns an array of 6 attribute rolls for the user to assign
	 * @return int[]
	 */
	public int[] attributeRolls() {

		int[] attributeRolls = { attributeRoll(), attributeRoll(), attributeRoll(), attributeRoll(), 
				attributeRoll(), attributeRoll() , attributeRoll() };
		//eliminate lowest
		//for (int i=0;i<=attributeRolls.length -1; i++) 
		//	System.out.format("\t%d\t", attributeRolls[i]);
		java.util.Arrays.sort(attributeRolls);
		int[] copyRolls = new int[6];
		System.arraycopy(attributeRolls, 1, copyRolls, 0, 6);
		attributeRolls = copyRolls;
		//reshuffle the rest
		
		for (int i = attributeRolls.length-1; i>1 ; i--) {
			int shuffleIndex = (int) Math.round(Math.random() * i);
			int attribute = attributeRolls[shuffleIndex];
			attributeRolls[shuffleIndex] = attributeRolls[i];
			attributeRolls[i] = attribute;
		}
		return attributeRolls;
	}

	public static void main (String[] args) {
		AttributeRoller attributeRoller = new AttributeRoller();
		int[] attributeRolls;
		List<Integer> sums = new ArrayList<Integer>();
		for (int t=0; t <5; t++)  {
			attributeRolls = attributeRoller.attributeRolls();
			int sum=0;
			for (int i = 0; i<=attributeRolls.length-1;i++) {
				sum+=attributeRolls[i];
				System.out.format("\n-- %d --", attributeRolls[i]);
			}
			sums.add(sum);
			System.out.format("Sum: %d\tAvg: %d\n", sum, sum/6);
		}
		IntSummaryStatistics stats = sums.stream()
				.mapToInt((x) -> x)
				.summaryStatistics();
		System.out.println(stats);
	}
}
