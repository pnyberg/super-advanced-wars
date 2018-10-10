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
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		if (xDiff == 1) {
			Unit leftDown = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			Unit leftOnly = unitGetter.getNonFriendlyUnit(cursorX - 2, cursorY);
			Unit leftUp = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			if (unitY < (mapDim.height - 1) && leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			Unit upLeft = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			Unit upOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY - 2);
			Unit upRight = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			if (unitX > 0 && upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			Unit rightDown = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			Unit rightOnly = unitGetter.getNonFriendlyUnit(cursorX + 2, cursorY);
			Unit rightUp = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			if (unitY > 0 && rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapDim.width - 1) && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			Unit downLeft = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			Unit downOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY + 2);
			Unit downRight = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			if (unitX < (mapDim.width - 1) && downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapDim.height - 1) && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveFiringCursorCounterclockwise() {
		Unit chosenUnit = gameProp.getChosenObject().chosenUnit;
		MapDim mapDim = gameProp.getMapDim();
		int unitX = chosenUnit.getPoint().getX();
		int unitY = chosenUnit.getPoint().getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		if (xDiff == 1) {
			Unit leftUp = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			Unit leftOnly = unitGetter.getNonFriendlyUnit(cursorX - 2, cursorY);
			Unit leftDown = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			if (unitY > 0 && leftUp != null && damageHandler.validTarget(chosenUnit, leftUp)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && leftOnly != null && damageHandler.validTarget(chosenUnit, leftOnly)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (leftDown != null && damageHandler.validTarget(chosenUnit, leftDown)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			Unit upRight = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			Unit upOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY - 2);
			Unit upLeft = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY - 1);
			if (unitX < (mapDim.width - 1) && upRight != null && damageHandler.validTarget(chosenUnit, upRight)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && upOnly != null && damageHandler.validTarget(chosenUnit, upOnly)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (upLeft != null && damageHandler.validTarget(chosenUnit, upLeft)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			Unit rightUp = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			Unit rightOnly = unitGetter.getNonFriendlyUnit(cursorX + 2, cursorY);
			Unit rightDown = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY - 1);
			if (unitY < (mapDim.height - 1) && rightUp != null && damageHandler.validTarget(chosenUnit, rightUp)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapDim.width - 1) && rightOnly != null && damageHandler.validTarget(chosenUnit, rightOnly)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (rightDown != null && damageHandler.validTarget(chosenUnit, rightDown)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			Unit downRight = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY + 1);
			Unit downOnly = unitGetter.getNonFriendlyUnit(cursorX, cursorY + 2);
			Unit downLeft = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY + 1);
			if (unitX > 0 && downRight != null && damageHandler.validTarget(chosenUnit, downRight)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapDim.height - 1) && downOnly != null && damageHandler.validTarget(chosenUnit, downOnly)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (downLeft != null && damageHandler.validTarget(chosenUnit, downLeft)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}
}