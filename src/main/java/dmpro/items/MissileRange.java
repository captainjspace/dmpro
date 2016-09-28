package dmpro.items;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MissileRange {
	//private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public enum RangeClass {

		POINTBLANK("Point Blank", 0),
		SHORT("Short" , 0),
		MEDIUM("Medium", -2),
		LONG("Long", -5);

		public String rangeName;
		public int rangeToHitModifier;

		RangeClass(String rangeName, int rangeToHitModifier) {
			this.rangeName= rangeName;
			this.rangeToHitModifier=rangeToHitModifier;

		}
	}//end enum

	Map<RangeClass,Integer> rangeMap = new HashMap<RangeClass, Integer>();
	
	public MissileRange(String s, String m, String l) {
		int shrt=-1, medium=-1,lng = -1;
		
		try {
			shrt = Integer.parseInt(s) * 10;
			medium = Integer.parseInt(m) * 10;
			lng = Integer.parseInt(l) * 10;
		} catch (NumberFormatException e) {
			//logger.log(Level.INFO, "Range not provide in full");
		}
		if (shrt > 0) {
			rangeMap.put(RangeClass.POINTBLANK, (shrt / 2));
			rangeMap.put(RangeClass.SHORT, shrt);
		}
		if (medium > 0) rangeMap.put(RangeClass.MEDIUM, medium);
		if (lng > 0 ) rangeMap.put(RangeClass.LONG, lng);
	}
}
