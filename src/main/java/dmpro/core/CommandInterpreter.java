package dmpro.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import dmpro.data.loaders.CombatTableLoader;
import dmpro.utils.ParseUtils;

/**
 * CommandInterpreter.java
 * 
 * Yep
 * expression::= &lt;command&gt; | &lt;subsystem&gt;  &amp; subsystem command |  parameters '*'
 * @author Joshua Landman, joshua.s.landman@gmail.com
 *
 */
public class CommandInterpreter {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Application application;
	private ReferenceDataSet rds;
	private Scanner input;
	private Formatter output;
	private Commander commander;
	
	public CommandInterpreter() {
		//support static main testing
		this.output = new Formatter(System.out);
		this.rds = new ReferenceDataSet();
	}
	
	//passing along references
	public CommandInterpreter(Application application, Scanner input, Formatter output) {
		this.application = application;
		this.input = input;
		this.output = output;
		this.rds = application.getReferenceDataSet();
	}
	
	public enum CommandSet {
		CREATE("create"),
		LOAD("load"),
		ADD("add"),
		SAVE("save"),
		HELP("[h|H][e|E][l|L][p|P]"),
		LIST("[l|L][i|I][s|S][t|T]"),
		GET("[g|G][e|E][t|T]"),
		SEARCH("[s|S][e|E][a|A][r|R][c|C][h|H]|lookup|find"),
		EXIT("exit");
		
		private String regex;
		
		CommandSet(String regex) {
			this.regex = regex;
		}
		
		public String getRegex() {
			return this.regex;
		}
	}

	public enum Subsystem {
		CHARACTER("character|CHARACTER"),
		DATA("data|DATA"),
		DICE("dice|DICE");
		private String regex;

		Subsystem(String regex) {
			this.regex = regex;
		}

		public String getRegex() {
			return this.regex;
		}
	}

	public enum DataSet {
		COMBAT("combat|COMBAT"),
		EXPERIENCE("xp|XP"),
		SAVINGTHROW("savingthrow|SAVINGTHROW"),
		THIEFABILITY("thieving"),
		SPELLTABLES("spelltables|SPELLTABLES"),
		SPELLS("spell|SPELL"),
		MAGIC("magicitem|MAGICITEM"),
		WEAPONS("weapon(s)?|WEAPON"),
		ATTRIBUTES("attribute(s)?");
		
		private String regex;
		
		DataSet(String regex) {
			this.regex=regex;
		}
		
		public String getRegex() {
			return this.regex;
		}
	}
	
	public class CommandObject {
		Subsystem subsystem=Subsystem.DATA;
		DataSet dataSet = null;
		CommandSet commandSet=CommandSet.GET;
		String varString = "";
		int varInt = -1;
		
		public String toString() {
			return String.format("Subsystem:%s, Dataset:%s,Command:%s,StringVar%s,IntVar:%d",
					subsystem, dataSet, commandSet, varString, varInt);
		}
	}
	
	/**
	 * * Helper function to generate lookups - not for runtime really
	 * @return java.lang.String
	 * @param commands  a list of commands 
	 */
	protected String interpretCommands(List<String> commands)  {
		logger.log(Level.INFO, "interpreting commands");
		if (commands.size() == 0) return " Received No Commands ";
		CommandObject commandObject = new CommandObject();
		boolean knownToken;
		StringBuilder stringParameter = new StringBuilder();
		for (String s : commands) {
			
			knownToken = false;
			//logger.log(Level.INFO, "String loop:" + s);
			for (Subsystem d: Subsystem.values()) {
				
				if (s.matches(d.getRegex())) {
					logger.log(Level.INFO, "Input: " + s + "Token: " + d.toString());
					output.format("Input: %s Token: %s\n", s, d.toString());
					commandObject.subsystem = d;
					knownToken = true;
				}
			}
			for (CommandSet d: CommandSet.values()) {
				
				if (s.matches(d.getRegex())) {
					logger.log(Level.INFO, "Input: " + s + "Token: " + d.toString());
					output.format("Input: %s Token: %s\n", s, d.toString());
					commandObject.commandSet = d;
					knownToken = true;
				}
			}
			for (DataSet d: DataSet.values()) {
				if (s.matches(d.getRegex())) {
					logger.log(Level.INFO, "Input: " + s + "Token: " + d.toString());
					output.format("Input: %s Token: %s\n", s, d.toString());
					commandObject.dataSet = d;
					knownToken = true;
				}
			}
			
			if (!knownToken) {
				//if dice just make the string...
				if ((commandObject.subsystem != Subsystem.DICE) && (ParseUtils.isNumber(s))) {
					logger.log(Level.INFO, "Input: " + s);
					output.format("Input: %s Integer Parameter\n", s);
					commandObject.varInt = Integer.parseInt(s);
				} else {
					output.format("Input: %s String Parameter\n", s);
					stringParameter.append(s).append(" ");
					logger.log(Level.INFO, stringParameter.toString());
					commandObject.varString = stringParameter.substring(0, stringParameter.length()-1);
				}
			}
		}
		output.flush();
		//TODO: clean me up - method, and stubbable application for testing.
		commander = new Commander(application, input, output);
		String results = null;
		try {
			results = commander.execute(commandObject);
		} catch (NullPointerException e) {
			results = String.format("No Data For:\n\t%s\n",commandObject.toString());
			logger.log(Level.WARNING, "Error with " + commandObject.toString(), e);
		}
		return results;
		
	}
	/**
	 * This is strictly for dev time to generate a list of possible commands to execute 
	 * would be dog slow in a real runtime but pretty convenient for keeping track of what 
	 * we can ask the reference data set for as it expand.
	 * 
	 * @return
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	private String list() throws SecurityException, ClassNotFoundException {
		
		java.lang.reflect.Field [] fields = rds.getClass().getFields(); 
		Method[] methods = rds.getClass().getDeclaredMethods();
		StringBuilder sb = new StringBuilder();
		StringBuilder codeBuilder = new StringBuilder();
		
		for (java.lang.reflect.Field f : fields) {		
			sb.append(f.toString()).append('\n');
		}
		
		for (java.lang.reflect.Method m : methods) {
			String [] methodName  = m.toString().split("\\.");
			Class<?> returnType = m.getReturnType();
			
			if (!returnType.toString().equals("void")) returnType.getDeclaredMethods();
			else continue;
			
			String method = methodName[methodName.length-1];
			sb.append(method).append(':').append(returnType.getTypeName()).append('\n');
			
			Method[] returnTypeMethods = null;
			returnTypeMethods = returnType.getDeclaredMethods();
			if (returnTypeMethods == null ) continue;
			for (Method rM: returnTypeMethods) {
				
				if (rM == null ) break;
				else if (!rM.getName().startsWith("get")) continue;
				
//				String[] methodDef = rM.toString().split("\\.");
				Class<?> tableReturnType = rM.getReturnType();
				Class [] queryParameters = rM.getParameterTypes();
				
				sb.append("\t").append(rM.getName()).append('\n');
				sb.append("\t\t").append("returns:").append(rM.getReturnType().getTypeName()).append('\n');
				String [] splitForVarName = rM.getReturnType().getTypeName().split("\\.");
				String [] splitForFullClass = rM.getReturnType().getTypeName().split(" ");
				String varName = splitForVarName[splitForVarName.length-1];
				String className = splitForFullClass[splitForFullClass.length-1];
				varName = varName.substring(0,1).toLowerCase() + varName.substring(1);
				
				codeBuilder.append(className).append(" ").append(varName)
				.append(" = ").append(m.getName()).append("().").append(rM.getName()).append('(');
				
				for (Class parameter : queryParameters) {
					
					sb.append("\t\t").append(parameter.getName()).append('\n');
					codeBuilder.append("var").append(parameter.getSimpleName().substring(0,1).toUpperCase())
					.append(parameter.getSimpleName().substring(1)).append(", ");
//					codeBuilder.append(parameter.getSimpleName())
				}
				if  (codeBuilder.charAt(codeBuilder.length()-2) == ',')
					codeBuilder.delete(codeBuilder.length()-2,codeBuilder.length()-1);
				codeBuilder.append(");").append('\n');
			}
			
			
		}
		return codeBuilder.toString();
		
	}
	
	
	public static void main (String args[]) throws SecurityException, ClassNotFoundException {
		CommandInterpreter ci = new CommandInterpreter();
		List<String> testCommand = new ArrayList<String>();
		for (String s : Arrays.asList("combat", "fighter", "6") )
			testCommand.add(s);
		
		System.out.println(ci.list());
		
		
	}

	/**
	 * @return the input
	 */
	public Scanner getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(Scanner input) {
		this.input = input;
	}

	/**
	 * @return the output
	 */
	public Formatter getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(Formatter output) {
		this.output = output;
	}

}
