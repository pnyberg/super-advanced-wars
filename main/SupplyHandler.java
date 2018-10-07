package main;

import map.UnitGetter;
import units.Unit;

public class SupplyHandler {
	private UnitGetter unitGetter;

	public SupplyHandler(UnitGetter unitGetter) {
		this.unitGetter = unitGetter;
	}
	
	public void replentishUnit(Unit unit) {
		if (unit != null) {
			unit.getUnitSupply().replentish();
		}
	}

	public boolean mayAPCSUpply(int x, int y) {
		if (unitGetter.getFriendlyUnit(x + 1, y) != null) {
			return true;
		} else if (unitGetter.getFriendlyUnit(x, y + 1) != null) {
			return true;
		} else if (unitGetter.getFriendlyUnit(x - 1, y) != null) {
			return true;
		} else if (unitGetter.getFriendlyUnit(x, y - 1) != null) {
			return true;
		}

		return false;
	}
}