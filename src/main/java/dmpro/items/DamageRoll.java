package dmpro.items;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmpro.utils.Dice;
import dmpro.utils.Die;

public class DamageRoll {


	private final static String dicePattern = "(\\d+)?(d\\d+)\\s*(([\\Q+\\E|-])?\\s*(\\d+))?";
	//	private final static String dicePattern = ".*([\\Q+\\E|-]).*";
	private final static Pattern pattern = Pattern.compile(dicePattern);
	private Matcher matcher;
	private Die die;
	private int rollCount;
	private int modifier;

	private int multiplier=1; //backstab, critical hit

	private int damage;

	public DamageRoll() {} ;

	public DamageRoll(String expression){
		//parseDamage

		matcher = DamageRoll.pattern.matcher(expression);
		if (!matcher.find()) {
			//System.out.println("Matcher no findy"); 
			return;
		} else { 
			//System.out.println("MATCHED!");
		}

		for (int i=1; i<=matcher.groupCount();i++) {
			//			System.out.printf("Group:%d: %s\n",i, matcher.group(i));
		}
		die = new Die(Dice.valueOf(matcher.group(2)));
		this.rollCount= 
				( matcher.group(1) != null) ? Integer.parseInt(matcher.group(1)): 1;
				if ( matcher.group(3) != null ) {
					this.modifier =Integer.parseInt(matcher.group(5)) * Integer.parseInt(matcher.group(4) + 1);
					//			System.out.printf("Modifier: %d\n", modifier);
				}

	}

	public DamageRoll(int rollCount, Die die, int modifier) {
		this.die=die;
		this.rollCount=rollCount;
		this.modifier=modifier;
	}

	public int getDamageRoll() {
		while (this.rollCount-- > 0 ) {
			damage+=die.roll();
//			System.out.printf("DAMAGE LOOP %d\n", damage);
		}
		damage += modifier;
//		System.out.printf("DAMAGE POST MODIFIER %d:\n", damage);
		damage*=multiplier;
//		System.out.printf("DAMAGE POST MULTIPLIER %d:\n", damage);
		return damage;
	}

	public static void main (String[] args) {
		System.out.println("----------------- DAMAGE ROLLER ------------------");
		Scanner scan = new Scanner(System.in);
		DamageRoll damageRoll; 
		String input;
		while(true) {
			System.out.println("Enter dice pattern <2d4 +1, etc>");
			input = scan.nextLine();
			if (input.equals(".exit."))  break; 
			damageRoll = new DamageRoll(input);
			System.out.println(damageRoll.getDamageRoll());
		}
		scan.close();
	}

}
