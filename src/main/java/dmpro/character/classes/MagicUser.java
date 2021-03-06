package dmpro.character.classes;

import dmpro.attributes.Attribute.AttributeType;
import dmpro.data.loaders.SpellsAllowedRecord;
import dmpro.data.loaders.SpellsAllowedTableLoader;
import dmpro.items.DamageRoll;
import dmpro.modifier.AttributeModifier;
import dmpro.attributes.Dexterity;

import java.util.ArrayList;
import java.util.List;

import dmpro.attributes.Strength;
import dmpro.spells.Spell;
import dmpro.utils.Dice;
import dmpro.utils.Die;

public class MagicUser extends CharacterClass implements SpellCaster {

	//bonus exp by 10% if Intelligence is > 16
	//minimum intelligence >=9
	//minimum dexterity >= 6
	//hit die 4d
	//restrictions: no armor, no shield
	//weapon list: caltrop,dagger,dart,knife,sling,staff
	
	SpellsAllowedRecord spellsAllowed; 
	List<Spell> spellBook = new ArrayList<Spell>();
	List<Spell> dailySpells = new ArrayList<Spell>();
	
	//should ref to character come in constructor
	public MagicUser() {
		
		this.characterClassType=CharacterClassType.MAGICUSER;
		this.className="Magic-User";
		this.hasSpells = true;
		this.setHitDie(new Die(Dice.d4));
		this.setMaxHitDice(11);
		this.setHitPointPerLevelAfterMax(1);
		this.setExperiencePointsPerLevelAfterMax(375000);
		this.addXPBonus(new XPBonus("intelligence", 16));
		this.setInitialGold(( new DamageRoll("2d4").getDamageRoll() * 10 ));

		this.setStartingProficiencies(1);
		this.setNewProficienyPerLevel(6);
		this.setNonProficiencyPenalty(-5);

		AttributeModifier am = new AttributeModifier();
		am.setAttributeToModify(Strength.class);
		am.setAttributeType(AttributeType.STRENGTH);
		am.setBonus(this.getNonProficiencyPenalty());
		am.setModifiesAbilityScore(false);
		am.setDescription("Magic-User non proficiency toHit penalty (" + this.nonProficiencyPenalty + ")");
		classAttribute.add(am);
		
	}
	public void init() {
		
	}
	
	public void addToSpellBook(Spell spell) {
		spellBook.add(spell);
	}
	//TODO:  Add spellsAllowed check to make sure the correct # of daily spells are available.
	// map might be better for removal.
	public boolean addToDailySpells(Spell spell) {
		if (spellBook.stream()
				.anyMatch(p -> p.getSpellName().equals(spell.getSpellName()))) {
				spellBook.add(spell);
				return true;
		}
		else return false;
	}
    public SpellsAllowedRecord getSpellsAllowed() {
    	return spellsAllowed;
    }
    public void setSpellsAllowedRecord(SpellsAllowedRecord spellsAllowedRecord) {
    	spellsAllowed = spellsAllowedRecord;
    }
//	public void processExperience(int i) {
//		System.out.println("PROCESS EXPERIENCE AS MAGIC USER");
//		super.processExperience(i);
//		processSpells();
//	}
//    public void processSpells() {
//    	System.out.println("PROCESS SPELLS");
//    	SpellsAllowedTableLoader sat = new SpellsAllowedTableLoader(this.getClassName());
//    	this.spellsAllowed = sat.getRecord(this.getExperienceLevel());
//    }
	/**
	 * @return the spellsAllowed
	 */
//	public SpellsAllowedRecord getSpellsAllowed() {
//		return spellsAllowed;
//	}
	/**
	 * @param spellsAllowed the spellsAllowed to set
	 */
//	public void setSpellsAllowed(SpellsAllowedRecord spellsAllowed) {
//		this.spellsAllowed = spellsAllowed;
//	}
//    
}
