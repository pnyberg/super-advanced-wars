package unitUtils;

public enum AttackType {
	NONE(0),
	DIRECT_ATTACK(1),
	INDIRECT_ATTACK(2);
	
	private int attackTypeIndex;

	AttackType(int attackTypeIndex) {
		this.attackTypeIndex = attackTypeIndex;
	}
	
	public int attackTypeIndex() {
		return attackTypeIndex;
	}
}