package dmpro.character.race;
import java.util.ArrayList;
import java.util.List;

import dmpro.character.Language;
import dmpro.modifier.AbilityModifier;
import dmpro.modifier.AttributeModifier;

public class Race {
	
	RaceType raceType;
	//List<RaceAbility> raceAbilities = new ArrayList<RaceAbility>();
	List<AbilityModifier> raceAbilities = new ArrayList<AbilityModifier>();
	List<AttributeModifier> raceAttributes = new ArrayList<AttributeModifier>();
	List<Language> languages = new ArrayList<Language>();
	
	public RaceType getRaceType() {
		return raceType;
	}
	public void setRaceType(RaceType race) {
		this.raceType = race;
	}
	public List<AbilityModifier> getRaceAbilities() {
		return raceAbilities;
	}
	public void setRaceAbilities(List<AbilityModifier> raceAbilities) {
		this.raceAbilities = raceAbilities;
	}
	public List<AttributeModifier> getRaceAttributes() {
		return raceAttributes;
	}
	public void setRaceAttributes(List<AttributeModifier> raceAttributes) {
		this.raceAttributes = raceAttributes;
	}
	/**
	 * @return the languages
	 */
	public List<Language> getLanguages() {
		return languages;
	}
	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	
}

