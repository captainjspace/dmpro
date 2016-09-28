package dmpro.game;

public interface CombatAction {
	
	public enum ActionType {
		MOVE,
		MISSILEATTACK,
		MELEEATTACK,
		FREEACTION,
		EQUIP,
		CASTSPELL,
		USEITEM,
		DRINKPOTION,
		READSCROLL,
		GRAPPLE;
	}
	
	void execute();

}
