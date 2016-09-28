package dmpro.character;

public enum Alignment {
	/**
	 * limited value most characters I've ever played choose good and fight evil
	 * does have a language component
	 * 
	 */
	
	LAWFUL_GOOD("Lawful Good"),
	LAWFUL_NEUTRAL("Lawful Neutral"),
	LAWFUL_EVIL("Lawful Evil"),
	CHAOTIC_GOOD("Chaotic Good"),
	CHAOTIC_NEUTRAL("Chaotic Netural"),
	CHAOTIC_EVIL("Chaotic Evil"),
	NEUTRAL("Neutral");	
	
	private String alignment;
	
	Alignment(String alignment) {
		this.alignment = alignment;
	}
	public String alignment() {
		return alignment;
	}
	
}
