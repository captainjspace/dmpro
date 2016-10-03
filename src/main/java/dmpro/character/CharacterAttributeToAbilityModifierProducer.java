package dmpro.character;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;
import java.util.logging.Level;
import dmpro.modifier.*;
import dmpro.modifier.ArmorClassModifier.ArmorClassModifierType;
import dmpro.modifier.MeleeModifier.MeleeModifierType;
import dmpro.modifier.MissileModifier.MissileModifierType;
import dmpro.modifier.Modifier.ModifierPriority;
import dmpro.modifier.Modifier.ModifierSource;
import dmpro.modifier.MovementModifier.MovementModifierType;

/** 
 * CharacterAttributeToAbilityModifierProducer.java
 * 
 * <table>
 * <tr>
 * <td>Strength</td>
 * <td>hitProbabilityModifier = MELEE</td>
 * <td>damageAdjustmentModifier = MELEE and MISSILE</td>
 * <td>weightAllowanceModifier = ENCUMBRANCE, ENCUMBRANCE->MOVEMENT </td>
 * </tr>
 * <tr>
 * <td>Intelligence</td>
 * <td>bonusLanguageModifier = LANGUAGE</td>
 * </tr>
 * <tr>
 * <td>Wisdom</td>
 * <td>bonusLanguageModifier -> SAVINGTHROW</td>
 * </tr>
 * <tr>
 * <td>Dexterity</td>
 * <td>initiativeAdjustment -> INITIATIVE</td>
 * <td>missileAttackAdjustment -> MISSILE TOHIT</td>
 * <td>defensiveAdjustment -> ARMORCLASS </td>
 * </tr>
 * <tr>
 * </table>
 * 
 * 
 * 
 * </table>
 * Take each Attributes raw scores and generate an ability modifier for the ModifierEngine
 * 
 * TODO -- refactor ... use constructors, abstract attributes?, reflection?
 * @author joshualandman
 * @version
 * @since
 *
 */

public class CharacterAttributeToAbilityModifierProducer implements ModifierProducer,Runnable {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Character character;
	List<Modifier> modifiers = new ArrayList<Modifier>();
	
	public CharacterAttributeToAbilityModifierProducer(Character character) {
		this.character = character;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public void run() {
		processStrength();
		processIntelligence();
		processWisdom();
		processDexterity();
		processConstitution();
		processCharisma();
		modifiers.forEach(p -> logger.log(Level.INFO, p.modifierSource.toString()));
		character.activeModifiers.addAll(modifiers);
	}
	
	void processStrength() {
		int toHit = character.getStrength().getHitProbabilityModifier();
		if (toHit != 0) {
			MeleeModifier meleeModifier = new MeleeModifier();
			meleeModifier.meleeModifierType = MeleeModifierType.TOHIT;
			meleeModifier.modifierPriority = ModifierPriority.HIGH;
			meleeModifier.modifierSource = ModifierSource.ATTRIBUTE;
			meleeModifier.setModifier(toHit);
			meleeModifier.setDescription("Strength To Hit Bonus");
			modifiers.add(meleeModifier);
		}
		
		int damage = character.getStrength().getDamageAdjustmentModifier();
		if (damage != 0) {
			MeleeModifier meleeModifier = new MeleeModifier();
			meleeModifier.meleeModifierType = MeleeModifierType.DAMAGE;
			meleeModifier.modifierPriority = ModifierPriority.HIGH;
			meleeModifier.modifierSource = ModifierSource.ATTRIBUTE;
			meleeModifier.setModifier(damage);
			meleeModifier.setDescription("Strength Damage Bonus");
			
			MissileModifier missileModifier = new MissileModifier();
			missileModifier.missileModifierType = MissileModifierType.DAMAGE;
			missileModifier.modifierPriority = ModifierPriority.HIGH;
			missileModifier.modifierSource = ModifierSource.ATTRIBUTE;
			missileModifier.setModifier(damage);
			missileModifier.setDescription("Strength Damage Bonus");
		
			modifiers.add(meleeModifier);
			modifiers.add(missileModifier);
		}
		
		int encumbrance = character.getStrength().getWeightAllowanceModifier();
		if (encumbrance != 0) {
			MovementModifier movementModifier = new MovementModifier();
			movementModifier.movementModifierType = MovementModifierType.ENCUMBRANCE;
			movementModifier.modifierPriority = ModifierPriority.LOW;
			movementModifier.modifierSource = ModifierSource.ATTRIBUTE;
			movementModifier.setModifier(encumbrance);
			movementModifier.setDescription("Strength Encumbrance Bonus");
			modifiers.add(movementModifier);
		}
			
	}
	
	//probably unnecessary.
	void processIntelligence() {
		//bonusLanguageModifier
		int lang = character.getIntelligence().getBonusLanguageModifier();
		if (lang != 0) {
			AbilityModifier abilityModifier = new AbilityModifier();
			abilityModifier.modifierPriority = ModifierPriority.LOW;
			abilityModifier.modifierSource = ModifierSource.ATTRIBUTE;
			abilityModifier.setModifier(lang);
			abilityModifier.setDescription("Intelligence Language Bonus");
			modifiers.add(abilityModifier);
		}
	}
	
	//legitimate combat evaluation
	void processWisdom() {
		//bonusLanguageModifier
		int magic = character.getWisdom().getMagicalAttackAdustment();
		if (magic != 0) {
			SavingThrowModifier savingThrowModifier = new SavingThrowModifier();
			savingThrowModifier.savingThrowType = SavingThrowType.MENTALATTACK;
			savingThrowModifier.modifierPriority = ModifierPriority.MEDIUM;
			savingThrowModifier.modifierSource = ModifierSource.ATTRIBUTE;
			savingThrowModifier.setModifier(magic);
			savingThrowModifier.setDescription("Wisdom Mental Save Bonus");
			modifiers.add(savingThrowModifier);
		}
	}
	
	//critical
	void processDexterity() {
		int init = character.getDexterity().getInitiativeAdjustment();
		if (init != 0) {
			InitiativeModifier initiativeModifier = new InitiativeModifier();
			initiativeModifier.modifierPriority = ModifierPriority.HIGH;
			initiativeModifier.modifierSource = ModifierSource.ATTRIBUTE;
			initiativeModifier.setModifier(init);
			initiativeModifier.setDescription("Dexterity Initiative Bonus");
			modifiers.add(initiativeModifier);
		}
		int missile = character.getDexterity().getMissileAttackAdjustment();
		if (missile != 0) {
			MissileModifier missileModifier = new MissileModifier();
			missileModifier.missileModifierType = MissileModifierType.TOHIT;
			missileModifier.modifierPriority = ModifierPriority.HIGH;
			missileModifier.modifierSource = ModifierSource.ATTRIBUTE;
			missileModifier.setModifier(missile);
			missileModifier.setDescription("Dexterity Ranged Attack ToHit Bonus");
			modifiers.add(missileModifier);
		}
		int ac = character.getDexterity().getDefensiveAdjustment();
		if (ac != 0) {
			ArmorClassModifier armorClassModifier = new ArmorClassModifier();
			armorClassModifier.armorClassModifierType = ArmorClassModifierType.ALL;
			armorClassModifier.modifierPriority = ModifierPriority.HIGH;
			armorClassModifier.modifierSource = ModifierSource.ATTRIBUTE;
			armorClassModifier.setModifier(ac);
			armorClassModifier.setDescription("Dexterity Armor Class Bonus");
			modifiers.add(armorClassModifier);
		}	
		//todo thief bonuses.
	}
	
	//only necessary at levelling
	void processConstitution() {
		
	}
	
	//henchman morale?
	void processCharisma() {
		
	}
}






