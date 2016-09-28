package dmpro.core;
import java.util.Formatter;
/**
 * Cheap start to a command pattern.
 * 
 * I figured it would be easy enough to break up later.
 * 
 * @author joshualandman
 */
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dmpro.core.CommandInterpreter.CommandObject;
import dmpro.core.CommandInterpreter.CommandSet;
import dmpro.core.CommandInterpreter.DataSet;
import dmpro.core.CommandInterpreter.Subsystem;
import dmpro.items.DamageRoll;

public class Commander implements Runnable {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Application application;
	private Scanner input;
	private Formatter output;
	
	private GsonBuilder builder = new GsonBuilder();
	private Gson gson;
	
	public Commander(Application application, Scanner input, Formatter output) {
		this.application = application;
		//these are here to reshape create character...
		this.input = input;
		this.output = output;
		builder.setPrettyPrinting();
		gson = builder.create();
	}
	
	public void run() {
		
	}
	
	public String execute(CommandObject commandObject) {
		String results = null;
		if (commandObject.commandSet == CommandSet.HELP) {
			return DungeonMasterProHandler.ascii.art.get("help");
		}
		switch (commandObject.subsystem) {
		case CHARACTER:
			switch (commandObject.commandSet) {
			case CREATE:
				application.getCharacterService().getCharacterBuildDirector().setInput(input);
				application.getCharacterService().getCharacterBuildDirector().setOutput(output);
				application.getCharacterService().createCharacter();
				break;
			}
			break;
		case DICE:
			dmpro.items.DamageRoll damageRoll = new DamageRoll(commandObject.varString);
			return "" + damageRoll.getDamageRoll();
		case DATA:
			switch (commandObject.dataSet) {
			case COMBAT:
				switch (commandObject.commandSet) {
				case GET:
					dmpro.CombatRecord combatRecord = application.getReferenceDataSet().getCombatTableLoader().getRecord(commandObject.varString, commandObject.varInt);
					StringBuilder combat = new StringBuilder();
					combatRecord.getArmorClass().entrySet().stream().sorted(Map.Entry.comparingByKey())
					.forEach(a -> combat.append("AC:").append(a.getKey()).append(" To Hit:").append(a.getValue()).append('\n'));
					results = combat.toString();
					break;
				case SEARCH:
					//implement me
					break;
				}
				break;
			case EXPERIENCE:
				dmpro.ExperienceTableRecord experienceTableRecord = application.getReferenceDataSet().getExperienceTableLoader().getRecord(commandObject.varString, commandObject.varInt );
				results = gson.toJson(experienceTableRecord);
				break;
			case SAVINGTHROW:
				dmpro.SavingThrowRecord savingThrowRecord = application.getReferenceDataSet().getSavingThrowTableLoader().getRecord(commandObject.varString, commandObject.varInt);
				results = gson.toJson(savingThrowRecord);
				break;
			case THIEFABILITY:
				dmpro.ThiefAbilityRecord thiefAbilityRecord = application.getReferenceDataSet().getThiefAbilityTableLoader().getRecord(commandObject.varInt);
				results = gson.toJson(thiefAbilityRecord);
				break;
			case SPELLS:
				switch (commandObject.commandSet) {
				case GET:
					dmpro.spells.Spell spell = application.getReferenceDataSet().getSpellLibrary().getSpell(commandObject.varString);
					results = spell.getFullSpellText();
					break;
				case SEARCH:
					List<dmpro.spells.Spell> spells = application.getReferenceDataSet().getSpellLibrary().searchSpell(commandObject.varString);
					results = spells.stream().map(w -> w.getSpellName() 
							+ "\t" 
							+ w.getSpellAbilityClassName() 
							+ ":" +  w.getSpellAbilityClassLevel())
							.collect(Collectors.joining("\n"));
					break;
				}
				break;
			case MAGIC:
				//logger.log(Level.INFO, "trying to get " + commandObject.varString);
				dmpro.items.MagicItem magicItem = application.getReferenceDataSet().getMagicItemLoader().getMagicItem(commandObject.varString);
				results = gson.toJson(magicItem);
				break;
			case WEAPONS:
				switch(commandObject.commandSet) {
				case GET:
					dmpro.items.WeaponItem weaponItem = application.getReferenceDataSet().getWeaponItemLoader().getWeaponItem(commandObject.varString);
					results = gson.toJson(weaponItem);
					break;
				case SEARCH:
					List<dmpro.items.WeaponItem> weapons = application.getReferenceDataSet().getWeaponItemLoader().searchWeaponItem(commandObject.varString);
					results = weapons.stream().map(w -> w.getItemName()).collect(Collectors.joining("\n"));
					break;
				case LIST:
					weapons = application.getReferenceDataSet().getWeaponItemLoader().getWeapons();
					StringBuilder sb = new StringBuilder();
			    	weapons.stream().forEach(w -> sb.append(w.getItemName()).append('\t'));
			    	results = sb.toString();
					break;
			    }
				break;
			case SPELLTABLES:
				dmpro.SpellsAllowedRecord spellsAllowedRecord = application.getReferenceDataSet().getSpellsAllowedTableLoader().getRecord(commandObject.varString, commandObject.varInt );
				results = gson.toJson(spellsAllowedRecord);
				break;
			case ATTRIBUTES:
				dmpro.attributes.Attribute.AttributeType attributeType = application.getReferenceDataSet().getAttributeLoader().getAttributeType(commandObject.varString);
				logger.log(Level.INFO, "ATTRIBUTE:" + attributeType.toString());
				dmpro.attributes.Attribute attribute = null;
				switch (attributeType) {
				case STRENGTH:
					attribute = application.getReferenceDataSet().getStrengthLoader().getRecord(commandObject.varInt);
					break;
				case INTELLIGENCE:
					attribute = application.getReferenceDataSet().getIntelligenceLoader().getRecord(commandObject.varInt);
					break;
				case WISDOM:
					attribute = application.getReferenceDataSet().getWisdomLoader().getRecord(commandObject.varInt);
					break;
				case DEXTERITY:
					attribute = application.getReferenceDataSet().getDexterityLoader().getRecord(commandObject.varInt);	
					break;
				case CONSTITUTION:
					attribute = application.getReferenceDataSet().getConstitutionLoader().getRecord(commandObject.varInt);
					break;
				case CHARISMA:
					attribute = application.getReferenceDataSet().getCharismaLoader().getRecord(commandObject.varInt);
					break;
				}
				results = gson.toJson(attribute);
				break;
			default:
				results = " hmmmm not implemented yet ";
				break;
			}
		}
		
		return results;
		
	}

}
