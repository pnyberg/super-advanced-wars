package handlers;

import point.Point;
import units.IndirectUnit;
import units.Unit;

public class AttackHandler {
	
	public AttackHandler() {
		
	}

	public void handleFiring() {
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
			Unit north = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x, y - 1);
			Unit east = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x + 1, y);
			Unit south = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x, y + 1);
			Unit west = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x - 1, y);
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

}
