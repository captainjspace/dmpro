package dmpro.api;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 20, 2016
 */


import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;

import com.google.gson.Gson;

import dmpro.character.CharacterService;
import dmpro.core.Server;
import dmpro.core.Application;
import dmpro.serializers.CharacterGsonService;

/* root is /api */
@Path("/")
public class RestAPI {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	//TODO:  Figure out how to pass handle to application from jetty to jersey
	@Context ServletContext context;
	boolean initialized = false;
	Server application;
	CharacterService characterService;
	Gson gson;

	
	public void init(){
		application = (dmpro.core.Application) context.getAttribute("dmpro");
		application.getReferenceDataSet().run();
		characterService = application.getCharacterService();
		//characterService.loadAllCharacters();
		gson = CharacterGsonService.getCharacterGson();
		initialized = true;
		logger.log(Level.INFO, application.getClass().getName());
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
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "RESTService Successfully started..";
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
 
	@GET
	@Path("/session")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSession(InputStream incomingData) {
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