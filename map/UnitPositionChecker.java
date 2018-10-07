package map;

import units.Unit;

public class UnitPositionChecker {
	private UnitGetter unitGetter;
	
	public UnitPositionChecker(UnitGetter unitGetter) {
		this.unitGetter = unitGetter;
	}
	
	public boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = unitGetter.getFriendlyUnitExceptSelf(unit, x, y);

		if (testUnit == null) {
			return false;
		}

		return testUnit.getUnitHealth().isHurt() && testUnit.getClass().equals(unit.getClass());
	}

	public Unit getAnyUnit(int x, int y) {
		return unitGetter.getAnyUnit(x, y);
	}
}