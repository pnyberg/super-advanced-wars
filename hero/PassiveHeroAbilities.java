package hero;

public class PassiveHeroAbilities {
	private int attackValue;
	private int defenceValue;
	
	public PassiveHeroAbilities(int attackValue, int defenceValue) {
		this.attackValue = attackValue;
		this.defenceValue = defenceValue;
	}
	
	public int getAttackValue(int unitIndex) {
		return attackValue;
	}

	public int getDefenceValue(int unitIndex) {
		return defenceValue;
	}
}