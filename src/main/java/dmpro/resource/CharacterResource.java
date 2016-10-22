/**
 * 
 */
package dmpro.resource;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import com.codahale.metrics.annotation.Timed;

import dmpro.character.CharacterService;
import dmpro.character.Character;

/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 16, 2016
 */
@Path("/character")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CharacterResource {


	private CharacterService characterService;

	public CharacterResource(CharacterService characterService) {
		this.characterService = characterService;
	}

	@GET
	@Path("/{id}")
	@Timed
	public Character getCharacter(@PathParam("id") String id) {
		Character character = characterService.getCharacter(id);
		if (character != null) {
			return character;
		} else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

	@GET
	@Timed
	public Map<String,Character> listCharacters() {
		return characterService.getCharacters();
	}

	@POST
	@Timed
	public void save(Character character) {
		if (character != null) {
			if (character.getCharacterId() != null) {
				characterService.saveCharacter(character);
			}
		}
	}
}