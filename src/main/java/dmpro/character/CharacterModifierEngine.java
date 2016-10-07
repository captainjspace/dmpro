package dmpro.character;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import dmpro.core.*;
import dmpro.modifier.AttributeModifier;
import dmpro.modifier.Modifier;
import dmpro.modifier.Modifier.ModifierType;
import dmpro.attributes.*;

/**
 * CharacterModifierEngine.java
 * 
 * This state engine is responsible for post-build and gametime modification evaluation for a live character.
 * It will be used to process all permanent race/class, and active modifiers on a given character.
 * 
 * All modifiers will subclass Modifier.
 * A master instance will live within the Application and will be delivered to the requesting instance.
 * 
 * Order is important
 * Scope is important
 * Priority is important
 * Some modifiers will not be stackable, some will.
 * 
 * Modifiers need to be assessed, assigned, priority, order, stacked/reduced and applied to the Character composite.
 * 
 * Should be event driven -- onCharacterChange....
 * Character should have 
 * @author joshualandman
 * @version
 * @since
 */

public class CharacterModifierEngine implements ModifierEngine {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Server application;
	private Character character;
	//List <Modifier> activeModifiers = new ArrayList<Modifier>();
	Set<Modifier> activeModifiers = new LinkedHashSet<Modifier>();
	private ModifierProducer modifierProducer;
	
	public CharacterModifierEngine() {
		
	}
	public CharacterModifierEngine(Server application) {
		this.application = application;
		this.modifierProducer = new CharacterAttributeToAbilityModifierProducer(character);
	}
	
	
	
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processModifiers(dmpro.character.Character)
	 */
	@Override
	public Character processModifiers(Character character) {
		this.character = character;
		
		/* clear modifier list */
		character.setActiveModifiers(new ArrayList<Modifier>());
		
		/* collect all existing modifiers */
		
		assembleAllModifiers();
		//character.activeModifiers = this.activeModifiers;
		//activeModifiers = new ArrayList<Modifier>(new LinkedHashSet<Modifier>(activeModifiers));
		
		/* handle attribute modifiers */		
		activeModifiers.stream()
		.filter(m -> m.modifierType == ModifierType.ATTRIBUTE)
		.forEach(m -> processAttributeModifier(m));
		
		/* expand attribute modifiers */
 		this.modifierProducer= new CharacterAttributeToAbilityModifierProducer(this.character);
 		modifierProducer.run();
 		this.character = modifierProducer.getCharacter();
 		
 		/* now add all non attribute modifiers back in*/
 		character.getActiveModifiers().addAll(
 		activeModifiers.stream()
		.filter(m -> m.modifierType != ModifierType.ATTRIBUTE)
		.collect(Collectors.toList()));
 		
 		character.getActiveModifiers().stream().forEach(p -> System.out.printf("ActiveMod: %s\t%s\t%s\n", p.modifierSource, p.modifierType, p.modifierPriority));
		
 		return character;
    }
	
	private void assembleAllModifiers() {
		
		
		//Race Modifiers
		activeModifiers.addAll(character.getRace().getRaceAttributes());
		activeModifiers.addAll(character.getRace().getRaceAbilities());
		
		//Class Modifiers
		character.getClasses().values().stream().forEach(c -> activeModifiers.addAll(c.getClassAttribute()));
		character.getClasses().values().stream().forEach(c -> activeModifiers.addAll(c.getClassAbilities()));

		//Equipped Item Modifiers - eliminate equipment without modifiers
		
		/* Display equipment modifiers */
		character.getEquippedItems().stream()
		 .forEach(c -> c.getModifiers().stream()
				 .forEach(m -> System.out.println(m.toString())));
		 

		character.getEquippedItems().stream()
				 //.filter(ei -> !ei.getModifiers().isEmpty())
				 .forEach(c -> activeModifiers.addAll(c.getModifiers()));
		
		/* start with empty list */
		
//		if (!character.getEquippedItems().isEmpty()) {
////			logger.log(Level.INFO, "Found Items");
////			for (Item i : character.getEquippedItems()) {
////				logger.log(Level.INFO, i.getItemName() + ":" + i.getModifiers().size());
////				for (Modifier m : i.getModifiers())
////					logger.log(Level.INFO, m.modifierType.toString());
////			}
//			character.getEquippedItems().stream()
//			.filter(ei -> !ei.getModifiers().isEmpty())
//			.forEach(c -> activeModifiers.addAll(c.getModifiers()));
//		}
		//LocationModifiers

	}
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processEnvironmentModifiers()
	 */
	@Override
	public void processEnvironmentModifiers() {
		
	}
	
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processEncounterModifiers()
	 */
	@Override
	public void processEncounterModifiers() {
		
	}
	
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processPartyModifiers()
	 */
	@Override
	public void processPartyModifiers() {
		
	}
	
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processAttributeModifier(dmpro.modifier.Modifier)
	 */
	@Override
	public void processAttributeModifier(Modifier modifier) {
		AttributeModifier attributeModifier = (AttributeModifier) modifier;
		logger.log(Level.INFO,"Processing Attribute Modifier " + attributeModifier.getDescription());
		int originalAbilityScore, modifiedAbilityScore;
		switch (attributeModifier.getAttributeType()) {
		case STRENGTH:
			
			originalAbilityScore = character.getStrength().getAbilityScore();
			modifiedAbilityScore = character.getStrength().getModifiedAbilityScore();
			character.getStrength().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getStrength().getAttributeModifiers())));
			
			//initialize if unset
			if (modifiedAbilityScore == -1) 
				character.getStrength().setModifiedAbilityScore(originalAbilityScore);
			
			//this has to be a score bonus, and not have been processed already
			if ( (attributeModifier.modifiesAbilityScore()) && 
					(!character.getStrength().getAttributeModifiers().contains(attributeModifier)) ) { 
				modifiedAbilityScore = character.getStrength().getModifiedAbilityScore() + attributeModifier.getBonus();
				Strength s = (Strength) application.getReferenceDataSet().getStrengthLoader().getRecord(modifiedAbilityScore);
				
				List<AttributeModifier> uniqueModifiers = new ArrayList<AttributeModifier>();
				uniqueModifiers.addAll(character.getStrength().getAttributeModifiers());
				uniqueModifiers.add(attributeModifier);
				uniqueModifiers = uniqueModifiers.stream().distinct().collect(Collectors.toList());
				
				character.setStrength(s);
				character.getStrength().setAbilityScore(originalAbilityScore);
				character.getStrength().setModifiedAbilityScore(modifiedAbilityScore);
				character.getStrength().setAttributeModifiers(uniqueModifiers);
				
			}
			break;
			
		case INTELLIGENCE:
			originalAbilityScore = character.getIntelligence().getAbilityScore();
			modifiedAbilityScore = character.getIntelligence().getModifiedAbilityScore();
			character.getIntelligence().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getIntelligence().getAttributeModifiers())));
			
			//initialize if unset
			if (modifiedAbilityScore == -1) 
				character.getIntelligence().setModifiedAbilityScore(originalAbilityScore);
			
			//this has to be a score bonus, and not have been processed already
			if ( (attributeModifier.modifiesAbilityScore()) && 
					(!character.getIntelligence().getAttributeModifiers().contains(attributeModifier)) ) { 
				modifiedAbilityScore = character.getIntelligence().getModifiedAbilityScore() + attributeModifier.getBonus();
				Intelligence s = (Intelligence) application.getReferenceDataSet().getIntelligenceLoader().getRecord(modifiedAbilityScore);
				s.getAttributeModifiers().addAll(character.getIntelligence().getAttributeModifiers());
				character.setIntelligence(s);
				character.getIntelligence().setAbilityScore(originalAbilityScore);
				character.getIntelligence().setModifiedAbilityScore(modifiedAbilityScore);
				character.getIntelligence().addAttributeModifier(attributeModifier);
			}
			break;
		case WISDOM:
			originalAbilityScore = character.getWisdom().getAbilityScore();
			modifiedAbilityScore = character.getWisdom().getModifiedAbilityScore();
			character.getWisdom().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getWisdom().getAttributeModifiers())));
			
			//initialize if unset
			if (modifiedAbilityScore == -1) 
				character.getWisdom().setModifiedAbilityScore(originalAbilityScore);
			
			//this has to be a score bonus, and not have been processed already
			if ( (attributeModifier.modifiesAbilityScore()) && 
					(!character.getWisdom().getAttributeModifiers().contains(attributeModifier)) ) { 
				modifiedAbilityScore = character.getWisdom().getModifiedAbilityScore() + attributeModifier.getBonus();
				Wisdom s = (Wisdom) application.getReferenceDataSet().getWisdomLoader().getRecord(modifiedAbilityScore);
				s.getAttributeModifiers().addAll(character.getWisdom().getAttributeModifiers());
				character.setWisdom(s);
				character.getWisdom().setAbilityScore(originalAbilityScore);
				character.getWisdom().setModifiedAbilityScore(modifiedAbilityScore);
				character.getWisdom().addAttributeModifier(attributeModifier);
			}
			break;
		case DEXTERITY:
			
			//TODO: Genericize this for all attributes
			logger.log(Level.INFO, "DEX");
			originalAbilityScore = character.getDexterity().getAbilityScore();
			modifiedAbilityScore = character.getDexterity().getModifiedAbilityScore();
			character.getDexterity().addAttributeModifier(attributeModifier);
			character.getDexterity().getAttributeModifiers().stream().forEach(p -> System.out.println("DEX" + p.toString()));
			
			//enforce uniqueness
			character.getDexterity().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getDexterity().getAttributeModifiers())));
			
			/* reset modified ability */
			character.getDexterity().setModifiedAbilityScore(originalAbilityScore);
		
			character.getDexterity().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getDexterity().getAttributeModifiers())));
			
			/* process boosts */
			if  (attributeModifier.modifiesAbilityScore()) {
				
				/* add new bonus */
				modifiedAbilityScore = character.getDexterity().getModifiedAbilityScore() + attributeModifier.getBonus();
				logger.log(Level.INFO, "" + modifiedAbilityScore);
				
				
				/* build new attribute */
				Dexterity s = (Dexterity) application.getReferenceDataSet().getDexterityLoader().getRecord(modifiedAbilityScore);
				List<AttributeModifier> uniqueModifiers = new ArrayList<AttributeModifier>();
				uniqueModifiers.addAll(character.getDexterity().getAttributeModifiers());
				//uniqueModifiers.add(attributeModifier);
				/* unique again just in case */
				uniqueModifiers = new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getDexterity().getAttributeModifiers()));
				uniqueModifiers.stream().forEach(p -> System.out.println("DEX" + p.toString() + " - " + p.hashCode()));
				
				/* set attribute in character */
				character.setDexterity(s);
				character.getDexterity().setAbilityScore(originalAbilityScore);
				character.getDexterity().setModifiedAbilityScore(modifiedAbilityScore);
				character.getDexterity().setAttributeModifiers(uniqueModifiers);
			}
			break;
			
		case CONSTITUTION:
			originalAbilityScore = character.getConstitution().getAbilityScore();
			modifiedAbilityScore = character.getConstitution().getModifiedAbilityScore();
			character.getConstitution().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getConstitution().getAttributeModifiers())));
			
			//initialize if unset
			if (modifiedAbilityScore == -1) 
				character.getConstitution().setModifiedAbilityScore(originalAbilityScore);
			
			//this has to be a score bonus, and not have been processed already
			if ( (attributeModifier.modifiesAbilityScore()) && 
					(!character.getConstitution().getAttributeModifiers().contains(attributeModifier)) ) { 
				modifiedAbilityScore = character.getConstitution().getModifiedAbilityScore() + attributeModifier.getBonus();
				Constitution s = (Constitution) application.getReferenceDataSet().getConstitutionLoader().getRecord(modifiedAbilityScore);
				s.getAttributeModifiers().addAll(character.getConstitution().getAttributeModifiers());
				character.setConstitution(s);
				character.getConstitution().setAbilityScore(originalAbilityScore);
				character.getConstitution().setModifiedAbilityScore(modifiedAbilityScore);
				character.getConstitution().addAttributeModifier(attributeModifier);
			}
			break;
		case CHARISMA:
			originalAbilityScore = character.getCharisma().getAbilityScore();
			modifiedAbilityScore = character.getCharisma().getModifiedAbilityScore();
			character.getCharisma().setAttributeModifiers(new ArrayList<AttributeModifier>(new LinkedHashSet<AttributeModifier>(character.getCharisma().getAttributeModifiers())));
			
			//initialize if unset
			//if (modifiedAbilityScore == -1) 
			//	character.getCharisma().setModifiedAbilityScore(originalAbilityScore);
			
			//this has to be a score bonus, and not have been processed already
			if ( (attributeModifier.modifiesAbilityScore()) && 
					(!character.getCharisma().getAttributeModifiers().contains(attributeModifier)) ) { 
				modifiedAbilityScore = character.getCharisma().getModifiedAbilityScore() + attributeModifier.getBonus();
				Charisma s = (Charisma) application.getReferenceDataSet().getCharismaLoader().getRecord(modifiedAbilityScore);
				s.getAttributeModifiers().addAll(character.getCharisma().getAttributeModifiers());
				character.setCharisma(s);
				character.getCharisma().setAbilityScore(originalAbilityScore);
				character.getCharisma().setModifiedAbilityScore(modifiedAbilityScore);
				character.getCharisma().addAttributeModifier(attributeModifier);
			}
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processAbilityModifiers()
	 */
	@Override
	public void processAbilityModifiers() {
		
	}
	
	/* (non-Javadoc)
	 * @see dmpro.character.ModifierEngine#processSavingThrowModifiers()
	 */
	@Override
	public void processSavingThrowModifiers() {
		
	}

}
