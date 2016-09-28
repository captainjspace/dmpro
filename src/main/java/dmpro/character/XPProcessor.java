package dmpro.character;

import java.util.logging.Level;
import java.util.logging.Logger;

//import dmpro.CombatTableLoader;
//import dmpro.ExperienceTableLoader;
import dmpro.ExperienceTableRecord;
//import dmpro.SavingThrowLoader;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.HitDieRecord;
import dmpro.character.classes.XPBonus;
import dmpro.character.classes.CharacterClass.CharacterClassType;
import dmpro.core.Server;

public class XPProcessor {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Server application;
	private Character character;
	
	public XPProcessor(Server application) {
		this.application = application;
	}
	
	public Character getCharacter() {
		return this.character;
	}
	
	public Character evaluateExperience(Character character) {
		this.character = character;
		addExperiencePoints(0);
		return character;
	}
	
	/**
	 * handle evaluations of xp bonus based on class attributes
	 * handle distribution to multi-class
	 * TODO:	add in non-distribution for human 2 class -- pretty rare...
	 * 			probably an isActive..
	 * 
	 * @param newXPpoints
	 */
	public void addExperiencePoints(int newXPpoints) {

		//support multiclass
		int classCount = character.getClasses().size();
		int xpToAdd = newXPpoints/classCount;

		//evaluate 10% bonus for each class.
		//add to xpToAdd
		//shorten this code
		for (CharacterClass characterClass : character.getClasses().values()) {
			float bonusMultiplier = 1;
			for (XPBonus xpBonus : characterClass.getXPBonusRequirements()) {
				Integer attributeValue = character.getAttributesAsMap().get(xpBonus.getAttributeName().toLowerCase()).getModifiedAbilityScore();
				if ( Integer.valueOf(attributeValue) > xpBonus.getMinVal())
					bonusMultiplier = (float)1.1;
				else { 
					bonusMultiplier = (float)1.0; 
					break; //end as soon as we don't have it
				}
			}

			xpToAdd = Math.round(xpToAdd * bonusMultiplier);
			characterClass.setExperiencePoints(characterClass.getExperiencePoints() + xpToAdd);
			processExperienceLevel(characterClass);
			//do i need to put here?
		}
		
		processCharacterHitPoints();
	}
	
	public void processExperienceLevel(CharacterClass characterClass) {
		int currentLevel  = characterClass.getExperienceLevel();
		ExperienceTableRecord experienceTableRecord = application.getReferenceDataSet().getExperienceTableLoader().getRecordByXP(characterClass.getExperiencePoints());
	
		//could implement the max 1 level increase and set XP right here.
		if (currentLevel < experienceTableRecord.getExperienceLevel()) {
			logger.log(Level.INFO,  "setting experience level to " + experienceTableRecord.getExperienceLevel());
			characterClass.setExperienceLevel(experienceTableRecord.getExperienceLevel());
		
			/* process level increase */
			processClassHitPoints(characterClass);
			processClassCombat(characterClass);
			processClassSavingThrows(characterClass);
			queueClassSpecificActions(character, characterClass);
		} else {
			logger.log(Level.INFO,  "no level change");
		}
	}

	private void queueClassSpecificActions(Character character, CharacterClass characterClass) {
		if  (
				(characterClass.getCharacterClassType() == CharacterClassType.MAGICUSER) ||
				(characterClass.getCharacterClassType() == CharacterClassType.ILLUSIONIST)  ||
				(characterClass.getCharacterClassType() == CharacterClassType.CLERIC) ||
				(characterClass.getCharacterClassType() == CharacterClassType.DRUID) || 
				(characterClass.getCharacterClassType() == CharacterClassType.RANGER) ||
				(characterClass.getCharacterClassType() == CharacterClassType.PALADIN)
			) {
			character.requiredActions.add(CharacterManagementActions.UPDATESPELLSALLOWED);
			character.requiredActions.add(CharacterManagementActions.UPDATESPELLBOOK);
			character.requiredActions.add(CharacterManagementActions.UPDATEDAILYSPELLS);
		}

		if ( 
				(characterClass.getCharacterClassType() == CharacterClassType.CLERIC) ||
				(characterClass.getCharacterClassType() == CharacterClassType.PALADIN)
			 ) {
			character.requiredActions.add(CharacterManagementActions.UPDATETURNUNDEAD);
		}
		
		if (characterClass.getCharacterClassType() == CharacterClassType.THIEF)
			character.requiredActions.add(CharacterManagementActions.UPDATETHIEFSKILLS);
			
	}

	
	
	public void processClassHitPoints(CharacterClass characterClass) {
		logger.log(Level.INFO,  "Class Hit Point Check");
		int hpBonus = 0;
		switch (characterClass.getCharacterClassType()) {
		case FIGHTER:
			hpBonus = character.getConstitution().getFighterHPAdjustment();
			break;
		case RANGER:
			hpBonus = character.getConstitution().getFighterHPAdjustment();
			break;
		case PALADIN:
			hpBonus = character.getConstitution().getFighterHPAdjustment();
			break;
		default:
			hpBonus = character.getConstitution().getHitPointAdjustment();
			break;
		}
		
		while (characterClass.getHitDieHistory().size() < characterClass.getExperienceLevel()) {
			characterClass.getHitDieHistory()
			.add(new HitDieRecord(characterClass.getHitDie().roll(), 
					hpBonus));
		}
	}
	
	/**
	 * These methods may belong in a more generic location if there are magical temporary level bumps
	 * they will live here for now.
	 * @param characterClass
	 */
	public void processClassSavingThrows(CharacterClass characterClass) {
		logger.log(Level.INFO,  "Class Saving Throw Check");
		characterClass.setSavingThrow( 
				application.getReferenceDataSet().getSavingThrowTableLoader()
				.getRecord(characterClass.getCharacterClassType().className, 
						characterClass.getExperienceLevel())
				);
	}
	
	public void processClassCombat(CharacterClass characterClass) {
		logger.log(Level.INFO,  "Class Combat Check : " + characterClass.getCharacterClassType().className + ":" + characterClass.getExperienceLevel());
		characterClass.setCombatRecord( 
				application.getReferenceDataSet().getCombatTableLoader()
				.getRecord(characterClass.getCharacterClassType().className, 
						characterClass.getExperienceLevel())
				);
	}

	public void processCharacterHitPoints() {
		// TODO Auto-generated method stub
		int classCount = character.getClasses().size();

		character.setMaxHitPoints(
				Math.round(
						(character.getClasses().entrySet().stream()
								.map(cClass -> cClass.getValue().getHitDieHistory())
								.flatMap(hpHist -> hpHist.stream())
								.map(hp -> hp.getHitPoints())
								.reduce(0, Integer::sum)) / classCount
						)
				);
	}
}
