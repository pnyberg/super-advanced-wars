package combat;

import cursors.Cursor;
import gameObjects.MapDim;
import map.UnitGetter;
import point.Point;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	private MapDim mapDim;
	private UnitGetter unitGetter;
	private AttackRangeHandler attackRangeHandler;
	private DamageHandler damageHandler;
	
	public AttackHandler(MapDim mapDimension, UnitGetter unitGetter, AttackRangeHandler attackRangeHandler, DamageHandler damageHandler) {
		this.mapDim = mapDimension;
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
			Unit north = unitGetter.getNonFriendlyUnit(x, y - mapDim.tileSize);
			Unit east = unitGetter.getNonFriendlyUnit(x + mapDim.tileSize, y);
			Unit south = unitGetter.getNonFriendlyUnit(x, y + mapDim.tileSize);
			Unit west = unitGetter.getNonFriendlyUnit(x - mapDim.tileSize, y);
			if (y > 0 && north != null && damageHandler.validTarget(chosenUnit, north)) {
				y -= mapDim.tileSize;
			} else if (x < (mapDim.getWidth() - 1) * mapDim.tileSize && east != null && damageHandler.validTarget(chosenUnit, east)) {
				x += mapDim.tileSize;
			} else if (south != null && damageHandler.validTarget(chosenUnit, south)) {
				y += mapDim.tileSize;
			} else if (west != null && damageHandler.validTarget(chosenUnit, west)) {
				x -= mapDim.tileSize;
			} else {
				return; // 
			}
		}

		cursor.setPosition(x / mapDim.tileSize, y / mapDim.tileSize);
	}

	public boolean unitWantsToFire(Unit chosenUnit) {
		return chosenUnit != null && chosenUnit.isAttacking();
	}

	public AttackRangeHandler getAttackRangeHandler() {
		return attackRangeHandler;
	}
}