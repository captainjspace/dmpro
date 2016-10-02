package dmpro.spells;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dmpro.data.loaders.ResourceLoader;
import dmpro.utils.ParseUtils;

public class AreaOfEffectLexer implements ResourceLoader {
	
	public static boolean DEBUG = true;
	
	public final String explode = "[\"]";
	public final String num = "([0-9]+(/[0-9]+)?[\']?)|([o|O|0]ne|[t|T]wo|[t|T]hree|[f|F]our|[f|F]ive|[s|S]ix|[s|S]even|[e|E]ight|[n|N]ine|Ten)|[u|U]p|[0-9]+-[0-9]+|level[s]?|Each|minimum|more";
	public final String dieRollOperator="to";
	public final String operator = "(\\+)|(plus)|or";
	public final String orOperator = "or";
	public final String factors = "by|times|/|[x|X]|,|(([0-9]+([\"|\'])?)|([a-zA-Z]+))/(([a-zA-Z]+)|([0-9]+))|per|[a-zA-Z]+,";
	public final String definedUnits = "weight|column|plane|\'|probe|cloud|wide|base|inch(es)?|miles|cube|globe|hemisphere|cone[\\.]?|diameter|radius|long|circle|sphere|square[\"]?|path|wedge|area|foot|feet|yard|range|g\\.p\\." +
	"|sight|[l|L]ine|light|deep";
	public final String definedSubjects 
	= "[\\(]?[c|C]reature[\\(]?[s]?[\\)]?|magic-user|[i|I]llusionist|animal[s]?|mammal|monster[s]?|scroll|[p|P]erson[s]?|fire"+
		"|Weapon\\(s\\)|[o|O]bject|stone|metal|club|item|inscription|chest|cleric|[c|C]aster|pages" +
		"|opponent|druid|Character|script|substance|arrow|bolt|source|pole";
	public final String definedAdjectives = "within|linear|extending|end|used|high|length|touched|cubic|reading|summoned|facing|small|written|speaking|normal|magical|oaken|[s|S]pell|Hearing" 
			+ "|plane|type|size|(a)?round";
	public final String connectors = "in|of|[t|T]he|of|a|[a|A]s|at|from|about";
	public final String special = "Personal|(\\()?[s|S]pecial(\\))?";
	public enum TokenType {
		NUMBER,
		EXPLODE,
		OBJECT,
		SUBJECT,
		ADJECTIVE,
		UNIT,
		OPERATOR,
		FACTOR,
		CONNECTOR,
		SPECIAL,
		UNDEFINED,
		TERMINATOR
	}
	
	FileReader r; 
	Scanner scanner;
	List<String> l = new ArrayList<String>();
	List<Token> tokens = new ArrayList<Token>();
	
	public AreaOfEffectLexer() {
		// TODO Auto-generated constructor stub
		try {
			r = new FileReader(dataDirectory + "spells/area_of_effect.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanner = new Scanner(r);
	}
    public static class Token {
    	TokenType t;
    	String s;
    	String normalized;
    	
    	public Token(TokenType t, String s) {
    		this.t=t;
    		this.s=s;
    	}
    	public Token(TokenType t, String s, String n) {
    		this.t=t;
    		this.s=s;
    		this.normalized = n;
    	}

    	public String toString() {
    		return this.t + ":" + this.s;
    	}
    }
    
	public void scanFile() {

		String line;
		while ( scanner.hasNextLine() ) {
			line = scanner.nextLine();
			//line =ParseUtils.replaceExtendedAsciiCharacter(189,line);
			//add spaces
			//line = line.replaceAll("/|\"|'|,"," $0 ");
			tokens.addAll(scanLine(line));
			
		}
		scanner.close();
		try {
			r.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public List<Token> scanLine(String line) {
    	String str;
    	Scanner lineScanner;
    	List<Token> tk = new ArrayList<Token>();
    	
    	//scrub-a-dub-dub
    	line =ParseUtils.replaceExtendedAsciiCharacter(189,line);
		//add spaces
		line = line.replaceAll("/|\"|'|,"," $0 ");
		
    	lineScanner = new Scanner(line);
		
		while (lineScanner.hasNext()) {
			str = lineScanner.next();
			//if (DEBUG) System.err.println(str);
			if (str.matches(num)) 
				tk.add(new Token(TokenType.NUMBER,ParseUtils.normalizeNumber(str)));
			else if (str.matches(orOperator))
				tk.add(new Token(TokenType.OPERATOR,"or"));
			else if (str.matches(dieRollOperator))
				tk.add(new Token(TokenType.OPERATOR,"-"));
			else if (str.matches(operator))
				tk.add(new Token(TokenType.OPERATOR,"+"));
			else if (str.matches(definedUnits))
				tk.add(new Token(TokenType.UNIT,str));
			else if (str.matches(definedSubjects))
				tk.add(new Token(TokenType.SUBJECT,str));
			else if (str.matches(factors))
				tk.add(new Token(TokenType.FACTOR,str));
			else if (str.matches(definedAdjectives))
				tk.add(new Token(TokenType.ADJECTIVE,str));
			else if (str.matches(connectors))
				tk.add(new Token(TokenType.CONNECTOR,str));
			else if (str.matches(special))
				tk.add(new Token(TokenType.SPECIAL,str));
			else if (str.matches(explode)) {
				tk.add(new Token(TokenType.FACTOR,"*"));
				tk.add(new Token(TokenType.NUMBER,"10"));
				tk.add(new Token(TokenType.UNIT,"feet or yards"));
			}
			else {
				tk.add(new Token(TokenType.UNDEFINED,str));
			}
		}
		lineScanner.close();
		tk.add(new Token(TokenType.TERMINATOR,"\n"));
    	return smashTokens(tk);
    }
    
    List<Token> smashTokens(List<Token> tk) {
    	
    	int i=0;
    	//System.out.println("TK-SIZE:" + tk.size());
    	if (DEBUG) tk.stream().forEach( a -> System.out.println(a.toString()));
    	//need to control the loop
    	int loopCount=1;
    	for  (;;) {
    		loopCount++;
    		//get two tokens
    		//tk.stream().forEach(a -> System.out.println("START:" + a.toString()));
    		Token current = tk.get(i);
    		Token next = tk.get(i+1);
    		if (DEBUG) System.out.println("TK-SIZE:" + tk.size());
    		if (DEBUG) System.out.println(" Loop Count: " + loopCount + "i value:" + i);
    		
    		//System.out.println(current.t + ":" + current.s + ":" +  next.t + ":" + next.s);
    		Token merge = null;
    		
    		if (( current.t == TokenType.NUMBER ) && ( next.t == TokenType.CONNECTOR)) {
    			int lookAhead = i+2;
    			if ((current.t == TokenType.NUMBER) && (tk.get(lookAhead).t == TokenType.NUMBER)) {
    			  merge = new Token(TokenType.NUMBER, current.s + " " + next.s);
    			  tk.set(i,merge);
    			  tk.remove(i+1);
    			  continue; 
    			  } else { 
    				  i++;
    				  continue;
    			  }
    		} else if (current.t == TokenType.CONNECTOR) {
    			merge = new Token(next.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ( (current.t == TokenType.ADJECTIVE) && (next.t == TokenType.CONNECTOR)) {
    			merge = new Token(current.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ( (current.t == TokenType.SUBJECT) && (next.t == TokenType.CONNECTOR)) {
    			merge = new Token(current.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ( (current.t == TokenType.NUMBER) && (next.t == TokenType.NUMBER)) {
    			merge = new Token(current.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.UNIT) && (next.t == TokenType.UNIT)) {
    			merge = new Token(next.t, (current.s + " " + next.s));
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.ADJECTIVE) && (next.t==TokenType.ADJECTIVE)) { 
    			merge = new Token(next.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.ADJECTIVE) && (next.t==TokenType.SUBJECT)) { 
    			merge = new Token(next.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.ADJECTIVE) && (next.t==TokenType.UNIT)) {
    			merge = new Token(next.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.SUBJECT) && (next.t==TokenType.SUBJECT)) {
    			merge = new Token(current.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.SUBJECT) && (next.t==TokenType.ADJECTIVE)) {
    			merge = new Token(current.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if ((current.t == TokenType.UNIT) && (next.t==TokenType.ADJECTIVE)) {
    			merge = new Token(current.t, current.s + " " + next.s);
    			tk.set(i,merge);
    			tk.remove(i+1);
    			continue;
    		} else if (next.t == TokenType.TERMINATOR) {
    	    	//s_Tk.add(current);
    			//s_Tk.add(next);
    			if (DEBUG) tk.stream().forEach(a -> System.out.println("TERM:" + a.toString()));
    			break;
    		} else {
    			i++; 
    			continue;
    		}

    	}

		return tk; //s_Tk;  //(lenTk == s_Tk.size()) ?  s_Tk : smashTokens(s_Tk);
    }
    	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AreaOfEffectLexer t = new AreaOfEffectLexer();
		if (args.length == 0) {
			t.scanFile();
			t.tokens.stream().forEach(a -> System.out.println(a.toString()));
			t.l.stream().forEach(a -> System.out.println(a.toString()));
		} else {
			String str="";
			for (String s:args) str+=(" " + s);
			t.scanLine(str);
			
		}
	}
}
