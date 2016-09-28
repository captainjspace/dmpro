package dmpro.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmpro.spells.AreaOfEffect;

public class ParseUtils {

	static Pattern p;
	static Matcher m;
	
	//Expressions
	static final String EMPTY ="";
	static final String numbersOnly = "[^0-9]";
	static final String numDecimal = "[^0-9].*$";
	static final String commas = ".*,.*";
	static final String operator = "[^+-/\\*]";
	static final String numWithOperator = "[^0-9.+/*-]";
	
	
	public ParseUtils() {
		// TODO Auto-generated constructor stub
	}
//http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form
	public static int getBaseDie(int min, int max) {
		int baseDie=max/min;
		int d=0;
		return 
				baseDie = (max % min == 0) ? baseDie : ParseUtils.getBaseDie(min-++d, max-d);
	}
	
	public static double eval (final String s) {
		return new Object() {
			//remember char and int are basically the same....
			int pos = -1, ch;
			
			void nextChar() {
				ch =  ( ++pos < s.length() )  ? s.charAt(pos) : -1; //end
			}
			//eat the next character
			boolean eat(int charToEat) {
				while (ch == ' ') nextChar();
				if ( ch == charToEat ) {
					nextChar();
					return true; //got what I was looking and current position in string is good 
				}
				return false; // move to next test
			}
			
			double parse() {
				nextChar();
				double x = parseExpression(); 
				//if you get back here before the end of the string - something is wrong.
				if (pos < s.length()) throw new RuntimeException ("Unexpected error - did not get to end of string" + (char)ch);
				return x;
			}
			
		    // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor
			//additional reading 
			//http://www3.cs.stonybrook.edu/~warren/xsbbook/node24.html
			
			//expressions are + -
			double parseExpression() {
				double x = parseTerm();
				for (;;) {
					if ( eat('+') ) x += parseTerm();
					else if ( eat('-') ) x -= parseTerm();
					else return x;
				}
			}
			
			//terms will either be / * or a deeper factor
			double parseTerm() {
				double x = parseFactor();
				for (;;) {
					if ( eat('/')) x /= parseFactor();
					else if ( eat('*')) x *= parseFactor();
					else return x;
				}
			}
			
			double parseFactor() {
				//eventually this happens...
				if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus
	            
				double x;
				int startPos = this.pos;
				
				if (eat('(')) { // double back for parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { 
	            	// while in numbers just keep going...
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                //not in a number any number so parse what we have.
	                x = Double.parseDouble(s.substring(startPos, this.pos));
	            } else {
	            	throw new RuntimeException("Unexpected error " + (char)ch);
	            }
				return x;
			}
		}.parse(); //very PERLish - had no idea you could deref something like this.
	}
	public static String replaceExtendedAsciiCharacter(int i, String str){
		String match="";
		String replaceWith="";
		char ch = (char)i; //get character
		switch (i) {
		case 189:
			match+=(char) i;
			replaceWith = "1/2";
			break;
		default:
			break;
		}
		//check previous string before replacing
		int y = str.indexOf(ch);
		String s="";
		while(y>0) {
			y--;
			char c = str.charAt(y);
			if ( c >= '0' && c <='9') s+=c;
			else break;
		}		
		if (!s.isEmpty()) {
			replaceWith="";
			for (int z = s.length()-1 ;z>=0; z--) {
				replaceWith += s.charAt(z);
			}
			match=replaceWith+match;
			replaceWith+=".5";
		}
		return str.replaceAll(match, replaceWith);
	}
	
	public static float expressionToFloat (final String s) {
		return new Object() {
			//remember char and int are basically the same....
			int pos = -1, ch;
			
			void nextChar() {
				ch =  ( ++pos < s.length() )  ? s.charAt(pos) : -1; //end
			}
			//eat the next character
			boolean eat(int charToEat) {
				while (ch == ' ') nextChar();
				if ( ch == charToEat ) {
					nextChar();
					return true; //got what I was looking and current position in string is good 
				}
				return false; // move to next test
			}
			
			float parse() {
				nextChar();
				float x = parseExpression(); 
				//if you get back here before the end of the string - something is wrong.
				if (pos < s.length()) throw new RuntimeException ("Unexpected error - did not get to end of string" + (char)ch);
				return x;
			}
			
		    // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor
			//additional reading 
			//http://www3.cs.stonybrook.edu/~warren/xsbbook/node24.html
			
			//expressions are + -
			float parseExpression() {
				float x = parseTerm();
				for (;;) {
					if ( eat('+') ) x += parseTerm();
					else if ( eat('-') ) x -= parseTerm();
					else return x;
				}
			}
			
			//terms will either be / * or a deeper factor
			float parseTerm() {
				float x = parseFactor();
				for (;;) {
					if ( eat('/')) x /= parseFactor();
					else if ( eat('*')) x *= parseFactor();
					else if ( eat(',')) x *= parseFactor(); //AD&D text
					else if ( eat('x')) x *= parseFactor(); //AD&D text
					else if ( eat('X')) x *= parseFactor(); //AD&D text
					else return x;
				}
			}
			
			float parseFactor() {
				//eventually this happens...
				if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus
	            
				float x;
				int startPos = this.pos;
				
				if (eat('(')) { // float back for parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { 
	            	// while in numbers just keep going...
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                //not in a number any number so parse what we have.
	                x = Float.parseFloat(s.substring(startPos, this.pos));
	            } else {
	            	throw new RuntimeException("Unexpected error " + (char)ch);
	            }
				return x;
			}
		}.parse(); //very PERLish - had no idea you could deref something like this.
	}
	
    public AreaOfEffect parseAreaofEffect(String s) {
    	AreaOfEffect areaOfEffect = new AreaOfEffect();

    	
    	return areaOfEffect;
    }
	public static void main(String[] args) {
		int ch;
		int pos=-1;
		
		
		for (String s : args){
			System.out.println(ParseUtils.eval(s));
			System.out.println(ParseUtils.expressionToFloat(s));
		}
		
	  
	}
	public static enum NumToken {
		n0("[u|U]p"),
		n1("[o|O|0]ne"),
		n2("[t|T]wo"),
		n3("[t|T]hree"),
		n4("[f|F]our"),
		n5("[f|F]ive"),
		n6("[s|S]ix"),
		n7("[s|S]even"),
		n8("[e|E]ight"),
		n9("[n|N]ine|");
		
		public final String numPattern;
		
		NumToken(String pattern) {
			this.numPattern = pattern;
		}
		
	}
	public static boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static final String num = "([0-9]+(/[0-9]+)?[\']?)|([o|O|0]ne|[t|T]wo|[t|T]hree|[f|F]our|[f|F]ive|[s|S]ix|[s|S]even|[e|E]ight|[n|N]ine|Ten)|[u|U]p|[0-9]+-[0-9]+|level[s]?|Each|minimum|more";
	public static String normalizeNumber(String str) {
		// TODO Auto-generated method stub
		if (isNumber(str)) return str; 
		else if (str.matches(num)) return parseNumberFromWord(str);
		else throw new RuntimeException("Unexpected Error@ParseUtils.normalizeNumber - Does not look translatable:" + str);
	}
	private static String parseNumberFromWord(String str) {
		// TODO Auto-generated method stub
		for (NumToken t : NumToken.values())
			if (str.matches(t.numPattern))
				return t.toString().replaceAll("[^0-9]","");
		return str;
	}

}
