package dmpro.core;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * TODO:
 * this is not working dammit. of-course this may be misguided anyway.
 * just trying to make the server aware that node is running and that node is shutdown in concert
 * with other systems - even if I sent an Ctrl + c
 * 
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */
public class NodeWrapperService implements Runnable {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final String directory="/Volumes/projects/mynodeapps/angular2-tour-of-heroes/";
	private final String commandToRun="/usr/bin/local/npm";
	protected ProcessBuilder builder;
	protected Process process;
	protected Scanner nodeConsoleReader;
	
	public void run() {

		logger.log(Level.INFO, "Buiding node process");
		builder = new ProcessBuilder(commandToRun, "start");
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
