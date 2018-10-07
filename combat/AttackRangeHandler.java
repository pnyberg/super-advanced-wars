package combat;

import java.awt.Color;
import java.awt.Graphics;

import cursors.Cursor;
import cursors.FiringCursor;
import gameObjects.MapDim;
import hero.Hero;
import hero.HeroPortrait;
import map.UnitGetter;
import map.area.Area;
import map.area.TerrainType;
import point.Point;
import routing.MovementMap;
import routing.RouteChecker;
import units.IndirectUnit;
import units.Unit;
import units.airMoving.TCopter;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class AttackRangeHandler {
	private MapDim mapDimension;
	private UnitGetter unitGetter;
	private boolean[][] rangeMap;
	private DamageHandler damageHandler;
	private RouteChecker routeChecker;
	private MovementMap movementMap;

	public AttackRangeHandler(MapDim mapDimension, UnitGetter unitGetter, DamageHandler damageHandler, RouteChecker routeChecker, MovementMap movementMap) {
		this.mapDimension = mapDimension;
		this.unitGetter = unitGetter;
		rangeMap = new boolean[mapDimension.width][mapDimension.height];
		this.damageHandler = damageHandler;
		this.routeChecker = routeChecker;
		this.movementMap = movementMap;
	}
	
	public void clearRangeMap() {
		rangeMap = new boolean[mapDimension.width][mapDimension.height];
	}

	public boolean unitCanFire(Unit chosenUnit, int cursorX, int cursorY) {
		if (chosenUnit instanceof IndirectUnit) {
			return indirectUnitCanFire(chosenUnit, cursorX, cursorY);
		} else if (chosenUnit instanceof APC
					|| chosenUnit instanceof Lander
					|| chosenUnit instanceof TCopter) {
			return false;
		}

		return directUnitCanFire(chosenUnit, cursorX, cursorY);
	}

	private boolean indirectUnitCanFire(Unit chosenUnit, int cursorX, int cursorY) {
		IndirectUnit attackingUnit = (IndirectUnit)chosenUnit;

		int unitX = attackingUnit.getPoint().getX();
		int unitY = attackingUnit.getPoint().getY();
		int minRange = attackingUnit.getMinRange();
		int maxRange = attackingUnit.getMaxRange();


		if (unitX != cursorX || unitY != cursorY) {
			return false;
		}

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapDimension.height) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapDimension.width) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					Unit targetUnit = unitGetter.getNonFriendlyUnit(x, y);
					if (targetUnit != null && damageHandler.validTarget(attackingUnit, targetUnit)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean directUnitCanFire(Unit chosenUnit, int cursorX, int cursorY) {
		Unit northernFront = unitGetter.getNonFriendlyUnit(cursorX, cursorY - 1);
		Unit easternFront = unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY);
		Unit southernFront = unitGetter.getNonFriendlyUnit(cursorX, cursorY + 1);
		Unit westernFront = unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY);

		return (northernFront != null && damageHandler.validTarget(chosenUnit, northernFront)) 
			|| (easternFront != null && damageHandler.validTarget(chosenUnit, easternFront))
			|| (southernFront != null && damageHandler.validTarget(chosenUnit, southernFront))
			|| (westernFront != null && damageHandler.validTarget(chosenUnit, westernFront));
	}

	public void findPossibleDirectAttackLocations(Unit chosenUnit) {
		routeChecker.findPossibleMovementLocations(chosenUnit);

		for (int n = 0 ; n < mapDimension.height ; n++) {
			for (int i = 0 ; i < mapDimension.width ; i++) {
				if (movementMap.isAcceptedMove(i, n)) {
					if (i > 0) {
						rangeMap[i - 1][n] = true;
					}
					if (i < (mapDimension.width - 1)) {
						rangeMap[i + 1][n] = true;
					}
					if (n > 0) {
						rangeMap[i][n - 1] = true;
					}
					if (n < (mapDimension.height - 1)) {
						rangeMap[i][n + 1] = true;
					}
				}
			}
		}
		movementMap.clearMovementMap();
	}

	public void createRangeAttackLocations(Unit chosenUnit) {
		IndirectUnit unit = (IndirectUnit)chosenUnit;

		int unitX = unit.getPoint().getX();
		int unitY = unit.getPoint().getY();
		int minRange = unit.getMinRange();
		int maxRange = unit.getMaxRange();

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapDimension.height) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapDimension.width) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					rangeMap[x][y] = true;
				}
			}
		}
	}

	public void calculatePossibleAttackLocations(IndirectUnit indirectUnit) {
		int unitX = indirectUnit.getPoint().getX();
		int unitY = indirectUnit.getPoint().getY();
		int minRange = indirectUnit.getMinRange();
		int maxRange = indirectUnit.getMaxRange();

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapDimension.height) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapDimension.width) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				
				Unit target = unitGetter.getNonFriendlyUnit(x, y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange && 
						 target != null && damageHandler.validTarget(indirectUnit, target)) {
					Point p = new Point(x, y);
					indirectUnit.addFiringLocation(p);
				}
			}
		}
	}
	
	public boolean[][] getRangeMap() {
		return rangeMap;
	}
	
	public void paintRange(Graphics g) {
		int tileSize = mapDimension.tileSize;

		for (int n = 0 ; n < mapDimension.height ; n++) {
			for (int i = 0 ; i < mapDimension.width ; i++) {
				if (rangeMap[i][n]) {
					int paintX = i * tileSize;
					int paintY = n * tileSize;

					g.setColor(Color.red);
					g.fillRect(paintX, paintY, tileSize, tileSize);
					g.setColor(Color.black);
					g.drawRect(paintX, paintY, tileSize, tileSize);
				}
			}
		}
	}
}