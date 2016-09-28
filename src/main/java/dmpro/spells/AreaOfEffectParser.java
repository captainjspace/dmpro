package dmpro.spells;

import java.util.ArrayList;
import java.util.List;

import dmpro.spells.AreaOfEffectLexer.Token;
import dmpro.spells.AreaOfEffectLexer.TokenType;
import dmpro.utils.ParseUtils;

public class AreaOfEffectParser {
	
	AreaOfEffectLexer lex = new AreaOfEffectLexer();
	
	
	public AreaOfEffectParser() {
		// TODO Auto-generated constructor stub
		AreaOfEffectLexer.DEBUG= true;
	}
	
	public void parseTest() {
		this.lex.scanFile();
		List<Token> mTokens = new ArrayList<Token>();
		int i=0;
		while (i<this.lex.tokens.size()-1) {
			//System.out.println(this.lex.tokens.size());
			if (this.lex.tokens.size() <= 1) break;
			Token tk = this.lex.tokens.get(i++);
			mTokens.add(tk);
			if (tk.t == TokenType.TERMINATOR) {
				for (int j = 0; j>=i;j++) this.lex.tokens.remove(j);
				System.out.println("ParseTest - Requestion Parse");
				mTokens.stream().forEach( a -> System.out.println(a.toString()));
				AreaOfEffect aoe = simpleParse(mTokens);
				mTokens.removeAll(mTokens);
				System.out.println("parse test loop");
				continue;
			}
		}
		
	}
	public AreaOfEffect simpleParse(String tokens) {
		return simpleParse(lex.scanLine(tokens));
	}

	public AreaOfEffect simpleParse(List<Token> tokens) {
		AreaOfEffect a = new AreaOfEffect();
		a.getAoeTokens().addAll(tokens);
		return a;
		
	}
	// do more later - for runtime
	public AreaOfEffect calcParse(List<Token> tokens) {
		AreaOfEffect aoe = new AreaOfEffect();
		StringBuilder b = new StringBuilder();
		b.append("LEXEMES:");
		tokens.stream().forEach(a -> b.append(a.s).append(":"));
		System.out.println(b.toString());
		int i = 0;
		//process numbers and factors;
		for (;;) {
			System.out.println("parse loop");
			Token current=tokens.get(i);
			Token next=tokens.get(i+1);
			if (next.t == TokenType.TERMINATOR) break;
			int lookAhead=i+2;
			Token superNext = null;
			while (true) {
			  superNext =  tokens.get(lookAhead);
			  if (superNext.t == TokenType.TERMINATOR) break;
			  else if (superNext.t == TokenType.NUMBER) break;
			  else {
				  lookAhead++;
				  continue;
			  }
			}
			if (superNext.t == TokenType.TERMINATOR) break;
			
			if (current.t == TokenType.SPECIAL) {
				aoe.isSpecial = true;
				i++;
				continue;
				//static pattern - shoudl resolve next NUMBER
			} else if ( (current.t == TokenType.NUMBER) && (next.t == TokenType.FACTOR) && (superNext.t == TokenType.NUMBER) ) {
				if (ParseUtils.isNumber(current.s) && ParseUtils.isNumber(superNext.s)) {
					String s = current.s + next.s + superNext.s;
					System.out.println(s);
					float f = ParseUtils.expressionToFloat(s);
					//tokens.set(i,new Token(current.t , " "));
					tokens.set(i, new Token(current.t, String.valueOf(f)));
					tokens.remove(lookAhead);
					tokens.remove(i+1); 
					
					
					System.out.println("Combined Factor");
					continue;
				} else {
					//handle skips and non-numbers
					i++;
					continue;
				}
			} else {
				i++;
				continue;
			}
			//if (superNext == null) break;
		}
		tokens.stream().forEach(a -> System.out.println("PARSER:" + a.toString()));
		return aoe;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AreaOfEffectParser p = new AreaOfEffectParser();
		p.parseTest();
	}

}
