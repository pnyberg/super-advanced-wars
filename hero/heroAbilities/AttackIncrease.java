package hero.heroAbilities;

import unitUtils.UnitType;

public class AttackIncrease extends CommanderEffect {
	private int[] attackPercentages;
	
	public AttackIncrease(int[] attackPercentages) {
		this.attackPercentages = attackPercentages;
	}
	
	public int getAttackIncrease(int unitIndex) {
		if (unitIndex < 0 || unitIndex >= UnitType.numberOfUnitTypes) {
			throw new IllegalArgumentException("The number: " + unitIndex + " does not correspond to a valid unit-type!");
		}
		return attackPercentages[unitIndex];
	}
}