package routing;

import map.area.Area;
import map.area.TerrainType;
import units.MovementType;

public class MovementCostCalculator {
	private int[][] movementCostMatrix;
	private Area[][] map;

	public MovementCostCalculator(Area[][] map) {
		movementCostMatrix = new MovementCostMatrixFactory().getMovementCostMatrix();
		this.map = map;
	}
	
	public int movementCost(int x, int y, MovementType movementType) {
		TerrainType terrainType = map[x][y].getTerrainType();
		return movementCostMatrix[movementType.movementTypeIndex()][terrainType.terrainTypeIndex()];
	}
}