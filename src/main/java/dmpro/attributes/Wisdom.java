package dmpro.attributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dmpro.spells.Spell;

public class Wisdom extends Attribute {

	static final int fieldCount = 5;

	
	// beguiling, charmming, fear, hypnosis, illusion,magic jar
	// mass charm, phanstasmal force, possession, rulership, suggestion, telepathic attack, etc.
	// general mental attack saving throw modifier
	int magicalAttackAdustment; 
	float percentSpellFailure;
	List<Integer> bonusSpells = new ArrayList<Integer>(); //cumulative above 13
	List<Spell> spellImmunity=new ArrayList<Spell>();

	public Wisdom(){}
	public Wisdom(String[] fields) {
		attributeName = "Wisdom";
		abilityScore=Integer.parseInt(fields[0].trim());
		magicalAttackAdustment = Integer.parseInt(fields[1]);
		setBonusSpells(fields[2]);
		setPercentSpellFailure(fields[3]);
		setSpellImmunity(fields[4]);

	}
	/**
	 * @return the magicalAttackAdustment
	 */
	public int getMagicalAttackAdustment() {
		return magicalAttackAdustment;
	}
	/**
	 * @param magicalAttackAdustment the magicalAttackAdustment to set
	 */
	public void setMagicalAttackAdustment(int magicalAttackAdustment) {
		this.magicalAttackAdustment = magicalAttackAdustment;
	}
	/**
	 * @return the percentSpellFailure
	 */
	public float getPercentSpellFailure() {
		return percentSpellFailure;
	}
	/**
	 * @param percentSpellFailure the percentSpellFailure to set
	 */
	public void setPercentSpellFailure(float percentSpellFailure) {
		this.percentSpellFailure = percentSpellFailure;
	}
	/**
	 * @return the bonusSpells
	 */
	public List<Integer> getBonusSpells() {
		return bonusSpells;
	}
	/**
	 * @param bonusSpells the bonusSpells to set
	 */
	public void setBonusSpells(List<Integer> bonusSpells) {
		this.bonusSpells = bonusSpells;
	}
	/**
	 * @return the spellImmunity
	 */
	public List<Spell> getSpellImmunity() {
		return spellImmunity;
	}
	/**
	 * @param spellImmunity the spellImmunity to set
	 */
	public void setSpellImmunity(List<Spell> spellImmunity) {
		this.spellImmunity = spellImmunity;
	}
	/**
	 * @return the fieldcount
	 */
	public static int getFieldcount() {
		return fieldCount;
	}
	private void setPercentSpellFailure(String string) {
		
		if (!string.matches(".*\\d+.*") )
			return;
		percentSpellFailure = Float.parseFloat(string);
		
	}
	private void setSpellImmunity(String spells) {
		
		if (spells.matches(".*--.*") )
			return;
		for (String s : spells.split(",")) {
			spellImmunity.add(new Spell(s.trim()));
		}

	}
	private void setBonusSpells(String levels) {
		// TODO Auto-generated method stub
		if (!levels.matches(".*\\d+.*") )
				return;
		
		//System.out.println(levels);
		//bonusSpells.add( Arrays.asList(levels.replaceAll("[","").replaceAll("]","").split(",")) );
		List<String> l = Pattern.compile(",")
		  .splitAsStream(levels.replaceAll("\\[","").replace("]","").trim())
		  .collect(Collectors.toList() );
		
		//System.out.println("done");
		bonusSpells.addAll(l.stream().map(Integer::parseInt).collect(Collectors.toList()));
//		bonusSpells.stream().forEach(a -> System.out.println(a));
		
		
	}
	@Override
	public String toString() {
		return "Wisdom [abilityScore=" + abilityScore + ", magicalAttackAdustment=" + magicalAttackAdustment
				+ ", percentSpellFailure=" + percentSpellFailure + ", bonusSpells=" + bonusSpells + ", spellImmunity="
				+ spellImmunity + "]";
	}





}
