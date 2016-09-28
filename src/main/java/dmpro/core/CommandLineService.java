package dmpro.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLineService implements Runnable {
	
	private Logger logger = Logger.getLogger(this.getClass().toString());
	private final ServerSocket serverSocket;
	
	//permitting 5 concurrent remote client connections
	private final ExecutorService pool;
	private int poolSize = 5;
	
	
	protected int serverPort = 8085;
	protected Thread clientHandler = null;
	Application application;
	
	public CommandLineService(Application application) throws IOException{
		this.application = application;
		this.serverSocket = new ServerSocket(serverPort);
		this.pool = Executors.newFixedThreadPool(poolSize);
		logger.log(Level.INFO, "dmpro commandline service is now listening");
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		synchronized(this){
			this.clientHandler = Thread.currentThread();
		}
		logger.log(Level.INFO, "dmpro command line server - " + clientHandler.getName());

		try {
			for (;;) {
				pool.execute(new DungeonMasterProHandler(serverSocket.accept(), application));
				logger.log(Level.INFO, "dmpro commandline service recieved a connection");
			} 
		} catch (IOException e) {
			pool.shutdown();
			logger.log(Level.SEVERE, "Unable to accept incoming connections", e);
		}
	}
	
	
	
	
	

}
