package units;

import java.awt.Color;

import unitUtils.AttackType;
import unitUtils.UnitType;

public abstract class IndirectUnit extends Unit {
	protected int minimumRange;
	protected int maximumRange;

	public IndirectUnit(UnitType unitType, int x, int y, Color color, int tileSize) {
		super(unitType, x, y, color, tileSize);

		attackType = AttackType.INDIRECT_ATTACK;
	}

	public int getMinRange() {
		return minimumRange;
	}

	public int getMaxRange() {
		return maximumRange;
	}
}