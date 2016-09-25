import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 * TODO-list
 * - capting
 * - FOG
 * - attacking
 * - heroes
 * - sides
 * - removed recalculating route which also removes movement-control within accepted area (infantrys may take more than three steps)
 * - not crashing on recalculating route
 *
 * @TODO: substitute ArrayList with HashMap for better performance
 */
public class Gameboard extends JPanel implements KeyListener {
	private boolean[][] rangeMap;

	private int mapWidth, mapHeight;

	private Cursor cursor;

	private MapMenu mapMenu;
	private UnitMenu unitMenu;

	private Unit chosenUnit, rangeUnit;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		rangeMap = new boolean[mapWidth][mapHeight];

		cursor = new Cursor(0, 0);

		mapMenu = new MapMenu(MapHandler.tileSize);
		unitMenu = new UnitMenu(MapHandler.tileSize);

		chosenUnit = null;
		rangeUnit = null;

		addKeyListener(this);

		MapHandler.initMapHandler(mapWidth, mapHeight);
		RouteHandler.initMovementMap(mapWidth, mapHeight);

		MapHandler.updatePortraitSideChoice(cursor.getX(), cursor.getY());
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		boolean noMenuVisible = !mapMenu.isVisible() && !unitMenu.isVisible();

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (!unitIsntDroppingOff()) {
				moveDroppingOffCursorCounterclockwise();
			} else if (cursorY > 0 && noMenuVisible) {
				RouteHandler.addArrowPoint(cursorX, cursorY - 1, chosenUnit);
				cursor.moveUp();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowUp();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!unitIsntDroppingOff()) {
				moveDroppingOffCursorClockwise();
			} else if (cursorY < (mapHeight - 1) && noMenuVisible) {
				RouteHandler.addArrowPoint(cursorX, cursorY + 1, chosenUnit);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && noMenuVisible && unitIsntDroppingOff()) {
				RouteHandler.addArrowPoint(cursorX - 1, cursorY, chosenUnit);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapWidth - 1) && noMenuVisible && unitIsntDroppingOff()) {
				RouteHandler.addArrowPoint(cursorX + 1, cursorY, chosenUnit);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (!unitIsntDroppingOff()) {
				if (chosenUnit instanceof APC) {
					((APC)chosenUnit).regulateDroppingOff(false);
					Unit exitingUnit = ((APC)chosenUnit).removeUnit();
					exitingUnit.moveTo(cursor.getX(), cursor.getY());

					chosenUnit = null;
					RouteHandler.clearMovementMap();
					RouteHandler.clearArrowPoints();
				}
			} else if (mapMenu.isVisible()) {
				if (mapMenu.atEndRow()) {
					MapHandler.changeHero();
					mapMenu.closeMenu();
				}
			} else if (unitMenu.isVisible()) {
				if (unitMenu.atUnitRow()) {
					handleDroppingOff();
				}
				if (unitMenu.atEnterRow()) {
					Unit entryUnit = MapHandler.getFriendlyUnitExceptSelf(chosenUnit, cursorX, cursorY);
					if (entryUnit instanceof APC) {
						APC apc = (APC)entryUnit;

						apc.addUnit(chosenUnit);
					}
					// @TODO unit enters other unit
				}

//				chosenUnit.moveTo(cursorX, cursorY); // should work without this

				if (unitIsntDroppingOff()) {
					chosenUnit = null;
					RouteHandler.clearMovementMap();
					RouteHandler.clearArrowPoints();
				}

				unitMenu.closeMenu();
			} else if (chosenUnit == null && rangeUnit == null) {
				chosenUnit = MapHandler.getFriendlyUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					RouteHandler.findPossibleMovementLocations(chosenUnit);

					RouteHandler.initArrowPoint(chosenUnit.getX(), chosenUnit.getY());
				}
			} else if (RouteHandler.movementMap(cursorX, cursorY) && rangeUnit == null) {
				handleOpenUnitMenu(cursorX, cursorY);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (!unitIsntDroppingOff()) {
				int x = chosenUnit.getX();
				int y = chosenUnit.getY();
				cursor.setPosition(x, y);
				handleOpenUnitMenu(x, y);

				if (chosenUnit instanceof APC) {
					((APC)chosenUnit).regulateDroppingOff(false);
				}
			} else if (chosenUnit != null) {
				// the start-position of the unit before movement
				Point unitStartPoint = RouteHandler.getArrowPoint(0);
				int unitStartX = unitStartPoint.getX();
				int unitStartY = unitStartPoint.getY();

				if (unitMenu.isVisible()) {
					unitMenu.closeMenu();
					chosenUnit.moveTo(unitStartX, unitStartY);
				} else {
					cursor.setPosition(unitStartX, unitStartY);
					chosenUnit.moveTo(unitStartX, unitStartY);
					chosenUnit = null;
					RouteHandler.clearMovementMap();
					RouteHandler.clearArrowPoints();
				}
			} else if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else {
				rangeUnit = getAnyUnit(cursorX, cursorY);

				if (rangeUnit != null) {
					if (rangeUnit.getAttackType() == Unit.DIRECT_ATTACK) {
						findPossibleAttackLocations(rangeUnit);
					} else if (rangeUnit.getAttackType() == Unit.INDIRECT_ATTACK) {
						createRangeAttackLocations(rangeUnit);
					}
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else if (chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}

		MapHandler.updatePortraitSideChoice(cursor.getX(), cursor.getY());
		repaint();
	}

	public void handleDroppingOff() {
		if (chosenUnit instanceof APC) {
			((APC)chosenUnit).regulateDroppingOff(true);
		}

		int x = chosenUnit.getX();
		int y = chosenUnit.getY();

		if (y > 0) {
			y--;
		} else if (x < (mapWidth - 1)) {
			x++;
		} else {
			y++;
		}

		cursor.setPosition(x, y);
	}

	public boolean unitIsntDroppingOff() {
		if (chosenUnit instanceof APC) {
			if (((APC)chosenUnit).isDroppingOff()) {
				return false;
			}
		}

		return true;
	}

	public void moveDroppingOffCursorClockwise() {
		int unitX = chosenUnit.getX();
		int unitY = chosenUnit.getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		if (xDiff == 1) {
			if (unitY < (mapHeight - 1)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitX > 0) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (yDiff == 1) {
			if (unitX > 0) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitY > 0) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY > 0) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitX < (mapWidth - 1)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		} else { // yDiff == -1
			if (unitX < (mapWidth - 1)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		}
	}

	public void moveDroppingOffCursorCounterclockwise() {
		int unitX = chosenUnit.getX();
		int unitY = chosenUnit.getY();
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		int xDiff = cursorX - unitX;
		int yDiff = cursorY - unitY;

		Unit containedUnit = ((APC)chosenUnit).getUnit();

		int movementType = containedUnit.getMovementType();

		if (xDiff == 1) {
			if (unitY > 0 && validPosition(cursorX - 1, cursorY - 1, movementType)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			} else if (unitX > 0 && validPosition(cursorX - 2, cursorY, movementType)) {
				cursor.setPosition(cursorX - 2, cursorY);
			} else if (validPosition(cursorX - 1, cursorY + 1, movementType)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			}
		} else if (yDiff == 1) {
			if (unitX < (mapWidth - 1) && validPosition(cursorX + 1, cursorY - 1, movementType)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			} else if (unitY > 0 && validPosition(cursorX, cursorY - 2, movementType)) {
				cursor.setPosition(cursorX, cursorY - 2);
			} else if (validPosition(cursorX - 1, cursorY - 1, movementType)) {
				cursor.setPosition(cursorX - 1, cursorY - 1);
			}
		} else if (xDiff == -1) {
			if (unitY < (mapHeight - 1) && validPosition(cursorX + 1, cursorY + 1, movementType)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			} else if (unitX < (mapWidth - 1) && validPosition(cursorX + 2, cursorY, movementType)) {
				cursor.setPosition(cursorX + 2, cursorY);
			} else if (validPosition(cursorX + 1, cursorY - 1, movementType)) {
				cursor.setPosition(cursorX + 1, cursorY - 1);
			}
		} else { // yDiff == -1
			if (unitX > 0 && validPosition(cursorX - 1, cursorY + 1, movementType)) {
				cursor.setPosition(cursorX - 1, cursorY + 1);
			} else if (unitY < (mapHeight - 1) && validPosition(cursorX, cursorY + 2, movementType)) {
				cursor.setPosition(cursorX, cursorY + 2);
			} else if (validPosition(cursorX + 1, cursorY + 1, movementType)) {
				cursor.setPosition(cursorX + 1, cursorY + 1);
			}
		}
	}

	private boolean validPosition(int testX, int testY, int movementType) {
		return MapHandler.allowedMovementPosition(testX, testY, movementType);
	}

	private Unit getAnyUnit(int x, int y) {
		for (int t = 0 ; t < 2 ; t++) {
			for (int k = 0 ; k < MapHandler.getTroopSize(t) ; k++) {
				Unit unit = MapHandler.getUnit(t, k);
				if (unit.getX() == x && unit.getY() == y) {
					return unit;
				}
			}
		}

		return null;
	}

	private void handleOpenUnitMenu(int cursorX, int cursorY) {
		if (!MapHandler.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY) || unitEntryingContainerUnit(chosenUnit, cursorX, cursorY)) {
			// @TODO
			if (chosenUnit instanceof Infantry || chosenUnit instanceof Mech) {
				if (footsoldierEnterableUnitAtPosition(cursorX, cursorY)) {
					unitMenu.unitMayEnter();
				}
			} else if (chosenUnit instanceof APC) {
				// should only be allowed this when close to a friendly unit
				unitMenu.unitMaySupply();

				if (((APC)chosenUnit).isFull()) {
					Unit holdUnit = ((APC)chosenUnit).getUnit();
					unitMenu.containedCargo(holdUnit);
				}
			}

			if (hurtSameTypeUnitAtPosition(chosenUnit, cursorX, cursorY)) {
				unitMenu.unitMayJoin();
			}

			if (!MapHandler.areaOccupiedByFriendly(chosenUnit, cursorX, cursorY)) {
				unitMenu.unitMayWait();
			}

			unitMenu.openMenu(cursorX, cursorY);
			chosenUnit.moveTo(cursorX, cursorY);
		}
	}

	private boolean unitEntryingContainerUnit(Unit unit, int x, int y) {
		if (unit instanceof Infantry || unit instanceof Mech) {
			return footsoldierEnterableUnitAtPosition(x, y);
		}

		return false;
	}

	private boolean footsoldierEnterableUnitAtPosition(int x, int y) {
		if (landbasedEnterableUnitAtPosition(x, y)) {
			return true;
		}

		Unit unit = MapHandler.getFriendlyUnit(x, y);

		if (unit instanceof APC && !((APC)unit).isFull()) {
			return true;
		}

		return false;
	}

	private boolean landbasedEnterableUnitAtPosition(int x, int y) {
		return false;
	}

	private boolean hurtSameTypeUnitAtPosition(Unit unit, int x, int y) {
		Unit testUnit = MapHandler.getFriendlyUnit(x, y);

		if (testUnit == null) {
			return false;
		}

		return testUnit.isHurt() && testUnit.getClass().equals(unit.getClass());
	}

	private void findPossibleAttackLocations(Unit chosenUnit) {
		RouteHandler.findPossibleMovementLocations(chosenUnit);

		for (int n = 0 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				if (RouteHandler.movementMap(i, n)) {
					if (i > 0) {
						rangeMap[i - 1][n] = true;
					}
					if (i < (mapWidth - 1)) {
						rangeMap[i + 1][n] = true;
					}
					if (n > 0) {
						rangeMap[i][n - 1] = true;
					}
					if (n < (mapHeight - 1)) {
						rangeMap[i][n + 1] = true;
					}
				}
			}
		}

		RouteHandler.clearMovementMap();
	}

	private void createRangeAttackLocations(Unit chosenUnit) {
		IndirectUnit unit = (IndirectUnit)chosenUnit;

		int unitX = unit.getX();
		int unitY = unit.getY();
		int minRange = unit.getMinRange();
		int maxRange = unit.getMaxRange();

		for (int y = unitY - maxRange ; y <= (unitY + maxRange) ; y++) {
			if (y < 0) {
				continue;
			} else if (y >= mapHeight) {
				break;
			}
			for (int x = unitX - maxRange ; x <= (unitX + maxRange) ; x++) {
				if (x < 0) {
					continue;
				} else if (x >= mapWidth) {
					break;
				}

				int distanceFromUnit = Math.abs(unitX - x) + Math.abs(unitY - y);
				if (minRange <= distanceFromUnit && distanceFromUnit <= maxRange) {
					rangeMap[x][y] = true;
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (rangeUnit != null) {
				rangeUnit = null;
				rangeMap = new boolean[mapWidth][mapHeight];
				repaint();
			}
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y = 0 ; y < mapHeight ; y++) {
			for (int x = 0 ; x < mapWidth ; x++) {
				MapHandler.paintArea(g, x, y, rangeMap[x][y]);
			}
		}

		if (chosenUnit != null) {
			if (!unitMenu.isVisible() && !unitIsntDroppingOff()) {
				RouteHandler.paintArrow(g);
			}

			chosenUnit.paint(g, MapHandler.tileSize);
		}

		paintRange(g);

		MapHandler.paintUnits(g, chosenUnit);

		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else {
			cursor.paint(g);
		}

		MapHandler.paintPortrait(g);
	}

	private void paintRange(Graphics g) {
		int tileSize = MapHandler.tileSize;

		for (int n = 0 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
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