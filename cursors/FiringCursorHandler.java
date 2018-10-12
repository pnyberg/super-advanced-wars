package cursors;

import combat.DamageHandler;
import gameObjects.ChosenObject;
import gameObjects.GameProp;
import gameObjects.MapDim;
import map.UnitGetter;
import units.Unit;

public class FiringCursorHandler {
	private GameProp gameProp;
	private Cursor cursor;
	private UnitGetter unitGetter;
	private DamageHandler damageHandler;
	
	public FiringCursorHandler(GameProp gameProp, Cursor cursor, UnitGetter unitGetter, DamageHandler damageHandler) {
		this.gameProp = gameProp;
		this.cursor = cursor;
		this.unitGetter = unitGetter;
		this.damageHandler = damageHandler;
	}

	public void moveFiringCursorClockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX() * mapDim.tileSize;
		int cursorY = cursor.getY() * mapDim.tileSize;

		int xDiff = (cursorX - unitX) / mapDim.tileSize;
		int yDiff = (cursorY - unitY) / mapDim.tileSize;

		if (xDiff == 1) {
			Unit leftDown = unitGetter.getNonFriendlyUnit(cursorX - mapDim.tileSize, cursorY + mapDim.tileSize);
			Unit leftOnly = unitGetter.getNonFriendlyUnit(cursorX - 2 * mapDim.tileSize, cursorY);
			Unit leftUp = unitGetter.getNonFriendlyUnit(cursorX - mapDim.tileSize, cursorY - mapDim.tileSize);
			if (unitY < (mapDim.getHeight() - 1) * mapDim.tileSize && leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize + 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 2, cursorY / mapDim.tileSize);
			} else if (leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize - 1);
			}
		} else if (yDiff == 1) {
			Unit upLeft = unitGetter.getNonFriendlyUnit(cursorX - 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			Unit upOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY - 2 * mapDim.tileSize);
			Unit upRight = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			if (unitX > 0 && upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize, cursorY / mapDim.tileSize - 2);
			} else if (upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize - 1);
			}
		} else if (xDiff == -1) {
			Unit rightDown = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			Unit rightOnly = unitGetter.getNonFriendlyUnit(cursorX + 2 * mapDim.tileSize, cursorY);
			Unit rightUp = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			if (unitY > 0 && rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize - 1);
			} else if (unitX < (mapDim.getWidth() - 1) * mapDim.tileSize && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 2, cursorY / mapDim.tileSize);
			} else if (rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize + 1);
			}
		} else { // yDiff == -1
			Unit downLeft = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			Unit downOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY + 2 * mapDim.tileSize);
			Unit downRight = unitGetter.getNonFriendlyUnit(cursorX - 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			if (unitX < (mapDim.getWidth() - 1) * mapDim.tileSize && downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize + 1);
			} else if (unitY < (mapDim.getHeight() - 1) * mapDim.tileSize && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize, cursorY / mapDim.tileSize + 2);
			} else if (downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize + 1);
			}
		}
	}

	public void moveFiringCursorCounterclockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX() * mapDim.tileSize;
		int cursorY = cursor.getY() * mapDim.tileSize;

		int xDiff = (cursorX - unitX) / mapDim.tileSize;
		int yDiff = (cursorY - unitY) / mapDim.tileSize;

		if (xDiff == 1) {
			Unit leftUp = unitGetter.getNonFriendlyUnit(cursorX - 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			Unit leftOnly = unitGetter.getNonFriendlyUnit(cursorX - 2 * mapDim.tileSize, cursorY);
			Unit leftDown = unitGetter.getNonFriendlyUnit(cursorX - 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			if (unitY > 0 && leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize - 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 2, cursorY / mapDim.tileSize);
			} else if (leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize + 1);
			}
		} else if (yDiff == 1) {
			Unit upRight = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			Unit upOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY - 2 * mapDim.tileSize);
			Unit upLeft = unitGetter.getNonFriendlyUnit(cursorX - 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			if (unitX < (mapDim.getWidth() - 1) * mapDim.tileSize && upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize, cursorY / mapDim.tileSize - 2);
			} else if (upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize - 1);
			}
		} else if (xDiff == -1) {
			Unit rightUp = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			Unit rightOnly = unitGetter.getNonFriendlyUnit(cursorX + 2 * mapDim.tileSize, cursorY);
			Unit rightDown = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY - 1 * mapDim.tileSize);
			if (unitY < (mapDim.getHeight() - 1) * mapDim.tileSize && rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize + 1);
			} else if (unitX < (mapDim.getWidth() - 1) * mapDim.tileSize && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 2, cursorY / mapDim.tileSize);
			} else if (rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize - 1);
			}
		} else { // yDiff == -1
			Unit downRight = unitGetter.getNonFriendlyUnit(cursorX - 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			Unit downOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY + 2 * mapDim.tileSize);
			Unit downLeft = unitGetter.getNonFriendlyUnit(cursorX + 1 * mapDim.tileSize, cursorY + 1 * mapDim.tileSize);
			if (unitX > 0 && downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX / mapDim.tileSize - 1, cursorY / mapDim.tileSize + 1);
			} else if (unitY < (mapDim.getHeight() - 1) * mapDim.tileSize && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX / mapDim.tileSize, cursorY / mapDim.tileSize + 2);
			} else if (downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX / mapDim.tileSize + 1, cursorY / mapDim.tileSize + 1);
			}
		}
	}
}