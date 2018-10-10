package hero.heroAbilities;

public class DamageAll extends OneTimeEffect {
	private int damageFriendlyUnits;
	private int damageTeammateUnits;
	private int damageEnemyUnits;
	
	public DamageAll(int damageFriendlyUnits, int damageTeammateUnits, int damageEnemyUnits) {
		this.damageFriendlyUnits = damageFriendlyUnits;
		this.damageTeammateUnits = damageTeammateUnits;
		this.damageEnemyUnits = damageEnemyUnits;
	}
	
	public int getDamageFriendlyUnits() {
		return damageFriendlyUnits;
	}
	
	public int getDamageTeammateUnits() {
		return damageTeammateUnits;
	}
	
	public int getDamageEnemyUnits() {
		return damageEnemyUnits;
	}
}