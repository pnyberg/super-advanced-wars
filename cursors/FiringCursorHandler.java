package cursors;

import combat.DamageHandler;
import gameObjects.GameProperties;
import gameObjects.MapDimension;
import map.UnitGetter;
import units.Unit;

public class FiringCursorHandler {
	private GameProperties gameProp;
	private Cursor cursor;
	private UnitGetter unitGetter;
	private DamageHandler damageHandler;
	
	public FiringCursorHandler(GameProperties gameProp, Cursor cursor, UnitGetter unitGetter, DamageHandler damageHandler) {
		this.gameProp = gameProp;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
		this.damageHandler = damageHandler;
	}

	// TODO: rewrite code to make it more readable
	public void moveFiringCursorClockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDimension mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();

		int xDiff = (cursor.getX() - unitX) / mapDim.tileSize;
		int yDiff = (cursor.getY() - unitY) / mapDim.tileSize;

		if (xDiff == 1) {
			Unit leftDown = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			Unit leftOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - 2 * mapDim.tileSize, cursor.getY());
			Unit leftUp = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			if (unitY < (mapDim.getTileHeight() - 1) * mapDim.tileSize && leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursor.getX() - 2 * mapDim.tileSize, cursor.getY());
			} else if (leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			}
		} else if (yDiff == 1) {
			Unit upLeft = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			Unit upOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY() - 2 * mapDim.tileSize);
			Unit upRight = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			if (unitX > 0 && upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursor.getX(), cursor.getY() - 2 * mapDim.tileSize);
			} else if (upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			}
		} else if (xDiff == -1) {
			Unit rightDown = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			Unit rightOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + 2 * mapDim.tileSize, cursor.getY());
			Unit rightUp = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() +  mapDim.tileSize);
			if (unitY > 0 && rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			} else if (unitX < (mapDim.getTileWidth() - 1) * mapDim.tileSize && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursor.getX() + 2 * mapDim.tileSize, cursor.getY());
			} else if (rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			}
		} else { // yDiff == -1
			Unit downLeft = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			Unit downOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY() + 2 * mapDim.tileSize);
			Unit downRight = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			if (unitX < (mapDim.getTileWidth() - 1) * mapDim.tileSize && downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			} else if (unitY < (mapDim.getTileHeight() - 1) * mapDim.tileSize && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursor.getX(), cursor.getY() + 2 * mapDim.tileSize);
			} else if (downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			}
		}
	}

	public void moveFiringCursorCounterclockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDimension mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();

		int xDiff = (cursor.getX() - unitX) / mapDim.tileSize;
		int yDiff = (cursor.getY() - unitY) / mapDim.tileSize;

		if (xDiff == 1) {
			Unit leftUp = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			Unit leftOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - 2 * mapDim.tileSize, cursor.getY());
			Unit leftDown = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			if (unitY > 0 && leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursor.getX() - 2 * mapDim.tileSize, cursor.getY());
			} else if (leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			}
		} else if (yDiff == 1) {
			Unit upRight = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			Unit upOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY() - 2 * mapDim.tileSize);
			Unit upLeft = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			if (unitX < (mapDim.getTileWidth() - 1) * mapDim.tileSize && upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursor.getX(), cursor.getY() + 2 * mapDim.tileSize);
			} else if (upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			}
		} else if (xDiff == -1) {
			Unit rightUp = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			Unit rightOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + 2 * mapDim.tileSize, cursor.getY());
			Unit rightDown = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			if (unitY < (mapDim.getTileHeight() - 1) * mapDim.tileSize && rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			} else if (unitX < (mapDim.getTileWidth() - 1) * mapDim.tileSize && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursor.getX() + 2 * mapDim.tileSize, cursor.getY());
			} else if (rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() - mapDim.tileSize);
			}
		} else { // yDiff == -1
			Unit downRight = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			Unit downOnly = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX(), cursor.getY() + 2 * mapDim.tileSize);
			Unit downLeft = unitGetter.getNonFriendlyUnitForCurrentHero(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			if (unitX > 0 && downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursor.getX() - mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			} else if (unitY < (mapDim.getTileHeight() - 1) * mapDim.tileSize && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursor.getX(), cursor.getY() + 2 * mapDim.tileSize);
			} else if (downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursor.getX() + mapDim.tileSize, cursor.getY() + mapDim.tileSize);
			}
		}
	}
}