package dmpro.character.managementaction;

public enum CharacterManagementActions {
	INITIALIZECHARACTER(new InitializeCharacter()),
	UPDATEXP(new UpdateExperience()),
	UPDATELEVEL(new UpdateLevel()),
	UPDATECOMBAT(new UpdateCombatTables()),
	UPDATESAVING(new UpdateSavingThrows()),
	UPDATEHP(new UpdateHitPoints()),
	UPDATESPELLSALLOWED(new UpdateSpellsAllowed()),
	UPDATESPELLBOOK(new UpdateSpellBook()),
	UPDATEDAILYSPELLS(new UpdateDailySpells()),
	UPDATETHIEFSKILLS(new UpdateThiefSkills()),
	INITIALIZESPELLBOOK(new InitializeSpellBook()),
	INTIAILIZEEQUIPMENT(new InitializeEquipment()),
	EQUIPCHARACTER(new EquipCharacter()),
	SPENDGOLD(new SpendGold()),
	INITIALIZEPROFICIENCIES( new InitializeProficiencies()),
	UPDATEPROFICIENCIES( new UpdateProficiencies()),
	ADDCHARACTERBACKGROUND( new AddCharacterBackground()),
	UPDATETURNUNDEAD(new UpdateTurnUndead());
	
	private ManagementAction managementAction;
	
	CharacterManagementActions(ManagementAction managementAction) {
		this.managementAction = managementAction;
	}
	
	public ManagementAction getManagementAction () {
		return this.managementAction;
	}
}
