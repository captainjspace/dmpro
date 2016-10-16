package dmpro.core;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class DungeonMasterProHandler implements Runnable {
	
	public AsciiArt ascii;
	Application application;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	public static final String EXIT = ".exit.";
	boolean isRunning;
	Socket clientSocket;
	Scanner input = null;
	Formatter output = null;
	private CommandInterpreter commandInterpreter;

	public DungeonMasterProHandler(Socket clientSocket, Application application) {
		this.clientSocket = clientSocket;
		this.application = application;
		this.ascii = application.getReferenceDataSet().getAsciiArt();
	}

	@Override
	public void run() {
		talk();
	}


	private void talk() {
		logger.log(Level.INFO,"dmpro client thread started");

		//grab input stream
		try {
			input = new Scanner(clientSocket.getInputStream());

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to attach scanner to input stream", e);
		}

		//and the output stream
		try {
			output = new Formatter(clientSocket.getOutputStream());
			commandInterpreter = new CommandInterpreter(application,input, output);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to attach Formatter to output stream", e);
		} 
		if ( input != null && output != null) {
			logger.log(Level.INFO, "successfully received stream connections, now munching");
		} 
		output.format(ascii.art.get("fighter"));
		output.format("Welcome to the Dungeon Master Pro Command Line Interface\n");
		output.format(ascii.art.get("help-file"));
		output.flush();
		String command = "";
		do  {
			command = parseAndRespond();
			if (Thread.currentThread().isInterrupted()) break;
			//logger.log(Level.INFO, "output open" + Boolean.toString());
		} while (!command.equals(EXIT) );
		input.close();
		output.close();
	}

	private String parseAndRespond() {
		// TODO Auto-generated method stub
		List<String> commands = new ArrayList<String>();
		while (input.hasNext()) {
			String inputToken = input.next();
			commands.add(inputToken);
			commands.get(commands.size()-1).chars().forEach(p -> System.out.println(p));
			
			/* trap telnet control c */
			String telnetControlC = IntStream.of(65533,65533,65533,65533,6).collect(StringBuilder::new,
					StringBuilder::appendCodePoint, StringBuilder::append).toString();
			if (inputToken.contains(telnetControlC)) { 
				System.out.println("Caught you CTRL+C evil!");
				return EXIT;
			}
			if (commands.get(commands.size()-1).equals(".")) {
				commands.remove(commands.size()-1);
				break;
			}
			
			if (commands.get(commands.size()-1).equals(EXIT)) return EXIT;
			
		}
		
		String response = "";
		/* I'm not sure if I want to trap an error or let it percolate up yet */
		
//		try {
			response = commandInterpreter.interpretCommands(commands);
//		} catch (IOException e) {
//			logger.log(Level.WARNING, "Error interpreting commands", e);
//			response = "Error occured!";
//		}
		output.format("%s\n>",response);
		output.flush();
		

		return "";

	}

	/**
	 * @return the ascii
	 */
	public AsciiArt getAscii() {
		return ascii;
	}

	/**
	 * @param ascii the ascii to set
	 */
	public void setAscii(AsciiArt ascii) {
		this.ascii = ascii;
	}

}
