/**
 * 
 */
package dmpro.character.classes;



/**
 * @author Joshua Landman, joshua.s.landman@gmail.com
 * created on Oct 24, 2016
 */

public enum CharacterClassType {
	
	MAGICUSER(1, "Magic-User", MagicUser.class),
	ILLUSIONIST(2, "Illusionist", Illusionist.class),
	FIGHTER(3, "Fighter",Fighter.class),
	RANGER(4, "Ranger", Ranger.class),
	PALADIN(5, "Paladin", Paladin.class),
	THIEF(6, "Thief", Thief.class),
	ASSASSIN(7, "Assassin", Assassin.class),
	CLERIC(8, "Cleric", Cleric.class),
	DRUID(9, "Druid", Druid.class),
	MONK(10,"Monk", Monk.class),
	BARD(11,"Bard", Bard.class) ;
	
	public int classIndex;
	public String className;
	Class <? extends CharacterClass> newClass;
	
	CharacterClassType(int classIndex, String className, Class <? extends CharacterClass> characterClass) {
		this.classIndex = classIndex;
		this.className = className;
		this.newClass = characterClass;
	}
	
	public CharacterClass newCharacterClass() {
		CharacterClass created = null;
		try {
			created = this.newClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return created;
	}
	
	public static CharacterClassType ByIndex(int classIndex) {
		for (CharacterClassType characterClassType : CharacterClassType.values())
			if ( classIndex == characterClassType.classIndex ) return characterClassType;
		return null;
	}
	
	public static void main (String [] args) {
		CharacterClass cc = CharacterClassType.MAGICUSER.newCharacterClass();
		System.out.println(cc.getCharacterClassType());
	}
}