package dmpro.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmpro.spells.AreaOfEffectLexer.Token;
import dmpro.utils.Dice;

public class AreaOfEffect {
	
	String effectType; //creature, area  refactor
	
	boolean creature; //single creature
	boolean numberOfCreatures; //multi creature
	boolean numberOfCreatureslevelModifier; //creatures per level
	boolean isSpecial;
	private List<Token> aoeTokens = new ArrayList<Token>();

	Map<String,Float> dimensions= new HashMap<String,Float>();
	int dimensionality; //point,linear,planar,three-dimensional
	
	float modifiers;
	Dice die;
	Dice modifierDie;
	boolean hasBonusDie;
	
	String shape; //circle, square, cube
	int x;
	int y; 
	int z;
	int d;
	int r;
	int levelDimensionMultiplier; // 1x1 per level, etc...
	public List<Token> getAoeTokens() {
		return aoeTokens;
	}
	public void setAoeTokens(List<Token> aoeTokens) {
		this.aoeTokens = aoeTokens;
	}
}
