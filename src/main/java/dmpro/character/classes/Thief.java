package dmpro.character.classes;

import java.util.ArrayList;
import java.util.List;

import dmpro.Ability.AbilityType;
import dmpro.attributes.Strength;
import dmpro.attributes.Attribute.AttributeType;
import dmpro.character.Language;
import dmpro.data.loaders.ThiefAbilityRecord;
import dmpro.data.loaders.ThiefAbilityTableLoader;
import dmpro.items.DamageRoll;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;
import dmpro.utils.Dice;
import dmpro.utils.Die;

public class Thief extends CharacterClass {
	/* Restrictions */
	//min dex 9
	//dex > 15 + 10%xp
	//Alignment neutral or evil
	
	/* thief abilities:  */
	//pick pockets, opening locks, finding/removing traps
	//move silently, hiding in shadows, listening at doors
	//ascending/descending vertical surfaces
	
	/* BackStab */
	//backstabbing - multiply damage by n++ for every (floor(level/4) + 1)
	//backstabbing +4 to Hit
	//language: thieves cant
	
	
	//4th level read languages ( 20% + ( 5% * (level-4) ) ) -- max 16th level at 80%
	//allowed small castle or tower within a mile of city
	//after 10th level castle may be a headquarters for gang of thieves (4-24)
	//2 hp per level level > 10
	//pick pockets modifier - ( 5% * (target level -3 ) )
	//opening locks - 1 chance per level
	//traps - 1 try per thief
	//hearing noise - casting time = 1 round
	//reading language - 1 chance per level
	
	//should these be parsed to individual abilities?
	//ThiefAbilityRecord thiefAbilityRecord;
	List<Language> languages = new ArrayList<Language>();
	
	public Thief() {
		
		this.setCharacterClassType(CharacterClassType.THIEF);
		this.className="Thief";
		this.setHitDie(new Die(Dice.d6));
		this.setMaxHitDice(10);
		this.setHitPointPerLevelAfterMax(2);
		this.setExperiencePointsPerLevelAfterMax(220000);
		this.addXPBonus(new XPBonus("dexterity", 15));
		this.setInitialGold(( new DamageRoll("2d6").getDamageRoll() * 10 ));
		
		this.setStartingProficiencies(2);
		this.setNewProficienyPerLevel(4);
		this.setNonProficiencyPenalty(-3);
		this.languages.add(new Language("Thieves' Cant"));
		
		//repeated move to character
		AttributeModifier am = new AttributeModifier();
		am.setAttributeToModify(Strength.class);
		am.setBonus(this.getNonProficiencyPenalty());
		am.setAttributeType(AttributeType.STRENGTH);
		am.setModifiesAbilityScore(false);
		am.setDescription("Thief non proficiency toHit penalty (" + this.nonProficiencyPenalty + ")");
		classAttribute.add(am);
		
		am = new AttributeModifier();
		am.setAttributeToModify(Strength.class);
		am.setAttributeType(AttributeType.STRENGTH);
		am.setBonus(4);
		am.setModifiesAbilityScore(false);
		am.setDescription("Thief +4 toHit on backstab");
		classAttribute.add(am);
		
		AbilityModifier abilityModifier= new AbilityModifier();
		abilityModifier.setModifier(Math.floorDiv(this.experienceLevel,4) + 2);
		abilityModifier.setPermanent(true);
		abilityModifier.getAbility().setAbilityName("Thief BackStab!");
		abilityModifier.getAbility().setAbilityType(AbilityType.THIEF);
		abilityModifier.getAbility().setDescription("Backstab Damage Multiplier");
		abilityModifier.setDescription("Thief Backstab Damage Multipler (floor(level/4) + 2) ");
		classAbilities.add(abilityModifier);
		
	}

//	void processThiefAbility() {
//		ThiefAbilityTableLoader tat = new ThiefAbilityTableLoader();
//		thiefAbilityRecord = tat.getRecord(this.getExperienceLevel());
//	}
//
//	/**
//	 * @return the thiefAbilityRecord
//	 */
//	public ThiefAbilityRecord getThiefAbilityRecord() {
//		return thiefAbilityRecord;
//	}
//
//	/**
//	 * @param thiefAbilityRecord the thiefAbilityRecord to set
//	 */
//	public void setThiefAbilityRecord(ThiefAbilityRecord thiefAbilityRecord) {
//		this.thiefAbilityRecord = thiefAbilityRecord;
//	}
}
