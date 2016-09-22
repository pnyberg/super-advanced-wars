import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Handles the map
 * - contains movementcost and movementability (should perhaps be put in RouteHandler?)
 */
public class MapHandler {
	public static final int 	ROAD = 0,
								PLAIN = 1,
								WOOD = 2,
								MOUNTAIN = 3,
								SEA = 4,
								CITY = 5,
								PORT = 6,
								AIRPORT = 7,
								FACTORY = 8,
								REEF = 9;

	public static final int tileSize = 40;

	private static int[][] map, movementCostMatrix;
	private static boolean[][] moveabilityCostMatrix;
	private static HeroPortrait portrait;

	public static void initMapHandler(int mapWidth, int mapHeight) {
		initMovementCostMatrix();
		initMoveabilityMatrix();
		initMap(mapWidth, mapHeight);

		portrait = new HeroPortrait(mapWidth, mapHeight);
		initHeroes();
		initTroops();
	}

	private static void initMovementCostMatrix() {
		// number of types of units x number of types of terrain
		movementCostMatrix = new int[6][10];

		for (int unitIndex = 0 ; unitIndex < movementCostMatrix.length ; unitIndex++) {
			for (int terrainIndex = 0 ; terrainIndex < movementCostMatrix[0].length ; terrainIndex++) {
				movementCostMatrix[unitIndex][terrainIndex] = 1;
			}
		}

		movementCostMatrix[Unit.TIRE][PLAIN] = 2;
		movementCostMatrix[Unit.BAND][WOOD] = 2;
		movementCostMatrix[Unit.TIRE][WOOD] = 3;
		movementCostMatrix[Unit.INFANTRY][WOOD] = 2;
		movementCostMatrix[Unit.SHIP][REEF] = 2;
		movementCostMatrix[Unit.TRANSPORT][REEF] = 2;
	}

	private static void initMoveabilityMatrix() {
		// number of types of units x number of types of terrain
		moveabilityCostMatrix = new boolean[6][10];

		moveabilityCostMatrix[Unit.INFANTRY][ROAD] = true;
		moveabilityCostMatrix[Unit.MECH][ROAD] = true;
		moveabilityCostMatrix[Unit.BAND][ROAD] = true;
		moveabilityCostMatrix[Unit.TIRE][ROAD] = true;

		moveabilityCostMatrix[Unit.INFANTRY][PLAIN] = true;
		moveabilityCostMatrix[Unit.MECH][PLAIN] = true;
		moveabilityCostMatrix[Unit.BAND][PLAIN] = true;
		moveabilityCostMatrix[Unit.TIRE][PLAIN] = true;

		moveabilityCostMatrix[Unit.INFANTRY][WOOD] = true;
		moveabilityCostMatrix[Unit.MECH][WOOD] = true;
		moveabilityCostMatrix[Unit.BAND][WOOD] = true;
		moveabilityCostMatrix[Unit.TIRE][WOOD] = true;

		moveabilityCostMatrix[Unit.INFANTRY][MOUNTAIN] = true;
		moveabilityCostMatrix[Unit.MECH][MOUNTAIN] = true;

		moveabilityCostMatrix[Unit.SHIP][SEA] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][SEA] = true;

		moveabilityCostMatrix[Unit.SHIP][REEF] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][REEF] = true;

		moveabilityCostMatrix[Unit.INFANTRY][CITY] = true;
		moveabilityCostMatrix[Unit.MECH][CITY] = true;
		moveabilityCostMatrix[Unit.BAND][CITY] = true;
		moveabilityCostMatrix[Unit.TIRE][CITY] = true;

		moveabilityCostMatrix[Unit.INFANTRY][PORT] = true;
		moveabilityCostMatrix[Unit.MECH][PORT] = true;
		moveabilityCostMatrix[Unit.BAND][PORT] = true;
		moveabilityCostMatrix[Unit.TIRE][PORT] = true;
		moveabilityCostMatrix[Unit.SHIP][PORT] = true;
		moveabilityCostMatrix[Unit.TRANSPORT][PORT] = true;

		moveabilityCostMatrix[Unit.INFANTRY][AIRPORT] = true;
		moveabilityCostMatrix[Unit.MECH][AIRPORT] = true;
		moveabilityCostMatrix[Unit.BAND][AIRPORT] = true;
		moveabilityCostMatrix[Unit.TIRE][AIRPORT] = true;

		moveabilityCostMatrix[Unit.INFANTRY][FACTORY] = true;
		moveabilityCostMatrix[Unit.MECH][FACTORY] = true;
		moveabilityCostMatrix[Unit.BAND][FACTORY] = true;
		moveabilityCostMatrix[Unit.TIRE][FACTORY] = true;
	}

	private static void initMap(int mapWidth, int mapHeight) {
		map = new int[mapWidth][mapHeight];

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

	private static void initHeroes() {
		portrait.addHero(new Hero(0));
		portrait.addHero(new Hero(1));
		portrait.selectStartHero();
	}

	private static void initTroops() {
		portrait.getHero(0).addTroop(new Infantry(2, 2, Color.red));
		portrait.getHero(0).addTroop(new Mech(3, 3, Color.red));
		portrait.getHero(0).addTroop(new Tank(4, 4, Color.red));
		portrait.getHero(0).addTroop(new Recon(5, 5, Color.red));
		portrait.getHero(0).addTroop(new Artillery(5, 2, Color.red));
		portrait.getHero(0).addTroop(new Rocket(2, 5, Color.red));
		portrait.getHero(0).addTroop(new Battleship(1, 3, Color.red));
		portrait.getHero(0).addTroop(new APC(3, 2, Color.red));

		portrait.getHero(1).addTroop(new Infantry(7, 7, Color.orange));
	}

	public static void updatePortraitSideChoice(int cursorX, int cursorY) {
		portrait.updateSideChoice(cursorX, cursorY);
	}

	public static void changeHero() {
		portrait.nextHero();
	}

	public static Unit getFriendlyUnit(int x, int y) {
		for (int k = 0 ; k < getFriendlyTroopSize() ; k++) {
			Unit unit = getFriendlyUnit(k);
			if (unit.getX() == x && unit.getY() == y && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public static Unit getFriendlyUnitExceptSelf(Unit notUnit, int x, int y) {
		for (int k = 0 ; k < getFriendlyTroopSize() ; k++) {
			Unit unit = getFriendlyUnit(k);
			if (unit.getX() == x && unit.getY() == y && unit != notUnit && !unit.isHidden()) {
				return unit;
			}
		}

		return null;
	}

	public static boolean areaOccupiedByFriendly(Unit chosenUnit, int x, int y) {
		Unit testFriendlyUnit = getFriendlyUnit(x, y);

		if (chosenUnit == testFriendlyUnit) {
			return false;
		}

		return testFriendlyUnit != null;
	}

	/***
	 * Used to check if a positions can be moved to by a specific movement-type
	 ***/
	public static boolean allowedMovementPosition(int x, int y, int movementType) {
		int terrainType = map[x][y];

		return moveabilityCostMatrix[movementType][terrainType];
	}

	public static int movementCost(int x, int y, int movementType) {
		int terrainType = map[x][y];

		return movementCostMatrix[movementType][terrainType];
	}

	public static int map(int x, int y) {
		return map[x][y];
	}

	public static Unit getUnit(int hero, int index) {
		return portrait.getHero(hero).getTroop(index);
	}

	public static Unit getFriendlyUnit(int index) {
		return portrait.getCurrentHero().getTroop(index);
	}

	public static int getTroopSize(int hero) {
		return portrait.getHero(hero).getTroopSize();
	}

	public static int getFriendlyTroopSize() {
		return portrait.getCurrentHero().getTroopSize();
	}

	public static void paintArea(Graphics g, int x, int y, boolean rangeAble) {
		int areaNumber = map[x][y];
		boolean movementAble = RouteHandler.movementMap(x, y);

		if (areaNumber == ROAD) {
			if (movementAble) {
				g.setColor(Color.lightGray);
			} else {
				g.setColor(Color.gray);
			}
		} else if (areaNumber == PLAIN) {
			if (movementAble) {
				g.setColor(new Color(255,250,205)); // lighter yellow
			} else {
				g.setColor(new Color(204,204,0)); // darker yellow
			}
		} else if (areaNumber == WOOD) {
			if (movementAble) {
				g.setColor(new Color(50,205,50)); // limegreen
			} else {
				g.setColor(new Color(0,128,0)); // green
			}
		} else if (areaNumber == MOUNTAIN) {
			if (movementAble) {
				g.setColor(new Color(205,133,63)); // lighter brown
			} else {
				g.setColor(new Color(142,101,64)); // brown
			}
		} else if (areaNumber == SEA) {
			if (movementAble) {
				g.setColor(new Color(30,144,255)); // lighter blue
			} else {
				g.setColor(Color.blue);
			}
		} else if (areaNumber == CITY) {
			g.setColor(Color.white);
		} else if (areaNumber == REEF) {
			if (movementAble) {
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

		if (areaNumber == CITY && !rangeAble) {
			g.drawLine(paintX, paintY, paintX + tileSize, paintY + tileSize);
			g.drawLine(paintX, paintY + tileSize, paintX + tileSize, paintY);
		} else if (areaNumber == REEF && !rangeAble) {
			g.fillRect(paintX + tileSize / 4, paintY + tileSize / 4, tileSize / 4, tileSize / 4);
			g.fillRect(paintX + 5 * tileSize / 8, paintY + 5 * tileSize / 8, tileSize / 4, tileSize / 4);
		}
	}

	public static void paintUnits(Graphics g, Unit chosenUnit) {
		for (int t = 0 ; t < 2 ; t++) {
			for (int k = 0 ; k < getTroopSize(t) ; k++) {
				Unit unit = getUnit(t, k);
				if (unit != chosenUnit) {
					if (!unit.isHidden()) {
						unit.paint(g, tileSize);
					}
				}
			}
		}
	}

	public static void paintPortrait(Graphics g) {
		portrait.paint(g);
	}
}