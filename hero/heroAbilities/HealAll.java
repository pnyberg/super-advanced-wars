package hero.heroAbilities;

public class HealAll extends OneTimeEffect {
	private int healFriendlyUnits;
	private int healTeammateUnits;
	private int healEnemyUnits;
	
	public HealAll(int healFriendlyUnits, int healTeammateUnits, int healEnemyUnits) {
		this.healFriendlyUnits = healFriendlyUnits;
		this.healTeammateUnits = healTeammateUnits;
		this.healEnemyUnits = healEnemyUnits;
	}
	
	public int getHealFriendlyUnits() {
		return healFriendlyUnits;
	}
	
	public int getHealTeammateUnits() {
		return healTeammateUnits;
	}
	
	public int getHealEnemyUnits() {
		return healEnemyUnits;
	}
}