import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
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
	private int[][] map;
	private boolean[][] movementMap;
	private boolean[][] rangeMap;

	private ArrayList<Unit> troops1;
	private ArrayList<Unit> troops2;
	private int teamNumber = 0;

	private int mapWidth, mapHeight;

	private Cursor cursor;
	private ArrayList<Point> arrowPoints;

	private MapMenu mapMenu;
	private UnitMenu unitMenu;

	private boolean unitChosen;
	private Unit chosenUnit, rangeUnit;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		movementMap = new boolean[mapWidth][mapHeight];
		rangeMap = new boolean[mapWidth][mapHeight];

		troops1 = new ArrayList<Unit>();
		troops2 = new ArrayList<Unit>();

		cursor = new Cursor(0, 0);
		arrowPoints = new ArrayList<Point>();

		mapMenu = new MapMenu(MapHandler.tileSize);
		unitMenu = new UnitMenu(MapHandler.tileSize);

		unitChosen = false;
		chosenUnit = null;
		rangeUnit = null;

		addKeyListener(this);

		map = MapHandler.initMap(mapWidth, mapHeight);
		initTroops();

		repaint();
	}

	private void initTroops() {
		troops1.add(new Infantry(2, 2, Color.red));
		troops1.add(new Mech(3, 3, Color.red));
		troops1.add(new Tank(4, 4, Color.red));
		troops1.add(new Recon(5, 5, Color.red));
		troops1.add(new Artillery(5, 2, Color.red));
		troops1.add(new Rocket(2, 5, Color.red));
		troops1.add(new Battleship(1, 3, Color.red));

		troops2.add(new Infantry(7, 7, Color.orange));
	}

	public void keyPressed(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (cursorY > 0 && !mapMenu.isVisible() && !unitMenu.isVisible()) {
				addArrowPoint(cursorX, cursorY - 1);
				cursor.moveUp();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowUp();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (cursorY < (mapHeight - 1) && !mapMenu.isVisible() && !unitMenu.isVisible()) {
				addArrowPoint(cursorX, cursorY + 1);
				cursor.moveDown();
			} else if (mapMenu.isVisible()) {
				mapMenu.moveArrowDown();
			} else if (unitMenu.isVisible()) {
				unitMenu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !mapMenu.isVisible() && !unitMenu.isVisible()) {
				addArrowPoint(cursorX - 1, cursorY);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapWidth - 1) && !mapMenu.isVisible() && !unitMenu.isVisible()) {
				addArrowPoint(cursorX + 1, cursorY);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (mapMenu.isVisible()) {
				// @TODO: mapMenu-option
			} else if (unitMenu.isVisible()) {
				chosenUnit.moveTo(cursorX, cursorY);
				unitChosen = false;
				chosenUnit = null;
				movementMap = new boolean[mapWidth][mapHeight];
				arrowPoints.clear();

				unitMenu.closeMenu();
			} else if (!unitChosen && rangeUnit == null) {
				chosenUnit = getUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					unitChosen = true;
					findPossibleMovementLocations(chosenUnit);

					arrowPoints.add(new Point(cursorX, cursorY));
				}
			} else if (movementMap[cursorX][cursorY] && rangeUnit == null) {
				handleOpenUnitMenu(cursorX, cursorY, teamNumber);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (chosenUnit != null) {
				if (unitMenu.isVisible()) {
					unitMenu.closeMenu();
					chosenUnit.moveTo(arrowPoints.get(0).getX(), arrowPoints.get(0).getY());
				} else {
					cursor.setPosition(arrowPoints.get(0).getX(), arrowPoints.get(0).getY());
					chosenUnit.moveTo(arrowPoints.get(0).getX(), arrowPoints.get(0).getY());
					unitChosen = false;
					chosenUnit = null;
					movementMap = new boolean[mapWidth][mapHeight];
					arrowPoints.clear();						
				}
			} else if (mapMenu.isVisible()) {
				mapMenu.closeMenu();
			} else {
				rangeUnit = getUnit(cursorX, cursorY);

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
			if (chosenUnit == null) {
				mapMenu.openMenu(cursorX, cursorY);
			}
		}

		repaint();
	}

	private Unit getUnit(int x, int y) {
		for (Unit unit : troops1) {
			if (unit.getX() == x && unit.getY() == y) {
				return unit;
			}
		}
		for (Unit unit : troops2) {
			if (unit.getX() == x && unit.getY() == y) {
				return unit;
			}
		}
		return null;
	}

	private void handleOpenUnitMenu(int cursorX, int cursorY, int teamNumber) {
		chosenUnit.moveTo(cursorX, cursorY);

		if (!areaOccupiedByFriendly(cursorX, cursorY, teamNumber)) {
			unitMenu.unitMayWait();
			unitMenu.openMenu(cursorX, cursorY);
		}
	}

	private boolean areaOccupiedByFriendly(int x, int y, int team) {
		if (team == 0) {
			for (Unit unit : troops1) {
				if (unit.getX() == x && unit.getY() == y && unit != chosenUnit) {
					return true;
				}
			}
		} else if (team == 1) {
			for (Unit unit : troops2) {
				if (unit.getX() == x && unit.getY() == y && unit != chosenUnit) {
					return true;
				}
			}
		}
		// currently no third team available

		return false;
	}

	private void findPossibleMovementLocations(Unit chosenUnit) {
		int x = chosenUnit.getX();
		int y = chosenUnit.getY();
		int movementSteps = chosenUnit.getMovement();
		int movementType = chosenUnit.getMovementType();

		movementMap[x][y] = true;

		checkPath(x + 1, y, movementSteps, movementType);
		checkPath(x, y + 1, movementSteps, movementType);
		checkPath(x - 1, y, movementSteps, movementType);
		checkPath(x, y - 1, movementSteps, movementType);
	}

	private void checkPath(int x, int y, int movementSteps, int movementType) {
		if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
			return;
		}

		if (!MapHandler.allowedMovementPosition(x, y, movementType, map[x][y])) {
			return;
		}

		movementSteps -= MapHandler.movementCost(x, y, movementType, map[x][y]);

		if (movementSteps < 0) {
			return;
		}

		movementMap[x][y] = true;

		checkPath(x + 1, y, movementSteps, movementType);
		checkPath(x, y + 1, movementSteps, movementType);
		checkPath(x - 1, y, movementSteps, movementType);
		checkPath(x, y - 1, movementSteps, movementType);
	}

	private void findPossibleAttackLocations(Unit chosenUnit) {
		findPossibleMovementLocations(chosenUnit);

		for (int n = 0 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				if (movementMap[i][n]) {
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

		movementMap = new boolean[mapWidth][mapHeight];
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

	private void addArrowPoint(int newX, int newY) {
		int newLast = -1;

		for (int i = 0 ; i < arrowPoints.size() ; i++) {
			int arrowX = arrowPoints.get(i).getX();
			int arrowY = arrowPoints.get(i).getY();

			if (arrowX == newX && arrowY == newY) {
				newLast = i;
				break;
			}
		}

		if (newLast > -1) {
			for (int i = arrowPoints.size() - 1 ; i > newLast ; i--) {
				arrowPoints.remove(i);
			}
		} else if (movementMap[newX][newY]) {
			arrowPoints.add(new Point(newX, newY));

			if (newPointNotConnectedToPreviousPoint()) {
				recountPath(newX, newY);
				// @TODO: add what happens when you make a "jump" between accepted locations
			}

			// @TODO: if movement is changed due to for example mountains, what happens?
			while (invalidCurrentPath()) {
				recountPath(newX, newY);
			}
		}
	}

	private boolean invalidCurrentPath() {
		int maximumMovement = chosenUnit.getMovement();
		int movementType = chosenUnit.getMovementType();

		int currentMovementValue = 0;

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x = arrowPoints.get(i).getX();
			int y = arrowPoints.get(i).getY();
			currentMovementValue += MapHandler.movementCost(x, y, movementType, map[x][y]);
		}

		return currentMovementValue > maximumMovement;
	}

	// @TODO: what happens if a tank want to go round a wood (U-movement) = will givet endless loop
	// @TODO: also, if (+2,0) is wood, (+3,0) is wood, if (+2,+1) is wood, (+3,+1) is wood and the 
	//        rest is road, what happens if you try to move the cursor from (+4,+2)->(+4,+1)->(+3,+1)
	//        result: will get stuck
	private void recountPath(int newX, int newY) {
		int mainX = chosenUnit.getX();
		int mainY = chosenUnit.getY();
		int movementType = chosenUnit.getMovementType();

		int diffX = newX - mainX;
		int diffY = newY - mainY;

		arrowPoints.clear();
		arrowPoints.add(new Point(mainX, mainY));


		while(Math.abs(diffX) > 0 || Math.abs(diffY) > 0) {
			int last = arrowPoints.size() - 1;
			int prevX = arrowPoints.get(last).getX();
			int prevY = arrowPoints.get(last).getY();

			if (prevX == newX && prevY == newY) {
				break;
			}

			int xAxle;
			if (Math.abs(diffX) > 0 && Math.abs(diffY) > 0) {
				xAxle = (int)(Math.random() * 10) % 2;
			} else if (Math.abs(diffX) > 0) {
				xAxle = 1;
			} else {
				xAxle = 0;
			}

			if (xAxle == 1) {
				int diff = diffX / Math.abs(diffX);
				arrowPoints.add(new Point(prevX + diff, prevY));
				int movementCost = MapHandler.movementCost(prevX + diff, prevY, movementType, map[prevX + diff][prevY]);
				diffX -= diff * movementCost;
			} else { // yAxel
				int diff = diffY / Math.abs(diffY);
				arrowPoints.add(new Point(prevX, prevY + diff));
				int movementCost = MapHandler.movementCost(prevX, prevY + diff, movementType, map[prevX][prevY + diff]);
				diffY -= diff * movementCost;
			}
		}
	}

	private boolean newPointNotConnectedToPreviousPoint() {
		int size = arrowPoints.size();

		int x1 = arrowPoints.get(size - 2).getX();
		int y1 = arrowPoints.get(size - 2).getY();
		int x2 = arrowPoints.get(size - 1).getX();
		int y2 = arrowPoints.get(size - 1).getY();

		return Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1;
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
				MapHandler.paintArea(g, x, y, map[x][y], movementMap[x][y], rangeMap[x][y]);
			}
		}

		if (unitChosen) {
			paintArrow(g, MapHandler.tileSize);

			chosenUnit.paint(g, MapHandler.tileSize);
		}

		paintRange(g, MapHandler.tileSize);

		for (Unit unit : troops1) {
			if (unit != chosenUnit) {
				unit.paint(g, MapHandler.tileSize);
			}
		}
		for (Unit unit : troops2) {
			if (unit != chosenUnit) {
				unit.paint(g, MapHandler.tileSize);
			}
		}

		// when the mapMenu is open the cursor is hidden
		if (mapMenu.isVisible()) {
			mapMenu.paint(g);
		} else if (unitMenu.isVisible()) {
			unitMenu.paint(g);
		} else {
			cursor.paint(g, MapHandler.tileSize);
		}
	}

	private void paintArrow(Graphics g, int tileSize) {
		if (arrowPoints.size() < 2) {
			return;
		}

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x1 = arrowPoints.get(i - 1).getX() * tileSize + tileSize / 2;
			int y1 = arrowPoints.get(i - 1).getY() * tileSize + tileSize / 2;
			int x2 = arrowPoints.get(i).getX() * tileSize + tileSize / 2;
			int y2 = arrowPoints.get(i).getY() * tileSize + tileSize / 2;

			g.setColor(Color.red);
			g.drawLine(x1, y1, x2, y2);
		}

		int size = arrowPoints.size();

		int xNext = arrowPoints.get(size - 2).getX() * tileSize;
		int yNext = arrowPoints.get(size - 2).getY() * tileSize;
		int xLast = arrowPoints.get(size - 1).getX() * tileSize;
		int yLast = arrowPoints.get(size - 1).getY() * tileSize;

		if (xNext == xLast) {
			if (yNext < yLast) {
				g.drawLine(xLast - 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			} else {
				g.drawLine(xLast - 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			}
		} else {
			if (xNext < xLast) {
				g.drawLine(xLast - 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast - 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			} else {
				g.drawLine(xLast + 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			}
		}
	}

	private void paintRange(Graphics g, int tileSize) {
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