package hero.heroAbilities;

import units.UnitType;

public class DefenceIncrease extends CommanderEffect {
	private int[] defencePercents;
	
	public DefenceIncrease(int[] defencePercents) {
		this.defencePercents = defencePercents;
	}
	
	public int getDefenceIncrease(int unitIndex) {
		if (unitIndex < 0 || unitIndex >= UnitType.numberOfUnitTypes) {
			throw new IllegalArgumentException("The number: " + unitIndex + " does not correspond to a valid unit-type!");
		}
		return defencePercents[unitIndex];
	}
}