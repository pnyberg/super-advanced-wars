import java.awt.Color;
import java.awt.Graphics;

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


	public static int[][] initMap(int mapWidth, int mapHeight) {
		int[][] map = new int[mapWidth][mapHeight];

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

		return map;
	}

	/***
	 * Used to check if a positions can be moved to by a specific movement-type
	 ***/
	public static boolean allowedMovementPosition(int x, int y, int movementType, int terrainType) {
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

	public static int movementCost(int x, int y, int movementType, int terrainType) {
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

	public static void paintArea(Graphics g, int x, int y, int areaNumber, boolean movementAble, boolean rangeAble) {
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
}