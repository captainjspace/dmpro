package dmpro.core;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * TODO:
 * just trying to make the server aware that node is running and that node is shutdown in concert
 * with other systems - even if I sent an Ctrl + c
 * 
 * using jetty as main server for now
 * 
 * may use node for transpiler purposes if the long API calls are pushed to msg queue later
 * 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */
public class NodeWrapperService implements Runnable {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final String directory=".";
	private final String commandToRun="node";
	protected ProcessBuilder builder;
	protected Process process;
	protected Scanner nodeConsoleReader;
	
	public void run() {

		/* 
		 * TODO:
		 * test that I can leverage the transpiler to monitor 
		 * asynchronous character output and update client app.
		 */
		logger.log(Level.INFO, "Buiding node process");
		builder = new ProcessBuilder(commandToRun, "-e", "console.log('Hello from Node')");
		builder.directory( new File( directory )); // this is where you set the root folder for the executable to run with
		builder.redirectErrorStream(true); //merge error and out streams
		
		try {
			logger.log(Level.INFO, "starting node.js");
			process =  builder.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.WARNING, "Node failed to start", e);
		}

		logger.log(Level.INFO, "node.js running - grabbing input stream");
		nodeConsoleReader = new Scanner(process.getInputStream());
		while (nodeConsoleReader.hasNextLine()) {
			logger.log(Level.INFO, nodeConsoleReader.nextLine());
		}
		int result = -1;
		try {
			result = process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.log(Level.INFO, "Process exited with result %d", result);
	}

}
