package combat;

import cursors.Cursor;
import gameObjects.MapDim;
import map.UnitGetter;
import point.Point;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	private MapDim mapDimension;
	private UnitGetter unitGetter;
	private AttackRangeHandler attackRangeHandler;
	private DamageHandler damageHandler;
	
	public AttackHandler(MapDim mapDimension, UnitGetter unitGetter, AttackRangeHandler attackRangeHandler, DamageHandler damageHandler) {
		this.mapDimension = mapDimension;
		this.unitGetter = unitGetter;
		this.attackRangeHandler = attackRangeHandler;
		this.damageHandler = damageHandler;
	}

	public void handleFiring(Unit chosenUnit, Cursor cursor) {
		chosenUnit.regulateAttack(true);

		int x = chosenUnit.getPoint().getX();
		int y = chosenUnit.getPoint().getY();

		if (chosenUnit instanceof IndirectUnit) {
			IndirectUnit indirectUnit = (IndirectUnit)chosenUnit;
			attackRangeHandler.calculatePossibleAttackLocations(indirectUnit);
			Point p = indirectUnit.getNextFiringLocation();
			x = p.getX();
			y = p.getY();
		} else {
			Unit north = unitGetter.getNonFriendlyUnit(x, y - 1);
			Unit east = unitGetter.getNonFriendlyUnit(x + 1, y);
			Unit south = unitGetter.getNonFriendlyUnit(x, y + 1);
			Unit west = unitGetter.getNonFriendlyUnit(x - 1, y);
			if (y > 0 && north != null && damageHandler.validTarget(chosenUnit, north)) {
				y--;
			} else if (x < (mapDimension.width - 1) && east != null && damageHandler.validTarget(chosenUnit, east)) {
				x++;
			} else if (south != null && damageHandler.validTarget(chosenUnit, south)) {
				y++;
			} else if (west != null && damageHandler.validTarget(chosenUnit, west)) {
				x--;
			} else {
				return; // cannot drop unit off anywhere
			}
		}

		cursor.setPosition(x, y);
	}

	public boolean unitWantsToFire(Unit chosenUnit) {
		return chosenUnit != null && chosenUnit.isAttacking();
	}

	public AttackRangeHandler getAttackRangeHandler() {
		return attackRangeHandler;
	}
}