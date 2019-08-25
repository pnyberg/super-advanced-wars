package hero.heroAbilities;

import unitUtils.UnitType;

public class DefenceIncrease extends CommanderEffect {
	private int[] defencePercentages;
	
	public DefenceIncrease(int[] defencePercentages) {
		this.defencePercentages = defencePercentages;
	}
	
	public int getDefenceIncrease(int unitIndex) {
		if (unitIndex < 0 || unitIndex >= UnitType.numberOfUnitTypes) {
			throw new IllegalArgumentException("The number: " + unitIndex + " does not correspond to a valid unit-type!");
		}
		return defencePercentages[unitIndex];
	}
}