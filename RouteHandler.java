public class RouteHandler {
	private static boolean[][] movementMap;
	private static int mapWidth, mapHeight;

	public static void initMovementMap(int mapWidth, int mapHeight) {
		RouteHandler.mapWidth = mapWidth;
		RouteHandler.mapHeight = mapHeight;
		movementMap = new boolean[mapWidth][mapHeight];
	}

	public static void clearMovementMap() {
		movementMap = new boolean[mapWidth][mapHeight];
	}

	public static void findPossibleMovementLocations(Unit chosenUnit) {
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

	private static void checkPath(int x, int y, int movementSteps, int movementType) {
		if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight) {
			return;
		}

		if (!MapHandler.allowedMovementPosition(x, y, movementType)) {
			return;
		}

		movementSteps -= MapHandler.movementCost(x, y, movementType);

		if (movementSteps < 0) {
			return;
		}

		movementMap[x][y] = true;

		checkPath(x + 1, y, movementSteps, movementType);
		checkPath(x, y + 1, movementSteps, movementType);
		checkPath(x - 1, y, movementSteps, movementType);
		checkPath(x, y - 1, movementSteps, movementType);
	}

	public static boolean movementMap(int x, int y) {
		return movementMap[x][y];
	} 
}