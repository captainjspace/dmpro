package dmpro.character;
/**
 * XP Processor 
 * - XP operations occur at the CharacterClass scope.
 * - Operations that are common to all classes will be contained in this unit
 * - Actions that are class specific will be registered in the queue for class specific processors
 * 
 * This class focuses will:
 * <ol>
 * <li> Add experience to a class</li>
 * <li> determine if xp gets the 10% bonus</li>
 * <li> divide experience among subclasses </li>
 * <li> check for level increases </li>
 * <li> add level to class </li>
 * <li> refresh combat record </li>
 * <li> refresh saving throw record </li>
 * <li> add class specific required actions for other processors </li>
 * <li> recalc max hitpoints </li>
 * </ol>
 */

import java.util.logging.Level;
import java.util.logging.Logger;

//import dmpro.SavingThrowLoader;
import dmpro.character.classes.CharacterClass;
import dmpro.character.classes.HitDieRecord;
import dmpro.character.classes.XPBonus;
import dmpro.character.classes.CharacterClassType;
import dmpro.character.managementaction.CharacterManagementActions;
import dmpro.core.Server;
import dmpro.data.loaders.ExperienceTableRecord;

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
	 * TODO: energy drain combat 
	 * @param newXPpoints XP to add
	 */
	public void addExperiencePoints(int newXPpoints) {

		//support multiclass
		int classCount = this.character.getClasses().size();
		int xpToAdd = newXPpoints/classCount;
		float bonusMultiplier=1.0f;

		//evaluate 10% bonus for each class.
		//add to xpToAdd
		//shorten this code
		int z=0;
		for (CharacterClass characterClass : this.character.getClasses().values()) {
			logger.log(Level.INFO, "" + z++ + ":" + characterClass.toString());
			//if characterClass record is damaged (most likely due to dev work) abort
			if(characterClass.getCharacterClassType() == null) { 
				logger.log(Level.INFO, " Null Character Class Type - Skipping ");
				character.addRequiredAction(CharacterManagementActions.CLASSCLEANUP);
				continue; 
			} 
			bonusMultiplier = (float)1.0;
//			try {
//			logger.log(Level.INFO, characterClass.getCharacterClassType().className);
//			} catch (NullPointerException e) {
//				logger.log(Level.INFO, "Dirty class record no type: " + characterClass);
//			}
			
			if (!characterClass.getXPBonusRequirements().isEmpty()) {
				for (XPBonus xpBonus : characterClass.getXPBonusRequirements()) { //you are my null pointer{
					Integer attributeValue = character.getAttributesAsMap().get(xpBonus.getAttributeName().toLowerCase()).getModifiedAbilityScore();
					//TODO: review, clean, tertiary
					if ( Integer.valueOf(attributeValue) > xpBonus.getMinVal())
						bonusMultiplier = (float)1.1;
					else { 
						bonusMultiplier = (float)1.0; 
						break; //end as soon as we don't have it
					}
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
		logger.log(Level.INFO, "Character Class: " + characterClass.getClassName());
		logger.log(Level.INFO, "Character Class: " + characterClass.getExperiencePoints());
		ExperienceTableRecord experienceTableRecord = application.getReferenceDataSet()
				.getExperienceTableLoader().getRecordByXP(characterClass.getClassName(), characterClass.getExperiencePoints());
//		logger.log(Level.INFO, "Character Class: " + characterClass.getClassName());
//		logger.log(Level.INFO, "Character Class: " + characterClass.getExperiencePoints());
		//TODO: implement race checks for max
		//TODO: implement max 1 level increase and cap XP.
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

	/* TODO: refactor me */
	private void queueClassSpecificActions(Character character, CharacterClass characterClass) {
		if  (
				(characterClass.getCharacterClassType() == CharacterClassType.MAGICUSER) ||
				(characterClass.getCharacterClassType() == CharacterClassType.ILLUSIONIST)  ||
				(characterClass.getCharacterClassType() == CharacterClassType.CLERIC) ||
				(characterClass.getCharacterClassType() == CharacterClassType.DRUID) || 
				(characterClass.getCharacterClassType() == CharacterClassType.RANGER) ||
				(characterClass.getCharacterClassType() == CharacterClassType.PALADIN)
			) {
			//TODO: Resolve character vs character-class action for multiclass
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
		
		//i feel like this is gross code.
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
		
		//keep record of hit dice in case we get touched by  vampire and lose two levels.
		while (characterClass.getHitDieHistory().size() < characterClass.getExperienceLevel()) {
			characterClass.getHitDieHistory()
			.add(new HitDieRecord(characterClass.getHitDie().roll(), 
					hpBonus));
		}
	}
	
	/**
	 * These methods may belong in a more generic location if there are magical temporary level bumps
	 * for example potion of heroism or ioun stones...
	 * they will live here for now.
	 * @param characterClass class to check saving throws table
	 */
	public void processClassSavingThrows(CharacterClass characterClass) {
		logger.log(Level.INFO,  "Class Saving Throw Check");
		characterClass.setSavingThrow( 
				application.getReferenceDataSet().getSavingThrowTableLoader()
				.getRecord(characterClass.getCombatClass().className, 
						characterClass.getExperienceLevel())
				);
	}
	
	public void processClassCombat(CharacterClass characterClass) {
		logger.log(Level.INFO,  "Class Combat Check : " + characterClass.getCharacterClassType().className + ":" + characterClass.getExperienceLevel());
		characterClass.setCombatRecord( 
				application.getReferenceDataSet().getCombatTableLoader()
				.getRecord(characterClass.getCombatClass(), 
						characterClass.getExperienceLevel())
				);
	}

	/**
	 * stream all hit die records and calculate max
	 * stream api is the bomb.
	 */
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
