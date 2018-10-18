package routing;

import map.GameMap;
import map.area.Area;
import map.area.TerrainType;
import units.MovementType;

public class MovementCostCalculator {
	private int[][] movementCostMatrix;
	private GameMap gameMap;

	public MovementCostCalculator(GameMap map) {
		movementCostMatrix = new MovementCostMatrixFactory().getMovementCostMatrix();
		this.gameMap = gameMap;
	}
	
	public int movementCost(int x, int y, MovementType movementType) {
		TerrainType terrainType = gameMap.getMap()[x][y].getTerrainType();
		return movementCostMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}
}