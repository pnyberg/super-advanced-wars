import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JPanel;

public class Gameboard extends JPanel implements KeyListener {
	private final int 	ROAD = 0,
						PLAIN = 1,
						WOOD = 2,
						MOUNTAIN = 3,
						SEA = 4,
						CITY = 5;

	private final int tileSize = 40;

	private int[][] map;
	private boolean[][] movementMap;
	private LinkedList<Unit> troops;

	private int mapWidth, mapHeight;

	private Cursor cursor;
	private LinkedList<Point> arrowPoints;

	private boolean unitChosen;
	private Unit chosenUnit;

	public Gameboard(int width, int height) {
		mapWidth = width;
		mapHeight = height;

		map = new int[mapWidth][mapHeight];
		movementMap = new boolean[mapWidth][mapHeight];

		troops = new LinkedList<Unit>();

		cursor = new Cursor(0, 0);
		arrowPoints = new LinkedList<Point>();

		unitChosen = false;
		chosenUnit = null;

		addKeyListener(this);

		initMap();
		initTroops();

		repaint();
	}

	public void initMap() {
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
	}

	public void initTroops() {
		troops.add(new Infantry(2, 2, Color.red));
		troops.add(new Mech(3, 3, Color.red));
		troops.add(new Infantry(7, 7, Color.orange));
	}

	public void keyPressed(KeyEvent e) {
		int cursorX = cursor.getX();
		int cursorY = cursor.getY();

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (cursorY > 0) {
				addArrowPoint(cursorX, cursorY - 1);
				cursor.moveUp();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (cursorY < (mapHeight - 1) * tileSize) {
				addArrowPoint(cursorX, cursorY + 1);
				cursor.moveDown();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (cursorX > 0) {
				addArrowPoint(cursorX - 1, cursorY);
				cursor.moveLeft();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (cursorX < (mapHeight - 1) * tileSize) {
				addArrowPoint(cursorX + 1, cursorY);
				cursor.moveRight();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (!unitChosen) {
				chosenUnit = getUnit(cursorX, cursorY);

				if (chosenUnit != null) {
					unitChosen = true;
					findPossibleMovementLocations(chosenUnit);

					arrowPoints.add(new Point(cursorX, cursorY));
				}
			} else if (movementMap[cursorX][cursorY]) {
				chosenUnit.moveTo(cursorX, cursorY);
				unitChosen = false;
				chosenUnit = null;
				movementMap = new boolean[mapWidth][mapHeight];
				arrowPoints.clear();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_B) {
			// pressing B, does nothing atm
		}

		repaint();
	}

	public Unit getUnit(int x, int y) {
		for (Unit unit : troops) {
			if (unit.getX() == x && unit.getY() == y) {
				return unit;
			}
		}
		return null;
	}

	public void findPossibleMovementLocations(Unit chosenUnit) {
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

	public void checkPath(int x, int y, int movementSteps, int movementType) {
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
	public boolean allowedMovementPosition(int x, int y, int movementType) {
		int terrainType = map[x][y];

		if (terrainType == ROAD) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
		} else if (terrainType == PLAIN) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
				return true;
			}
		} else if (terrainType == WOOD) {
			if (movementType == Unit.INFANTRY) {
				return true;
			}
			if (movementType == Unit.MECH) {
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
		}

		return false;
	}

	public int movementCost(int x, int y, int movementType) {
		int terrainType = map[x][y];

		if (terrainType == PLAIN) {
		} else if (terrainType == WOOD) {
		} else if (terrainType == MOUNTAIN) {
			if (movementType == Unit.INFANTRY) {
				return 2;
			}
		} else if (terrainType == SEA) {
		} else if (terrainType == CITY) {
		}

		return 1;
	}

	public void addArrowPoint(int newX, int newY) {
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

	public boolean invalidCurrentPath() {
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

	public void recountPath(int newX, int newY) {
		int mainX = chosenUnit.getX();
		int mainY = chosenUnit.getY();
		int movementType = chosenUnit.getMovementType();

		int diffX = newX - mainX;
		int diffY = newY - mainY;

		arrowPoints.clear();
		arrowPoints.add(new Point(mainX, mainY));


		while(Math.abs(diffX) > 0 || Math.abs(diffY) > 0) {
			int prevX = arrowPoints.getLast().getX();
			int prevY = arrowPoints.getLast().getY();

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

	public boolean newPointNotConnectedToPreviousPoint() {
		int size = arrowPoints.size();

		int x1 = arrowPoints.get(size - 2).getX();
		int y1 = arrowPoints.get(size - 2).getY();
		int x2 = arrowPoints.get(size - 1).getX();
		int y2 = arrowPoints.get(size - 1).getY();

		return Math.abs(x1 - x2) + Math.abs(y1 - y2) > 1;
	}

	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int y = 0 ; y < mapHeight ; y++) {
			for (int x = 0 ; x < mapWidth ; x++) {
				paintArea(g, x, y, map[x][y]);
			}
		}

		for (Unit unit : troops) {
			if (unit != chosenUnit) {
				unit.paint(g, tileSize);
			}
		}

		if (unitChosen) {
			paintArrow(g, tileSize);

			chosenUnit.paint(g, tileSize);
		}

		cursor.paint(g, tileSize);
	}

	private void paintArea(Graphics g, int x, int y, int areaNumber) {
		if (areaNumber == 0) {
			if (movementMap[x][y]) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(Color.gray);
			}
		} else if (areaNumber == 1) {
			if (movementMap[x][y]) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		} else if (areaNumber == 2) {
			if (movementMap[x][y]) {
				g.setColor(new Color(50,205,50)); // limegreen
			} else {
				g.setColor(new Color(0,128,0)); // green
			}
		} else if (areaNumber == 3) {
			if (movementMap[x][y]) {
				g.setColor(new Color(205,133,63)); // lighter brown
			} else {
				g.setColor(new Color(142,101,64)); // brown
			}
		} else if (areaNumber == 4) {
			if (movementMap[x][y]) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else {
			g.setColor(Color.white);
		}

		int paintX = x * tileSize;
		int paintY = y * tileSize;

		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		if (areaNumber == 5) {
			g.drawLine(paintX, paintY, paintX + tileSize, paintY + tileSize);
			g.drawLine(paintX, paintY + tileSize, paintX + tileSize, paintY);
		}
	}

	public void paintArrow(Graphics g, int tileSize) {
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
}