package routing;

import map.GameMap;
import map.area.TerrainType;
import unitUtils.MovementType;

public class MovementCostCalculator {
	private int[][] movementCostMatrix;
	private GameMap gameMap;

	public MovementCostCalculator(GameMap gameMap) {
		movementCostMatrix = new MovementCostMatrixFactory().getMovementCostMatrix();
		this.gameMap = gameMap;
	}
	
	public int movementCost(int tileX, int tileY, MovementType movementType) {
		TerrainType terrainType = gameMap.getMap()[tileX][tileY].getTerrainType();
		return movementCostMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}
}