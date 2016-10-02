package dmpro.spells;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dmpro.data.loaders.ResourceLoader;
import dmpro.utils.Dice;
import dmpro.utils.ParseUtils;


public class SpellFileParser implements ResourceLoader {

	String spellDir;
	String fileName;

	File file,jsonFile;
	FileReader reader;
	FileWriter writer;
	Scanner scan;
	Scanner spellScan;
	Gson gson;
	List<Spell> spellEncyclopedia = new ArrayList<Spell>();
	String spellAbilityClassName = ""; //header record for spell sections
	int spellId=1;
	AreaOfEffectLexer areaOfEffectLexer = new AreaOfEffectLexer();
	
	public SpellFileParser() {
		
		// TODO Auto-generated constructor stub
		spellDir = dataDirectory + "spells/";
		fileName = "spell-tables-scrubbed.txt";
		file = new File(spellDir + fileName);
		jsonFile = new File(spellDir + "spells.json");
		gson = new GsonBuilder().setPrettyPrinting().create();

	}

	public void parseFile() {
		try {
			reader= new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		scan = new Scanner(reader);
		scan.useDelimiter(Pattern.compile("^\\s*$", Pattern.MULTILINE));
		String s;
		int i=0;
		
		
		Pattern spellPattern = Pattern.compile("Range",Pattern.DOTALL);
		Pattern classPattern = Pattern.compile("(\\R)(CLERIC|MAGIC-USER|DRUID|ILLUSIONIST)(\\s+SPELLS.*\\R)");
		Matcher mClass;
		//String spellAbilityClassName;

		while (scan.hasNext()){
			i++; //
			s = scan.next();
			//System.out.println(">" + s + "<");
			mClass = classPattern.matcher(s);
			if (mClass.find()) {
				this.spellAbilityClassName = mClass.group(2);
				//logException(s, "ClassMatch");
			} else if (spellPattern.matcher(s).find()){ 
				//;
				//System.out.println(i + s);
				parseSpell(s);
				//if (i==10) break;
			} else {
				System.out.println("Unused Line: " + i+ "\n" + s);
			}

		}
		scan.close();
		


	}

	public void parseSpell(String s) {
		//System.out.println(s);
		Spell spell = new Spell();
		spell.spellId = this.spellId++;
		spell.spellAbilityClassName = this.spellAbilityClassName.replace("-","");
		System.out.println(spell.spellAbilityClassName); //CLASSNAME
		spell.fullSpellText = s.trim();

		String[] spellLines = s.split("\n");
		//System.out.println(spellLines[1]);
		//Pattern lastOpenParenL1 = Pattern.compile("\\(");

		//parse line 1
		String line1 = spellLines[1];
		boolean reversible = false;
		if (line1.matches(".*\\).*Reversible.*")) {
			reversible = true;
			//if (reversible) System.out.println("---REVERSIBLE");
			String temp = line1.replace("Reversible","");
			line1 = temp;
			//System.out.println(line1);
		}
		spell.reversible=reversible;


		//works in most cases

		String[] spellName = line1.split("\\(");
		spell.spellName = spellName[0].trim();
		System.out.println(spell.spellName);

		//just for parsing First Level Mag

		String spellCategory = spellName[1].replace(")","").trim();
		spell.spellCategory = spellCategory;
		System.out.println(spell.spellCategory);

		if (reversible) 
			System.out.println("Reversible");
		//return;

		//line 2
		String line2 = spellLines[2];
		String[] spellStats = line2.split("\\s\\s+");
		
		int j=0;
		//parse SpellStats
		for (String spellStat : spellStats) {
			j++;
			String[] kv = spellStat.split(":");
			System.out.println(kv[0] + "\t" + kv[1]);

			switch (j) {
			case 1: //parse level
				spell.spellAbilityClassLevel = Integer.parseInt(kv[1].trim());
				break;
			case 2: //parse spell components
				//clean this
				List<String> l = 
				Arrays.asList(kv[1].split(","));
				List<SpellComponent> sc = l.stream()
						.map(a -> new SpellComponent(a.trim()))
						.collect(Collectors.toList());
				spell.spellComponent.addAll(sc);
				//System.out.println(spell.spellComponent.toString());
				break;
			case 3: //parse range
				String rangeText = kv[1].trim();
				spell.rangeText = rangeText;
				//System.out.println(rangeText);
				
				//set booleans
				spell.isRangeTouch = (rangeText.matches("Touch"))?true:false;
				spell.isRangeSpecial = (rangeText.matches(".*[s|S]pecial"))?true:false;
				spell.isRangeInfinite = (rangeText.matches("Infinite.*"))?true:false;
				spell.isRangeUnlimited = (rangeText.matches("Unlimited"))?true:false;

				//remove AD&D distance notation
				String cRangeText = rangeText.replace("\"","").trim();
				
				

				
				//String cRangeText = rangeText;
				//System.out.println(cRangeText);

				if (!( spell.isRangeTouch || spell.isRangeInfinite 
						|| spell.isRangeSpecial || spell.isRangeUnlimited)) {
					/**
					System.out.println(spell.isRangeTouch);
					System.out.println(spell.isRangeSpecial);
					System.out.println(spell.isRangeInfinite);
					System.out.println(spell.isRangeUnlimited);
					*/
					
					//replaces print-friendly fraction 1/2 with real 1/2
					cRangeText = ParseUtils.replaceExtendedAsciiCharacter(189, cRangeText);
					
					if (cRangeText.matches("[0-9]+")) { 
						// matches standard spells with just a number
						spell.rangeBase = Float.parseFloat(cRangeText);
						
					} else if (cRangeText.matches(".*,.*")) { 
						//matches spells that have comma, maximum
						//split
						String[] max = cRangeText.split(",");
						//take max side and remove all non numbers, trim and parse
						spell.rangeMax = Float.parseFloat(max[1].replaceAll("[^0-9.]", "").trim());
					
					} else if (cRangeText.matches(".*\\+.*")) {
						// matches spells with + sign in range - base and modifier
						//i.e., 1" + 1"/level
						
						// split base and modifier on plus sign
						String [] cRangeElements = cRangeText.split("\\+");
						//for (String cR : cRangeElements) System.out.println(cR.trim());
						
						//scrub non numeric values, trim and parse
						//modifier requires expression resolution i.e, 1/4 = .25
						spell.rangeBase = Float.parseFloat(cRangeElements[0].trim()
								.replaceAll("[^0-9.]", ""));
						spell.rangeModifier = (float)ParseUtils.eval(cRangeElements[1]
								.replaceAll("/level","")
								.replaceAll("[^0-9/.]", ""));
						

					} else if (cRangeText.matches("([0-9]+/){1,2}[a-zA-Z]+")) {
						//match spells that have no base, just a modifier
						//i.e. 1"/level
						spell.rangeBase = 0.0f;
						spell.rangeModifier = 
								ParseUtils.expressionToFloat(cRangeText.split("/l")[0].trim());
						
					} else if (cRangeText.matches("\\d\\s[a-zA-Z]+")) {
						//match spells that have some additional Unit
						//3" radius
						//strip out all letters for one, and all numbers for the other
						spell.rangeBase = Float.parseFloat(cRangeText.replaceAll("[^0-9.]", "").trim());
						spell.rangeUnits = Units.valueOf(cRangeText.replaceAll("[^a-zA-Z]", "").trim());
					} else if (cRangeText.matches("[0-9]+\\s[a-zA-Z]+/[a-zA-Z]+")) {
						//match spells that have some a modifier unit only
						spell.rangeBase = 0.0f;
						spell.rangeUnits = Units.valueOf(
								cRangeText.split("/")[0].replaceAll("[^a-zA-Z]","").trim()
								);
						spell.rangeModifier = Float.parseFloat(
								cRangeText.replaceAll("[^0-9.]","").trim()
								);
						
					}
						else {
						//catch missing cases
						//cRangeText = ParseUtils.replaceExtendedAsciiCharacter(189, cRangeText);
						//System.out.println(cRangeText);
						logException(rangeText, "Range");
					}
				
				// these spells have no numerics in Range 
				// i.e., Range = Special
				} else 
					System.out.println("\t\tSpell has special range");
				break;
				
			case 4: //Casting Time --
				
				String castingTimeText = kv[1].trim();
				spell.castingTimeText = castingTimeText;

				if (castingTimeText.matches("[0-9]+([/][0-9]+)?\\s[a-zA-Z]+")) {
					//standard case - i.e., 7 segments 
					//number/number - one word unit
				  String[] castingComponents = kv[1].trim().split(" ");
				  spell.castingTime = ParseUtils.expressionToFloat(castingComponents[0]);
				  spell.castingTimeUnits = Units.valueOf(castingComponents[1].toLowerCase());
				  
				}  else if (castingTimeText.matches("[Special].*") ){
					//Text case
					//word space - plus-sign and more
					spell.isCastingTimeSpecial = true;
					
					if (castingTimeText.matches(".*\\+.*") ) {
						//split on + 1 seg
						spell.castingTimeModifiers = 
								ParseUtils.expressionToFloat(
										((castingTimeText.split("\\+"))[1].trim().split(" "))[0].trim()
								);
						spell.castingTimeUnits = 
								Units.valueOf(
										((castingTimeText.split("\\+"))[1].trim().split(" "))[1].trim()
								);
					}
				}  else if (castingTimeText.matches("[0-9]+-[0-9]+[\\+]?\\s[a-zA-Z]+")) {
					//number-number - plus-sign? - one word unit
					String[] castingComponents = kv[1].trim().split(" ");
					spell.castingTimeDie = Dice.valueOf("d" + (castingComponents[0]
							.split("-")[1])
							.replaceAll("[^0-9]",""));
					spell.castingTimeUnits = Units.valueOf(castingComponents[1]);
					spell.isCastingTimeSpecial= true;
				
				} else if (castingTimeText.matches("[0-9]+\\s[a-zA-Z]+/[a-zA-Z]+")) {
					//number-space -word/word
					//i.e., 1 round/level
					spell.castingTime = 0.0f;
					spell.castingTimeModifiers = Float.parseFloat(castingTimeText.replaceAll("[^0-9]", "").trim());
					spell.castingTimeUnits = 
							Units.valueOf(
									(castingTimeText.replaceAll("[^a-zA-Z/]","").split("/"))[0]
							);
					
					
				} else {
					
					//catch missing cases
					logException(castingTimeText, "Casting Time");
				}
				
				break;
			case 5: //Duration
				String durationText = kv[1].trim();
				spell.durationText = durationText;
				durationText = durationText.replace("melee","").trim();
				durationText = ParseUtils.replaceExtendedAsciiCharacter(189, durationText);
				System.out.println(durationText);
				
				int p=-1;
				if (durationText.matches("[0-9]+\\s+[a-zA-Z]+")) {
					//standard number - whitespace - units
					p=1;
					spell.durationBase = Float.parseFloat(durationText.replaceAll("[^0-9]","").trim());
					
					spell.durationUnits = Units.valueOf(
							durationText.replaceAll("[^a-zA-Z]","").trim()
							);
					
				} else if (durationText.matches("Permanent.*")) {
					//Permanent etc.
					p=2;
					spell.durationIsPermanent = true;
				} else if (durationText.matches("[0-9]+\\s+[a-zA-Z]+\\s\\+[\\s]*[0-9]+(/[0-9]+)?\\s[a-zA-Z]+[\\s]?/[a-zA-Z]+") ) {
					p=3;
					//Duration:1 turn + 1/2 turn/level
					String[] durationComponents = durationText.split("\\+");
					spell.durationBase = Float.parseFloat(durationComponents[0].trim().replaceAll("[^0-9]","").trim());
					spell.durationUnits = Units.valueOf(
							durationComponents[0].trim().replaceAll("[^a-zA-Z]","").trim()
							);
					spell.durationModifier = ParseUtils.expressionToFloat(
							(durationComponents[1].trim().split("\\s"))[0].trim()
							);
				} else if (durationText.matches("[0-9]+\\s+[a-zA-Z]+[\\s]?/[\\s]?[a-zA-Z]+")) {
					p=4;
					//modifier only i.e. 5 rounds/level
					spell.durationBase = 0.0f;
					spell.durationModifier = Float.parseFloat(durationText.replaceAll("[^0-9]", "").trim());
					spell.durationUnits = Units.valueOf(
							((durationText.split("/"))[0]).replaceAll("[^a-zA-Z]", "").trim()
							);
				} else if (durationText.matches("Special")) {
					p=5;
					spell.durationIsSpecial = true;
				} else if (durationText.matches("Time of chanting")) {
					p=6;
					spell.durationIsTimeofChanting = true;
				} else if (durationText.matches("Until fulfilled")) {
					p=7;
					spell.durationIsUntilFulfilled = true;
				} else if (durationText.matches("Instantaneous")) {
					p=8;
					spell.durationIsInstantaneous = true;
				} else if (durationText.matches("One touch")) {
					p=9;
					spell.durationIsTouch = true;
				} else if (durationText.matches("[0-9]+-[0-9]+\\s+[a-zA-Z]+")) {
					//dice match 2-5 rounds
					p=10;
					int max = Integer.parseInt((durationText.split("\\s")[0]).trim().split("-")[1]);
					int min = Integer.parseInt((durationText.split("\\s")[0]).trim().split("-")[0]);
					int dVal = ParseUtils.getBaseDie(min, max);
					spell.durationDie = Dice.valueOf("d" + dVal);
					spell.durationDieModifier = max % dVal;
					spell.durationDieRollMultiplier = min - spell.durationDieModifier;
					
					//get units
					
				} else if (durationText.matches("[0-9]+(/[0-9]+)?\\s[a-zA-Z]+/[a-zA-Z]+\\s\\+\\s[0-9]+-[0-9]+\\s[a-zA-Z]+")) {
					p=11;
					//Absurd pattern - Duration:1 turn/level + 1-6 turns
					spell.durationBase = 0.0f;
					spell.durationModifier = ParseUtils.expressionToFloat(
							durationText.split("\\s")[0]
								);
					//remove to handle 1/2
					/**
					spell.durationModifier = Float.parseFloat(
							durationText.split("\\+")[0].replaceAll("[^0-9]", "").trim()
					);
					*/
					
					spell.durationUnits = Units.valueOf(
							//(durationText.split("\\+")[0]).split("/")[0].replaceAll("[^a-zA-Z]", "").trim()
							durationText.split("\\s")[1].split("/")[0].trim()
							);
					
					int max = Integer.parseInt(
										(((durationText.split("\\+")[1]).trim().split("\\s")[0]).split("-")[1]).trim()
								);
					int min = Integer.parseInt(
										((durationText.split("\\+")[1]).trim().split("\\s")[0].split("-")[0]).trim()
								);
					int dVal = ParseUtils.getBaseDie(min, max);
					spell.durationDie = Dice.valueOf("d" + dVal);
					spell.durationHasBonusDie = true;
					spell.durationDieModifier = max % dVal;
					spell.durationDieRollMultiplier = min - spell.durationDieModifier;
					
				} else if (durationText.matches("[0-9]+\\s[a-zA-Z]+/[0-9]+\\s[a-zA-Z]+")) {
					p=12;
					//Duration:1 round/2 levels
					spell.durationBase = 0.0f;
					spell.durationModifier = ParseUtils.expressionToFloat(
							durationText.replaceAll("[^0-9/]","")
							);
					spell.durationUnits = Units.valueOf(
							(durationText.split("/")[0]).replaceAll("[^a-zA-Z]","").trim()
							);
				} else if (durationText.matches("[0-9]+\\s+[a-zA-Z]+\\s+\\+\\s+[0-9]+-[0-9]+\\s+[a-zA-Z]+")) {
					p=13;
					//4 rounds + 1-6 rounds
					//Fixed base plus bonus die
					spell.durationBase = Float.parseFloat(
							(durationText.split("\\+")[0]).replaceAll("[^0-9]", "").trim()
							);
					spell.durationHasBonusDie=true;
					int max = Integer.parseInt(
									durationText.split("\\+")[1].trim().split("\\s")[0].split("-")[1].trim()
								);
					int min=
								Integer.parseInt(
									durationText.split("\\+")[1].trim().split("\\s")[0].split("-")[0].trim()
							);
					int dVal = ParseUtils.getBaseDie(min, max);
					spell.durationDie = Dice.valueOf("d" + dVal);
					spell.durationDieModifier = max % dVal;
					spell.durationDieRollMultiplier = min - spell.durationDieModifier;
					
				} else if (durationText.matches("[0-9]+-[0-9]+\\s+[a-zA-Z]+\\s+\\+\\s+[0-9]+\\s+[a-zA-Z]+[\\s]?/[a-zA-Z]+")) {
					p=14;
					//2-12 rounds + 2 rounds/level
					//modifier plus bonus die
					spell.durationBase = 0.0f;
					spell.durationHasBonusDie = true;
					spell.durationUnits = Units.valueOf(durationText.split("\\s")[1].trim());
					int max = Integer.parseInt(durationText.split("\\s")[0].split("-")[1]);
					int min = Integer.parseInt(durationText.split("\\s")[0].split("-")[0]);
					int dVal = ParseUtils.getBaseDie(min, max);
					spell.durationDie = Dice.valueOf("d" + dVal);
					spell.durationDieModifier = max % dVal;
					spell.durationDieRollMultiplier = min - spell.durationDieModifier;
					spell.durationModifier = Float.parseFloat(durationText.split("\\s")[3]); //getting lazy
					
				} else if (durationText.matches("Variable")) {
					p=15;
					spell.isVariable = true;
				} else {
					logException(durationText, "Duration:Pattern:" + p);
				}
				break;
			case 6: //Saving Throw
				String savingThrowText = kv[1].trim();
				savingThrowText = ParseUtils.replaceExtendedAsciiCharacter(189, savingThrowText);
				spell.savingThrowText = savingThrowText;
				
				if (savingThrowText.matches("Neg[\\.]?")) {
					spell.savingThrowImpact = 0.0f;
				} else if (savingThrowText.matches("None")){
					spell.savingThrowImpact = 1.0f;
				} else if (savingThrowText.matches("1/2")) {
					spell.savingThrowImpact = 0.5f;
				} else if (savingThrowText.matches("Special")){
					spell.savingThrowIsSpecial=true;
				} else if (savingThrowText.matches("Neg.*1/2")){
					spell.savingThrowIsSpecial=true;
					spell.savingThrowImpact = 0.5f; 
				} else {
					logException(savingThrowText, "Saving Throw:");
				}
				
				break;
			case 7: //Area of Effect
				//This is insane 187 unique cases .... need to standardize the text
				String areaOfEffectText = kv[1].trim();
				spell.areaOfEffectText = areaOfEffectText;
				AreaOfEffectParser aoeParser = new AreaOfEffectParser();
				spell.areaOfEffect = aoeParser.simpleParse(areaOfEffectText);
		    	spell.areaOfEffect.getAoeTokens().stream().forEach( a -> System.out.println(a.toString()));
				break;
				
				
			default:
				break;

			} //end switch

		}
		//the rest
		String description="";
		for (int i=3;i<=spellLines.length-1;i++) {
			description += spellLines[i];
		}
		System.out.println(description);
		spell.description = description;
		this.spellEncyclopedia.add(spell);
		System.out.println(gson.toJson(spell));
		/**
		Matcher m;

		List<String> spellElements = new ArrayList<String>();

		String spellLine;
		int i=0;


		while (spellScan.hasNextLine()) {
			spellLine = spellScan.nextLine();
			System.out.println("---" + spellLine);
			m = lastOpenParenL1.matcher(spellLine);
			if (m.find() ) {
				spellElements.add(m.group(m.groupCount()));
			}
			System.out.println(spellLine);
			break;

		}
		 */
	}
	
	public void writeSpellFile(){
		
		
	}
	public void logException (String e, String operation) {
		for (char ch : e.toCharArray()) {
			System.out.println("Char: " + ch + ":Int: " + (int) ch);
		}
		throw new RuntimeException("Unexpected Error@" + operation + ":" + e);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpellFileParser p = new SpellFileParser();
		p.parseFile();
		p.writeSpellFile();
		//System.out.println(p.gson.toJson(p.spellEncyclopedia));
		try {
			p.writer = new FileWriter(p.jsonFile);
			p.writer.write(p.gson.toJson(p.spellEncyclopedia));
			p.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
