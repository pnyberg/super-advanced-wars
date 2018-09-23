package handlers;

import java.awt.Color;
import java.awt.Graphics;

import area.Area;
import area.TerrainType;
import heroes.Hero;
import heroes.HeroPortrait;
import point.Point;
import units.IndirectUnit;
import units.Unit;
import units.airMoving.TCopter;
import units.seaMoving.Lander;
import units.treadMoving.APC;

public class AttackRangeHandler {
	private MapDimension mapDimension;
	private boolean[][] rangeMap;

	public AttackRangeHandler(MapDimension mapDimension) {
		this.mapDimension = mapDimension;
		rangeMap = new boolean[mapDimension.width][mapDimension.height];
	}

	public boolean unitCanFire(int cursorX, int cursorY) {
		if (chosenUnit instanceof IndirectUnit) {
			return indirectUnitCanFire(cursorX, cursorY);
		} else if (chosenUnit instanceof APC
					|| chosenUnit instanceof Lander
					|| chosenUnit instanceof TCopter) {
			return false;
		}

		return directUnitCanFire(cursorX, cursorY);
	}

	private boolean indirectUnitCanFire(int cursorX, int cursorY) {
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
					Unit targetUnit = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x, y);
					if (targetUnit != null && damageHandler.validTarget(attackingUnit, targetUnit)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean directUnitCanFire(int cursorX, int cursorY) {
		Unit northernFront = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY - 1);
		Unit easternFront = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX + 1, cursorY);
		Unit southernFront = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX, cursorY + 1);
		Unit westernFront = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(cursorX - 1, cursorY);

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
				
				Unit target = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x, y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange && 
						 target != null && damageHandler.validTarget(chosenUnit, target)) {
					Point p = new Point(x, y);
					indirectUnit.addFiringLocation(p);
				}
			}
		}
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
	
	public void paintFiringCursor(Graphics g) {
		int tileSize = mapDimension.tileSize;

		int x = cursor.getX();
		int y = cursor.getY();

		int xDiff = x - chosenUnit.getPoint().getX();
		int yDiff = y - chosenUnit.getPoint().getY();

		Unit targetUnit = mapHandler.getMapGettersObject().unitGetter.getNonFriendlyUnit(x, y);

		HeroPortrait portrait = mapHandler.getHeroPortrait();
		Hero chosenHero = portrait.getHeroHandler().getCurrentHero();
		Hero targetHero = portrait.getHeroHandler().getHeroFromUnit(targetUnit);
		
		Area[][] map = mapHandler.getMap();
		TerrainType terrainType = map[x][y].getTerrainType();
//		int areaDefenceValue = mapHandler.getDefenceValue(terrainType);

		int damage = damageHandler.getNonRNGDamageValue(chosenUnit, chosenHero, targetUnit, targetHero, terrainType); 
				
		int damageFieldWidth = (damage <= 9 ? 3 * tileSize / 5 : 
									(damage <= 99 ? 4 * tileSize / 5
										: tileSize - 3));
		int damageFieldHeight = 3 * tileSize / 5;

		int paintX = x * tileSize + 2;
		int paintY = y * tileSize + 2;
		int dmgFieldX = x * tileSize; // will be changed
		int dmgFieldY = y * tileSize; // will be changed

		g.setColor(Color.black);
		g.drawOval(paintX, paintY, tileSize - 4, tileSize - 4);
		g.drawOval(paintX + 2, paintY + 2, tileSize - 8, tileSize - 8);

		g.setColor(Color.white);
		g.drawOval(paintX + 1, paintY + 1, tileSize - 6, tileSize - 6);

		if (yDiff == -1) {
			dmgFieldX += tileSize;
			dmgFieldY += -damageFieldHeight;
		} else if (xDiff == 1) {
			dmgFieldX += tileSize;
			dmgFieldY += tileSize;
		} else if (yDiff == 1) {
			dmgFieldX += -damageFieldWidth;
			dmgFieldY += tileSize;
		} else { // xDiff == -1
			dmgFieldX += -damageFieldWidth;
			dmgFieldY += -damageFieldHeight;
		}

		g.setColor(Color.red);
		g.fillRect(dmgFieldX, dmgFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.black);
		g.drawRect(dmgFieldX, dmgFieldY, damageFieldWidth, damageFieldHeight);
		g.setColor(Color.white);
		g.drawString("" + damage + "%", dmgFieldX + damageFieldWidth / 10, dmgFieldY + 2 * damageFieldHeight / 3);
	}
}
