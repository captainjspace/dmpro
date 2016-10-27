package dmpro.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import dmpro.character.CharacterModifierEngine;
import dmpro.character.CharacterService;
import dmpro.character.ModifierEngine;
import dmpro.character.Character;

public class Application implements Server {
	
	//Class members
	private static final String APPLICATION_NAME = "Dungeon Master Pro";
	private static final String SECRETDOOR = "3 taps, a level and a gem of seeing";
	private static final String SHUTDOWN = "dmpro-shutdown";
	private static final int SHUTDOWNWAIT = 8;
	
	//the logger
	private Logger logger= Logger.getLogger(APPLICATION_NAME);
	
	//TODO: at some point i will track threads
	private final Map<Integer,String> threadMap = new HashMap<Integer,String>();
	//TODO: get node stream
	//		trap ctrl + c
	private final Runtime server;
	
	//threads for subsystem
	private final ExecutorService subsystem;
	private final int poolSize  = 12; 
	
	//Core subsystems
	private CharacterService characterService;
	private ReferenceDataSet referenceDataSet = new ReferenceDataSet();
	private ModifierEngine characterModifierEngine;
	private CommandLineService commandLineService;
	private NodeWrapperService nodeWrapperService;
	private JettyServer jetty;
	//private RestServer dropwizard;
	
//	private GeoFactory geoFactory;
	
//	private CharacterStateEngine characterStateEngine;
//	private MovementCalculatorEngine movementCalculatorEngine;
//	private EncounterEngine encounterEngine;
//	private CombatEngine combatEngine;
//	private RestWebService webService;
//	private JsonService jsonService;
//	private MessagingService messagingService;
//	private ApplicationHealthService applicationHealthService;

	

	public Application() {
		//set thread pool
		this.subsystem = Executors.newFixedThreadPool(poolSize);
		//trap
		this.server = Runtime.getRuntime();
		server.addShutdownHook(new Thread() {
			public void run() { 
				shutdownAndAwaitTermination();
				}
			});
			
	}
	
	//share preloaded data
	public ReferenceDataSet getReferenceDataSet() {
		return referenceDataSet;
	}
	public CharacterService getCharacterService(){
		return characterService;
	}
	
	public void start() {
		
		//main loop
		getMemoryStats();
		startReferenceDataSet();
		startCommandLineService();
		startCharacterService();
		startCharacterModifierEngine();
		startJetty();
		//startRestServer();
		System.out.println(this.referenceDataSet.getAsciiArt().art.get("banner.txt"));
		//startNodeWrapperService();
		getMemoryStats();
		
		int threads =  ((ThreadPoolExecutor) subsystem).getActiveCount();
		logger.log(Level.INFO, "Dungeon Master pro is here: Subsystem Thread Count:" + threads);

		//hangout and wait for shutdown command
		while (true) {
			System.out.println(">>> Exit with dmpro-shutdown");
			Scanner console = new Scanner(System.in);
			while (console.hasNext()) {
				System.out.println(">>> Exit with dmpro-shutdown");
				String command = console.nextLine();
				logger.log(Level.INFO, "-->" + command);
				if ( command.equals(SHUTDOWN)) {
					logger.log(Level.INFO, "Dungeon Master Pro shutting down - bye!");
					shutdownAndAwaitTermination();
					console.close();
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * 
//	 */
//	private void startRestServer() {
//		return; //this is not doing what I want...
//		try {
//			//this.dropwizard = new RestServer(this);
//			String [] args = { "server", "dmpro.yml"};
//			RestServer.main(args);
//			logger.log(Level.INFO, "Dropwizard is online");
//		} catch (Exception e) {
//			logger.log(Level.SEVERE, "Dropwizard failed - investigate", e);
//		}
//		
//		
//	}

	/**
	 * 
	 */
	private void startJetty() {
		try {
			this.jetty = new JettyServer(this);
			subsystem.execute(this.jetty);
			logger.log(Level.INFO, "Jetty  is online");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Jetty failed - investigate", e);
		}
		
	}

	/**
	 * Get Heap Data
	 * @return
	 */
	private String getMemoryStats() {
		String memInfo = String.format("Total Memory: %s\tFree Memory %s\t Used Memory %s:\n", 
				server.totalMemory(),
				server.freeMemory(), server.totalMemory()-server.freeMemory());
		
		logger.log(Level.INFO, memInfo);
		return memInfo;
	}
	
	private void startReferenceDataSet() {
		this.referenceDataSet = new ReferenceDataSet();
		subsystem.execute(this.referenceDataSet);
		logger.log(Level.INFO, "Reference Data is loading");
	}

	private void startNodeWrapperService() {
		try {
			this.nodeWrapperService = new NodeWrapperService();
			subsystem.execute(this.nodeWrapperService);
			logger.log(Level.INFO, "Node Wrapper Service started");
		} catch (Exception e) {
			logger.log(Level.INFO, "Node Wrappers Imploded", e);
		}
		
	}

	private void startCharacterModifierEngine() {
		//TODO : fine tune exceptions and implement Runnable
		try {
			this.characterModifierEngine = new CharacterModifierEngine(this);
			logger.log(Level.INFO, "Character Modifier Engine started");
		} catch (Exception e) {
			logger.log(Level.INFO, "Character Modifier Imploded", e);
		}
		
	}

	private void startCharacterService() {
		//TODO : fine tune exceptions and implement runnable
		try {
			this.characterService=new CharacterService(this);
			subsystem.execute(this.characterService);
			logger.log(Level.INFO, "CharacterService is online");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "CharacterService failed - investigate", e);
		}
		
	}

	private void startCommandLineService() {
		try {
			this.commandLineService = new CommandLineService(this);
			subsystem.execute(this.commandLineService);
			logger.log(Level.INFO, "CommandLineService is online");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "CommandLineService subsystem failed - check port status", e);
		}
	}

	void shutdownAndAwaitTermination() {
		logger.log(Level.WARNING, "System shutting down");
		try {
		  jetty.stop();
		} catch (Exception e) {
			logger.log(Level.WARNING,"Jetty close is messy...", e);
		}
		subsystem.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!subsystem.awaitTermination(SHUTDOWNWAIT, TimeUnit.SECONDS)) {
				subsystem.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!subsystem.awaitTermination(SHUTDOWNWAIT, TimeUnit.SECONDS))
					logger.log(Level.WARNING,"susbsystems did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			subsystem.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) {
		Application dmpro = new Application();
		dmpro.start();
	}

}
