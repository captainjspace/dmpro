/**
 * 
 */
package dmpro.core;
import dmpro.resource.CharacterResource;

import io.dropwizard.Application;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 16, 2016
 */



public class RestServer extends io.dropwizard.Application<RestServerConfiguration> { // implements Runnable

	private dmpro.core.Server application; //injecting dmpro
	
	public RestServer() {};
	
	public RestServer(dmpro.core.Server application){
		this.application = application;
	}
	
	public static void main(final String[] args) throws Exception {
		new RestServer().run(args);
	}

	@Override
	public String getName() {
		return "Dungeon Master Pro Rest Services";
	}

	@Override
	public void initialize(final Bootstrap<RestServerConfiguration> bootstrap) {
		bootstrap.addBundle(new Java8Bundle());
	}

	@Override
	public void run(final RestServerConfiguration configuration,
			final Environment environment) {
		final CharacterResource characterResource = new CharacterResource(
				application.getCharacterService());
		environment.jersey().register(characterResource);
	}

//	/* (non-Javadoc)
//	 * @see java.lang.Runnable#run()
//	 */
//	@Override
//	public void run() {
//
//		initialize(new Bootstrap<RestServerConfiguration>(this));
//		run(new RestServerConfiguration(), 
//				new io.dropwizard.setup.Environment(this.getName(),
//						new ObjectMapper(),
//						javax.validation.Validation.buildDefaultValidatorFactory().getValidator(),
//						new com.codahale.metrics.MetricRegistry(),
//						this.getClass().getClassLoader()));
//	}

}


