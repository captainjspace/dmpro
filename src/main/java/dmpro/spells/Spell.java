package dmpro.spells;
import java.util.ArrayList;
import java.util.List;

import dmpro.utils.Dice;

public class Spell {
	
	public Spell(String spellName) {
		// TODO Auto-generated constructor stub
		this.spellName=spellName;
	}

	public Spell() {
		// TODO Auto-generated constructor stub
	}

	//identifier
	int spellId;
	
	//Class Information
	String spellAbilityClassId;
	String spellAbilityClassName; //needed?
	int spellAbilityClassLevel;
	
	//Spell (name) and errata
	String spellName;
	String spellCategory; //Alteration, Invocation, etc.
	boolean reversible;
	
	//Range
	boolean isRangeTouch;
	boolean isRangeSpecial;
	boolean isRangeInfinite;
	boolean isRangeUnlimited;
	Units rangeUnits;
	float rangeBase;
	float rangeModifier; //per level or what?
	float range = rangeBase+rangeModifier;
	float rangeMax;
	String rangeText;
	
	//Duration
	//duration will be in rounds
	String durationText;
	float durationBase;
	float durationModifier;
	//float durationRounds= durationBase + durationModifier;
	//float durationTurns = durationRounds/10;
	Units durationUnits;
	//Duration duration;
	boolean durationIsPermanent;
	boolean durationIsSpecial;
	boolean durationIsTimeofChanting;
	boolean durationIsUntilFulfilled;
	boolean durationIsInstantaneous;
	boolean durationIsTouch;
	Dice durationDie;
	int durationDieRollMultiplier;
	int durationDieModifier;
	boolean durationHasBonusDie;
	boolean isVariable;
	
	String areaOfEffectText;
	AreaOfEffect areaOfEffect;
	
	//Spell Components
	List<SpellComponent> spellComponent = new ArrayList<SpellComponent>();
	
	//Casting Time
	boolean isCastingTimeSpecial;
	float castingTime; //in segments
	Dice castingTimeDie;
	Units castingTimeUnits;
	float castingTimeModifiers;
	String castingTimeText;
	
	//Saving Throws
	float savingThrowImpact; //0/.5/1
	boolean savingThrowIsSpecial;
	String savingThrowText;
	
	//Description
	String description;
	
	//Printable Text
	String fullSpellText;

	
	public String getSpellName() {
		return spellName;
	}

	@Override
	public String toString() {
		return "Spell [spellId=" + spellId + ", "
				+ (spellAbilityClassId != null ? "spellAbilityClassId=" + spellAbilityClassId + ", " : "")
				+ (spellAbilityClassName != null ? "spellAbilityClassName=" + spellAbilityClassName + ", " : "")
				+ "spellAbilityClassLevel=" + spellAbilityClassLevel + ", "
				+ (spellName != null ? "spellName=" + spellName + ", " : "")
				+ (spellCategory != null ? "spellCategory=" + spellCategory + ", " : "") + "reversible=" + reversible
				+ ", isRangeTouch=" + isRangeTouch + ", isRangeSpecial=" + isRangeSpecial + ", isRangeInfinite="
				+ isRangeInfinite + ", isRangeUnlimited=" + isRangeUnlimited + ", "
				+ (rangeUnits != null ? "rangeUnits=" + rangeUnits + ", " : "") + "rangeBase=" + rangeBase
				+ ", rangeModifier=" + rangeModifier + ", range=" + range + ", rangeMax=" + rangeMax + ", "
				+ (rangeText != null ? "rangeText=" + rangeText + ", " : "")
				+ (durationText != null ? "durationText=" + durationText + ", " : "") + "durationBase=" + durationBase
				+ ", durationModifier=" + durationModifier + ", "
				+ (durationUnits != null ? "durationUnits=" + durationUnits + ", " : "") + "durationIsPermanent="
				+ durationIsPermanent + ", durationIsSpecial=" + durationIsSpecial + ", durationIsTimeofChanting="
				+ durationIsTimeofChanting + ", durationIsUntilFulfilled=" + durationIsUntilFulfilled
				+ ", durationIsInstantaneous=" + durationIsInstantaneous + ", durationIsTouch=" + durationIsTouch + ", "
				+ (durationDie != null ? "durationDie=" + durationDie + ", " : "") + "durationDieRollMultiplier="
				+ durationDieRollMultiplier + ", durationDieModifier=" + durationDieModifier + ", durationHasBonusDie="
				+ durationHasBonusDie + ", isVariable=" + isVariable + ", "
				+ (areaOfEffectText != null ? "areaOfEffectText=" + areaOfEffectText + ", " : "")
				+ (areaOfEffect != null ? "areaOfEffect=" + areaOfEffect + ", " : "")
				+ (spellComponent != null ? "spellComponent=" + spellComponent + ", " : "") + "isCastingTimeSpecial="
				+ isCastingTimeSpecial + ", castingTime=" + castingTime + ", "
				+ (castingTimeDie != null ? "castingTimeDie=" + castingTimeDie + ", " : "")
				+ (castingTimeUnits != null ? "castingTimeUnits=" + castingTimeUnits + ", " : "")
				+ "castingTimeModifiers=" + castingTimeModifiers + ", "
				+ (castingTimeText != null ? "castingTimeText=" + castingTimeText + ", " : "") + "savingThrowImpact="
				+ savingThrowImpact + ", savingThrowIsSpecial=" + savingThrowIsSpecial + ", "
				+ (savingThrowText != null ? "savingThrowText=" + savingThrowText + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (fullSpellText != null ? "fullSpellText=" + fullSpellText : "") + "]";
	}

	public int getSpellId() {
		return spellId;
	}

	public void setSpellId(int spellId) {
		this.spellId = spellId;
	}

	public String getSpellAbilityClassId() {
		return spellAbilityClassId;
	}

	public void setSpellAbilityClassId(String spellAbilityClassId) {
		this.spellAbilityClassId = spellAbilityClassId;
	}

	public String getSpellAbilityClassName() {
		return spellAbilityClassName;
	}

	public void setSpellAbilityClassName(String spellAbilityClassName) {
		this.spellAbilityClassName = spellAbilityClassName;
	}

	public int getSpellAbilityClassLevel() {
		return spellAbilityClassLevel;
	}

	public void setSpellAbilityClassLevel(int spellAbilityClassLevel) {
		this.spellAbilityClassLevel = spellAbilityClassLevel;
	}

	public String getSpellCategory() {
		return spellCategory;
	}

	public void setSpellCategory(String spellCategory) {
		this.spellCategory = spellCategory;
	}

	public boolean isReversible() {
		return reversible;
	}

	public void setReversible(boolean reversible) {
		this.reversible = reversible;
	}

	public boolean isRangeTouch() {
		return isRangeTouch;
	}

	public void setRangeTouch(boolean isRangeTouch) {
		this.isRangeTouch = isRangeTouch;
	}

	public boolean isRangeSpecial() {
		return isRangeSpecial;
	}

	public void setRangeSpecial(boolean isRangeSpecial) {
		this.isRangeSpecial = isRangeSpecial;
	}

	public boolean isRangeInfinite() {
		return isRangeInfinite;
	}

	public void setRangeInfinite(boolean isRangeInfinite) {
		this.isRangeInfinite = isRangeInfinite;
	}

	public boolean isRangeUnlimited() {
		return isRangeUnlimited;
	}

	public void setRangeUnlimited(boolean isRangeUnlimited) {
		this.isRangeUnlimited = isRangeUnlimited;
	}

	public Units getRangeUnits() {
		return rangeUnits;
	}

	public void setRangeUnits(Units rangeUnits) {
		this.rangeUnits = rangeUnits;
	}

	public float getRangeBase() {
		return rangeBase;
	}

	public void setRangeBase(float rangeBase) {
		this.rangeBase = rangeBase;
	}

	public float getRangeModifier() {
		return rangeModifier;
	}

	public void setRangeModifier(float rangeModifier) {
		this.rangeModifier = rangeModifier;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public float getRangeMax() {
		return rangeMax;
	}

	public void setRangeMax(float rangeMax) {
		this.rangeMax = rangeMax;
	}

	public String getRangeText() {
		return rangeText;
	}

	public void setRangeText(String rangeText) {
		this.rangeText = rangeText;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}

	public float getDurationBase() {
		return durationBase;
	}

	public void setDurationBase(float durationBase) {
		this.durationBase = durationBase;
	}

	public float getDurationModifier() {
		return durationModifier;
	}

	public void setDurationModifier(float durationModifier) {
		this.durationModifier = durationModifier;
	}

	public Units getDurationUnits() {
		return durationUnits;
	}

	public void setDurationUnits(Units durationUnits) {
		this.durationUnits = durationUnits;
	}

	public boolean isDurationIsPermanent() {
		return durationIsPermanent;
	}

	public void setDurationIsPermanent(boolean durationIsPermanent) {
		this.durationIsPermanent = durationIsPermanent;
	}

	public boolean isDurationIsSpecial() {
		return durationIsSpecial;
	}

	public void setDurationIsSpecial(boolean durationIsSpecial) {
		this.durationIsSpecial = durationIsSpecial;
	}

	public boolean isDurationIsTimeofChanting() {
		return durationIsTimeofChanting;
	}

	public void setDurationIsTimeofChanting(boolean durationIsTimeofChanting) {
		this.durationIsTimeofChanting = durationIsTimeofChanting;
	}

	public boolean isDurationIsUntilFulfilled() {
		return durationIsUntilFulfilled;
	}

	public void setDurationIsUntilFulfilled(boolean durationIsUntilFulfilled) {
		this.durationIsUntilFulfilled = durationIsUntilFulfilled;
	}

	public boolean isDurationIsInstantaneous() {
		return durationIsInstantaneous;
	}

	public void setDurationIsInstantaneous(boolean durationIsInstantaneous) {
		this.durationIsInstantaneous = durationIsInstantaneous;
	}

	public boolean isDurationIsTouch() {
		return durationIsTouch;
	}

	public void setDurationIsTouch(boolean durationIsTouch) {
		this.durationIsTouch = durationIsTouch;
	}

	public Dice getDurationDie() {
		return durationDie;
	}

	public void setDurationDie(Dice durationDie) {
		this.durationDie = durationDie;
	}

	public int getDurationDieRollMultiplier() {
		return durationDieRollMultiplier;
	}

	public void setDurationDieRollMultiplier(int durationDieRollMultiplier) {
		this.durationDieRollMultiplier = durationDieRollMultiplier;
	}

	public int getDurationDieModifier() {
		return durationDieModifier;
	}

	public void setDurationDieModifier(int durationDieModifier) {
		this.durationDieModifier = durationDieModifier;
	}

	public boolean isDurationHasBonusDie() {
		return durationHasBonusDie;
	}

	public void setDurationHasBonusDie(boolean durationHasBonusDie) {
		this.durationHasBonusDie = durationHasBonusDie;
	}

	public boolean isVariable() {
		return isVariable;
	}

	public void setVariable(boolean isVariable) {
		this.isVariable = isVariable;
	}

	public String getAreaOfEffectText() {
		return areaOfEffectText;
	}

	public void setAreaOfEffectText(String areaOfEffectText) {
		this.areaOfEffectText = areaOfEffectText;
	}

	public AreaOfEffect getAreaOfEffect() {
		return areaOfEffect;
	}

	public void setAreaOfEffect(AreaOfEffect areaOfEffect) {
		this.areaOfEffect = areaOfEffect;
	}

	public List<SpellComponent> getSpellComponent() {
		return spellComponent;
	}

	public void setSpellComponent(List<SpellComponent> spellComponent) {
		this.spellComponent = spellComponent;
	}

	public boolean isCastingTimeSpecial() {
		return isCastingTimeSpecial;
	}

	public void setCastingTimeSpecial(boolean isCastingTimeSpecial) {
		this.isCastingTimeSpecial = isCastingTimeSpecial;
	}

	public float getCastingTime() {
		return castingTime;
	}

	public void setCastingTime(float castingTime) {
		this.castingTime = castingTime;
	}

	public Dice getCastingTimeDie() {
		return castingTimeDie;
	}

	public void setCastingTimeDie(Dice castingTimeDie) {
		this.castingTimeDie = castingTimeDie;
	}

	public Units getCastingTimeUnits() {
		return castingTimeUnits;
	}

	public void setCastingTimeUnits(Units castingTimeUnits) {
		this.castingTimeUnits = castingTimeUnits;
	}

	public float getCastingTimeModifiers() {
		return castingTimeModifiers;
	}

	public void setCastingTimeModifiers(float castingTimeModifiers) {
		this.castingTimeModifiers = castingTimeModifiers;
	}

	public String getCastingTimeText() {
		return castingTimeText;
	}

	public void setCastingTimeText(String castingTimeText) {
		this.castingTimeText = castingTimeText;
	}

	public float getSavingThrowImpact() {
		return savingThrowImpact;
	}

	public void setSavingThrowImpact(float savingThrowImpact) {
		this.savingThrowImpact = savingThrowImpact;
	}

	public boolean isSavingThrowIsSpecial() {
		return savingThrowIsSpecial;
	}

	public void setSavingThrowIsSpecial(boolean savingThrowIsSpecial) {
		this.savingThrowIsSpecial = savingThrowIsSpecial;
	}

	public String getSavingThrowText() {
		return savingThrowText;
	}

	public void setSavingThrowText(String savingThrowText) {
		this.savingThrowText = savingThrowText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFullSpellText() {
		return fullSpellText;
	}

	public void setFullSpellText(String fullSpellText) {
		this.fullSpellText = fullSpellText;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}
	
	
}
