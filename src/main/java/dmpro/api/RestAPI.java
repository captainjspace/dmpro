package dmpro.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dmpro.attributes.Attribute;
import dmpro.character.AttributeRoller;
import dmpro.character.Character;
import dmpro.character.CharacterService;
import dmpro.character.PCCharacterBuilder;
import dmpro.character.classes.CharacterClassType;
import dmpro.character.race.RaceType;
import dmpro.core.ReferenceDataSet;
import dmpro.core.Server;
import dmpro.data.loaders.ClassAttributeLoader.ListPossibleClassResults;
import dmpro.data.loaders.ClassRaceLoader.ListPossibleClassRaceResults;
import dmpro.data.loaders.RaceAttributeLoader.ListPossibleRaceResults;
import dmpro.serializers.CharacterGsonService;
/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 20, 2016
 */


/* root is /api */
@Path("/")
public class RestAPI {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Context ServletContext context;
	boolean initialized = false;
	Server application;
	CharacterService characterService;
	ReferenceDataSet referenceDataSet;
	Gson gson;


	public void init(){
		application = (dmpro.core.Application) context.getAttribute("dmpro");
		application.getReferenceDataSet().run();
		characterService = application.getCharacterService();
		referenceDataSet = application.getReferenceDataSet();
		//characterService.loadAllCharacters();
		gson = CharacterGsonService.getCharacterGson();
		initialized = true;
		//logger.log(Level.INFO, application.getClass().getName());
	}

	@GET
	@Produces("application/json")
	@Path("/allcharacters")
	public String getCharacters() {
		if (!initialized) init();
		return gson.toJson(characterService.getCharacterList());
	}


	@GET
	@Produces("application/json")
	@Path("/character/{characterId}")
	public String getCharacter(@PathParam("characterId") String characterId) {

		if (!initialized) init();
		return characterService.getCharactersJSON().get(characterId);

		//Character c = characterService.loadCharacter(characterId);
		//System.out.println("Request Received: ");

		// return HTTP response 200 in case of success
		//return CharacterGsonService.getCharacterGson().toJson(c);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/character/{characterId}/setpersonal")
	public Response setPersonalCharacter(@PathParam("characterId") String characterId) {
		if (!initialized) init();
		Character c = characterService.getCharacter(characterId);
		c.setAge(referenceDataSet.getRaceClassAgeLoader().getAge(c.getRace().getRaceType(),
																 c.getClasses().values().iterator().next().getCharacterClassType()));
		c.setHeight(referenceDataSet.getRaceSizeLoader().getHeight(c.getRace().getRaceType()));
		c.setWeight(referenceDataSet.getRaceSizeLoader().getWeight(c.getRace().getRaceType()));
		characterService.initCharacter(c); //processModifers, XP, and Save
		return Response.status(200).entity(gson.toJson(c.getCharacterId())).build();
		//characterService.getCharactersJSON().get(characterId);
	}

	@POST
	@Produces("application/json")
	@Path("/character")
	@Consumes("application/json")
	public Response createCharacter(String characterJSON) {
		logger.log(Level.INFO, characterJSON);

		if (!initialized) init();

		dmpro.character.Character formData =  gson.fromJson(characterJSON, dmpro.character.Character.class);
		PCCharacterBuilder pc = new PCCharacterBuilder();
		pc.buildCharacterInitialize();

		pc.buildCharacterPersonalInformation(formData.getSex(),
				formData.getName(),
				formData.getPrefix(),
				formData.getFirstName(),
				formData.getLastName(),
				formData.getTitle(),
				formData.getSuffixes());

		/* probabaly should contain this */
		AttributeRoller attributeRoller = new AttributeRoller();
		Map<String,Attribute> attributes = new HashMap<String,Attribute>();
		int[] attributeRolls = attributeRoller.attributeRolls();
		attributes.put("Strength", referenceDataSet.getStrengthLoader().getRecord(attributeRolls[0]));
		attributes.put("Intelligence", referenceDataSet.getIntelligenceLoader().getRecord( attributeRolls[1]));
		attributes.put("Wisdom", referenceDataSet.getWisdomLoader().getRecord(attributeRolls[2]));
		attributes.put("Dexterity", referenceDataSet.getDexterityLoader().getRecord(attributeRolls[3]));
		attributes.put("Constitution", referenceDataSet.getConstitutionLoader().getRecord( attributeRolls[4]));
		attributes.put("Charisma", referenceDataSet.getCharismaLoader().getRecord(attributeRolls[5]));
		pc.buildCharacterAttributes(attributes);
		//initialize attributes
		//cannot do this without a race
		//Character c = characterService.getCharacterModifierEngine().processModifiers(pc.getCharacter());
		Character c = pc.getCharacter();
		c.initializeModifiedAbilityScores();
		/* end attribute generation */
		characterService.saveCharacter(c);
		return Response.status(201).entity(gson.toJson(c)).build();
	}

	@GET
	@Produces("application/json")
	@Path("/character/{characterId}/select-race")
	public Response getCharacterRacePossible(@PathParam("characterId") String characterId) {
		String result="Your attributes are so bad you can't be a race?  3's in everything???";
		if (!initialized) init();
		Character c = characterService.getCharacter(characterId);
		//logger.log(Level.INFO, c.getCharacterId());
		ListPossibleRaceResults listPossibleRaceResults = referenceDataSet.getRaceAttributeLoader().listPossibleRaces(c.getAttributesAsMap(), c.getSex());
		//listPossibleRaceResults.possibleRaces.stream().forEach(r -> logger.log(Level.INFO, r.raceName()));
		if (!listPossibleRaceResults.possibleRaces.isEmpty()) {
			result=gson.toJson(listPossibleRaceResults);
		} else {
			result=gson.toJson(result);
		}
		return Response.status(200).entity(result).build();
	}

	//@Path("/character/{characterId}/select-race/{raceType}")
	@PUT
	@Produces("application/json")
	@Path("/savecharacter")
	@Consumes("application/json")
	public Response putCharacter(String characterJSON) {
		logger.log(Level.INFO, characterJSON);
		if (!initialized) init();
		dmpro.character.Character formData =  gson.fromJson(characterJSON, dmpro.character.Character.class);
		Character c = characterService.saveCharacter(formData);
		return Response.status(200).entity(gson.toJson(c.getCharacterId())).build();
	}


	@GET
	@Produces("application/json")
	@Path("/character/{characterId}/select-class")
	public Response getCharacterClassPossible(@PathParam("characterId") String characterId) {
		/** 
		 * throws NullPointer if race not set
		 */
		String result="Class selection train wreck!";
		if (!initialized) init();
		Character c = characterService.getCharacter(characterId);
		ListPossibleClassResults listPossibleClassResults = referenceDataSet.getClassAttributeLoader().listPossibleClasses(c.getAttributesAsMap());
		ListPossibleClassRaceResults possibleClassRaceResults = referenceDataSet.getClassRaceLoader().listPossibleClasses(c.getRace().getRaceType());
		List<CharacterClassType> possibleClasses = 
				listPossibleClassResults.possibleClasses.stream().filter(p -> possibleClassRaceResults.possibleClasses
						.stream()
						.anyMatch(b -> b.className.equals(p.className)))
				.collect(Collectors.toList());
		Map<String,Object> results = new HashMap<String,Object>();
		results.put("attributeFilteredClasses", listPossibleClassResults);
		results.put("raceFilteredClasses", possibleClassRaceResults);
		results.put("possibleClasses", possibleClasses);
		if (!possibleClasses.isEmpty()) {
			result=gson.toJson(results);
		} else {
			result=gson.toJson(result);
		}
		return Response.status(200).entity(result).build();
	}

	//1477429801056019711205
	/*
	 * SPELLS API
	 *
	 *
	 */
	
	@GET
	@Produces("application/json")
	@Path("/spells/{spellName}")
	public String getSpell(@PathParam("spellName") String spellName) {
		if (!initialized) init();
		return gson.toJson(referenceDataSet.getSpellLibrary().getSpell(spellName));
	}

	@GET
	@Produces("application/json")
	@Path("/allspells")
	public String listSpells() {
		if (!initialized) init();
		return referenceDataSet.getSpellLibrary().getWebSpellLibrary(); //pre-cached JSON
	}

	/*
	 * COMBAT API
	 */
	@GET
	@Produces("application/json")
	@Path("/allcombat")
	public String getCombatTables() {
		if (!initialized) init();

		return referenceDataSet.getCombatTableLoader().getWebCombatTables();
	}

	/*
	 * Saving Throws API
	 */
	@GET
	@Produces("application/json")
	@Path("/allsavingthrows")
	public String getSavingThrowTables() {
		if (!initialized) init();
		return referenceDataSet.getSavingThrowTableLoader().getWebSavingThrowTables();
	}

	/*
	 * Thief Skills API
	 */
	@GET
	@Produces("application/json")
	@Path("/thiefskills")
	public String getThiefSkillTable() {
		if (!initialized) init();
		return referenceDataSet.getThiefAbilityTableLoader().getWebTable();
	}

	/*
	 * Turn Undead API
	 */
	@GET
	@Produces("application/json")
	@Path("/turnundead")
	public String getTurnUndeadTable() {
		if (!initialized) init();
		return referenceDataSet.getTurnUndeadLoader().getWebTable();
	}

	/*
	 * Race Details API
	 */
	@GET
	@Produces("application/json")
	@Path("/race")
	public String getRaces() {
		if (!initialized) init();
		return gson.toJson(referenceDataSet.getRace());
	}
	@GET
	@Produces("application/json")
	@Path("/race/{raceType}")
	public String getRace(@PathParam("raceType") String raceType) {
		if (!initialized) init();
		RaceType rt = RaceType.valueOf(raceType);
		return gson.toJson(referenceDataSet.getRace().get(rt));
	}


	/*
	 * Class Details API
	 */
	@GET
	@Produces("application/json")
	@Path("/classes")
	public String getCharacterClasses() {
		if (!initialized) init();
		return gson.toJson(referenceDataSet.getClasses());
		//return Response.status(200).entity(referenceDataSet.getClasses()).build();
	}
	@GET
	@Produces("application/json")
	@Path("/classes/{characterClass}")
	public String getCharacterClasses(@PathParam("characterClassType") String characterClassType) {
		if (!initialized) init();
		CharacterClassType cct = CharacterClassType.valueOf(characterClassType);
		return gson.toJson(referenceDataSet.getClasses().get(cct));
		//return Response.status(200).entity(referenceDataSet.getClasses()).build();
	}

	/*
	 * TESTING CONNECTIVITY API
	 */
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verify() {
		String result = "RESTService Successfully started..";
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/session")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSession() {
		StringBuilder sb = new StringBuilder();
		sb.append("Comment")
		.append(context.getSessionCookieConfig().getComment())
		.append("Name")
		.append(context.getSessionCookieConfig().getName());
		String result = sb.toString();
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}

}