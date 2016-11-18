package dmpro.character.managementaction;

public enum CharacterManagementActions {
	INITIALIZECHARACTER(new InitializeCharacter()),
	INITIALIZESPELLBOOK(new InitializeSpellBook()),
	INITIALIZEEQUIPMENT(new InitializeEquipment()),
	INITIALIZEPROFICIENCIES( new InitializeProficiencies()),
	UPDATEXP(new UpdateExperience()),
	UPDATELEVEL(new UpdateLevel()),
	UPDATECOMBAT(new UpdateCombatTables()),
	UPDATESAVINGTHROWS(new UpdateSavingThrows()),
	UPDATEHP(new UpdateHitPoints()),
	UPDATESPELLSALLOWED(new UpdateSpellsAllowed()),
	UPDATESPELLBOOK(new UpdateSpellBook()),
	UPDATEDAILYSPELLS(new UpdateDailySpells()),
	UPDATETHIEFSKILLS(new UpdateThiefSkills()),
	EQUIPCHARACTER(new EquipCharacter()),
	SPENDGOLD(new SpendGold()),
	UPDATEPROFICIENCIES( new UpdateProficiencies()),
	ADDCHARACTERBACKGROUND( new AddCharacterBackground()),
	UPDATETURNUNDEAD(new UpdateTurnUndead()),
	ACCUMULATEMODIFIERS(new AccumulateModifiers()),
	CLASSCLEANUP(new ClassCleanUp()),
	UPDATECOMBATSTATS(new UpdateCombatStats());
	
	private ManagementAction managementAction;
	
	CharacterManagementActions(ManagementAction managementAction) {
		this.managementAction = managementAction;
	}
	
	public ManagementAction getManagementAction () {
		return this.managementAction;
	}
}
