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
 * - not crashing on recalculating route
 *
 * @TODO: substitute ArrayList with HashMap for better performance
 */
public class Gameboard extends JPanel implements KeyListener {
	private final int 	ROAD = 0,
						PLAIN = 1,
						WOOD = 2,
						MOUNTAIN = 3,
						SEA = 4,
						CITY = 5,
						PORT = 6,
						AIRPORT = 7,
						FACTORY = 8,
						REEF = 9;

	private final int tileSize = 40;

	private int[][] map;
	private boolean[][] movementMap;
	private boolean[][] rangeMap;

	private ArrayList<Unit> troops1;
	private ArrayList<Unit> troops2;
	private int teamNumber = 0;

	private int mapWidth, mapHeight;

	private Cursor cursor;
	private ArrayList<Point> arrowPoints;

	private MapMenu menu;

	private boolean unitChosen;
	private Unit chosenUnit, rangeUnit;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		map = new int[mapWidth][mapHeight];
		movementMap = new boolean[mapWidth][mapHeight];
		rangeMap = new boolean[mapWidth][mapHeight];

		troops1 = new ArrayList<Unit>();
		troops2 = new ArrayList<Unit>();

		cursor = new Cursor(0, 0);
		arrowPoints = new ArrayList<Point>();

		menu = new MapMenu(tileSize);

		unitChosen = false;
		chosenUnit = null;
		rangeUnit = null;

		addKeyListener(this);

		initMap();
		initTroops();

		repaint();
	}

	private void initMap() {
		for (int n = 0 ; n < 2 ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = SEA;
			}
		}

		for (int i = 0 ; i < 2 ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = SEA;
			}
		}

		map[2][2] = CITY;
		map[4][2] = PLAIN;
		map[5][2] = PLAIN;
		map[6][2] = MOUNTAIN;
		map[7][2] = MOUNTAIN;

		map[2][3] = PLAIN;
		map[7][3] = MOUNTAIN;

		map[2][4] = PLAIN;
		map[4][4] = WOOD;
		map[5][4] = WOOD;
		map[7][4] = PLAIN;

		map[2][5] = PLAIN;
		map[4][5] = WOOD;
		map[5][5] = WOOD;
		map[7][5] = PLAIN;

		map[2][6] = MOUNTAIN;
		map[7][6] = PLAIN;

		map[2][7] = MOUNTAIN;
		map[3][7] = MOUNTAIN;
		map[4][7] = PLAIN;
		map[5][7] = PLAIN;
		map[7][7] = CITY;

		for (int i = 8 ; i < mapWidth ; i++) {
			for (int n = 2 ; n < 8 ; n++) {
				map[i][n] = SEA;
			}
		}

		for (int n = 8 ; n < mapHeight ; n++) {
			for (int i = 0 ; i < mapWidth ; i++) {
				map[i][n] = SEA;
			}
		}

		map[9][0] = REEF;
		map[1][1] = REEF;
		map[8][8] = REEF;
		map[0][9] = REEF;
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
			if (cursorY > 0 && !menu.isVisible()) {
				addArrowPoint(cursorX, cursorY - 1);
				cursor.moveUp();
			} else if (menu.isVisible()) {
				menu.moveArrowUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (cursorY < (mapHeight - 1) && !menu.isVisible()) {
				addArrowPoint(cursorX, cursorY + 1);
				cursor.moveDown();
			} else if (menu.isVisible()) {
				menu.moveArrowDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0 && !menu.isVisible()) {
				addArrowPoint(cursorX - 1, cursorY);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapWidth - 1) && !menu.isVisible()) {
				addArrowPoint(cursorX + 1, cursorY);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (menu.isVisible()) {
				// @TODO: menu-option
			} else if (!unitChosen && rangeUnit == null) {
				chosenUnit = getUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					unitChosen = true;
					findPossibleMovementLocations(chosenUnit);

					arrowPoints.add(new Point(cursorX, cursorY));
				}
			} else if (movementMap[cursorX][cursorY] && rangeUnit == null) {
				if (!areaOccupiedByFriendly(cursorX, cursorY, teamNumber)) {
					chosenUnit.moveTo(cursorX, cursorY);
					unitChosen = false;
					chosenUnit = null;
					movementMap = new boolean[mapWidth][mapHeight];
					arrowPoints.clear();
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			if (chosenUnit != null) {
				cursor.setPosition(chosenUnit.getX(), chosenUnit.getY());
				unitChosen = false;
				chosenUnit = null;
				movementMap = new boolean[mapWidth][mapHeight];
				arrowPoints.clear();
			} else if (menu.isVisible()) {
				menu.closeMenu();
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
				menu.openMenu(cursor.getX(), cursor.getY());
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

		if (!allowedMovementPosition(x, y, movementType)) {
			return;
		}

		movementSteps -= movementCost(x, y, movementType);

		if (movementSteps < 0) {
			return;
		}

		movementMap[x][y] = true;

		checkPath(x + 1, y, movementSteps, movementType);
		checkPath(x, y + 1, movementSteps, movementType);
		checkPath(x - 1, y, movementSteps, movementType);
		checkPath(x, y - 1, movementSteps, movementType);
	}

	/***
	 * Used to check if a positions can be moved to by a specific movement-type
	 ***/
	private boolean allowedMovementPosition(int x, int y, int movementType) {
		int terrainType = map[x][y];

		if (terrainType == ROAD) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		} else if (terrainType == PLAIN) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		} else if (terrainType == WOOD) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		} else if (terrainType == MOUNTAIN) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
		} else if (terrainType == SEA) {
		} else if (terrainType == CITY) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		} else if (terrainType == PORT) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		} else if (terrainType == AIRPORT) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		} else if (terrainType == FACTORY) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
			if (movementType == Unit.BAND) {
				return true;
			}
			if (movementType == Unit.TIRE) {
				return true;
			}
		}

		return false;
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

	private int movementCost(int x, int y, int movementType) {
		int terrainType = map[x][y];

		if (terrainType == ROAD) {
		} else if (terrainType == PLAIN) {
			if (movementType == Unit.TIRE) {
				return 2;
			}
		} else if (terrainType == WOOD) {
			if (movementType == Unit.BAND) {
				return 2;
			}
			if (movementType == Unit.TIRE) {
				return 3;
			}
		} else if (terrainType == MOUNTAIN) {
			if (movementType == Unit.INFANTRY) {
				return 2;
			}
		} else if (terrainType == SEA) {
		} else if (terrainType == CITY) {
		} else if (terrainType == PORT) {
		} else if (terrainType == AIRPORT) {
		} else if (terrainType == FACTORY) {
		}

		return 1;
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
			currentMovementValue += movementCost(x, y, movementType);
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
				System.out.println(prevX + diff + "," + prevY + " -> " + diff);
				int movementCost = movementCost(prevX + diff, prevY, movementType);
				diffX -= diff * movementCost;
			} else { // yAxel
				int diff = diffY / Math.abs(diffY);
				arrowPoints.add(new Point(prevX, prevY + diff));
				int movementCost = movementCost(prevX, prevY + diff, movementType);
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
				paintArea(g, x, y, map[x][y]);
			}
		}

		if (unitChosen) {
			paintArrow(g, tileSize);

			chosenUnit.paint(g, tileSize);
		}

		paintRange(g, tileSize);

		for (Unit unit : troops1) {
			if (unit != chosenUnit) {
				unit.paint(g, tileSize);
			}
		}
		for (Unit unit : troops2) {
			if (unit != chosenUnit) {
				unit.paint(g, tileSize);
			}
		}

		// when the menu is open the cursor is hidden
		if (menu.isVisible()) {
			menu.paint(g);
		} else {
			cursor.paint(g, tileSize);
		}
	}

	private void paintArea(Graphics g, int x, int y, int areaNumber) {
		if (areaNumber == ROAD) {
			if (movementMap[x][y]) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(Color.gray);
			}
		} else if (areaNumber == PLAIN) {
			if (movementMap[x][y]) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		} else if (areaNumber == WOOD) {
			if (movementMap[x][y]) {
				g.setColor(new Color(50,205,50)); // limegreen
			} else {
				g.setColor(new Color(0,128,0)); // green
			}
		} else if (areaNumber == MOUNTAIN) {
			if (movementMap[x][y]) {
				g.setColor(new Color(205,133,63)); // lighter brown
			} else {
				g.setColor(new Color(142,101,64)); // brown
			}
		} else if (areaNumber == SEA) {
			if (movementMap[x][y]) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (areaNumber == CITY) {
			g.setColor(Color.white);
		} else if (areaNumber == REEF) {
			if (movementMap[x][y]) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		}

		int paintX = x * tileSize;
		int paintY = y * tileSize;

		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		if (areaNumber == CITY && !rangeMap[x][y]) {
			g.drawLine(paintX, paintY, paintX + tileSize, paintY + tileSize);
			g.drawLine(paintX, paintY + tileSize, paintX + tileSize, paintY);
		} else if (areaNumber == REEF && !rangeMap[x][y]) {
			g.fillRect(paintX + tileSize / 4, paintY + tileSize / 4, tileSize / 4, tileSize / 4);
			g.fillRect(paintX + 5 * tileSize / 8, paintY + 5 * tileSize / 8, tileSize / 4, tileSize / 4);
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