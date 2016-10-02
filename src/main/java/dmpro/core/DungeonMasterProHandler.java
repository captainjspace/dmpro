package dmpro.core;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class DungeonMasterProHandler implements Runnable {
	
	//TODO: cheap and tawdry - getting lazy - although technically this really is static...
	public static AsciiArt ascii = new AsciiArt();
	
	Application application;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final String EXIT = ".exit.";
	boolean isRunning;
	Socket clientSocket;
	Scanner input = null;
	Formatter output = null;
	private CommandInterpreter commandInterpreter;

	public DungeonMasterProHandler(Socket clientSocket, Application application) {
		this.clientSocket = clientSocket;
		this.application = application;
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
			//			commands.get(commands.size()-1).chars().forEach(p -> System.out.println(p));
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
//			int [] ctrlc = {65533,65533,65533,65533,6};
			
			//IntStream is = Arrays.stream(ctrlc).boxed().collect(Collectors.toList()))) 
			
//			if (commands.stream().anyMatch(p -> p.chars() == IntStream.of(ctrlc))) {
//				return EXIT;
//			}
			
			if (commands.get(commands.size()-1).equals(EXIT)) return EXIT;
			
		}
		
		String response = "";
//		try {
			response = commandInterpreter.interpretCommands(commands);
//		} catch (IOException e) {
//			logger.log(Level.WARNING, "Error interpreting commands", e);
//			response = "Error occured!";
//		}
		output.format("%s\n>",response);
		output.flush();
		
//		output.format(application.getReferenceDataSet().getMagicItemLoader().getMagicItem("potion of animal control").toString());
//		output.flush();
//
//		try { 
//			commands.stream()
//			.forEach(cmd -> 
//			output.format("%s\n>",application.getReferenceDataSet()
//					.getSpellLibrary().getSpell(cmd).getFullSpellText()).flush());
//		} catch (NullPointerException e) {
//			output.format("spelling error!");
//			logger.log(Level.INFO, "No spell found");
//		}
//		commands.stream().forEach(cmd -> logger.log(Level.INFO, cmd));
//		output.flush();
//

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
