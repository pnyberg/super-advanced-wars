package hero;

public class AttackDefenceObject {
	private int attackValue;
	private int defenceValue;
	
	public AttackDefenceObject(int attackValue, int defenceValue) {
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