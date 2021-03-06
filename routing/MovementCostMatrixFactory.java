package routing;

import map.area.TerrainType;
import unitUtils.MovementType;

public class MovementCostMatrixFactory {
	private int[][] movementCostMatrix;
	
	public MovementCostMatrixFactory() {
		// number of types of units x number of types of terrain
		movementCostMatrix = new int[MovementType.numberOfMovementTypes][TerrainType.numberOfAreaTypes];
		initMovementCostMatrix();
	}

	private void initMovementCostMatrix() {
		// set all costs initially to 1
		for (int unitIndex = 0 ; unitIndex < movementCostMatrix.length ; unitIndex++) {
			for (int terrainIndex = 0 ; terrainIndex < movementCostMatrix[0].length ; terrainIndex++) {
				movementCostMatrix[unitIndex][terrainIndex] = 1;
			}
		}

		// movement-costs higher than 1
		movementCostMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = 2;
		movementCostMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = 2;
		movementCostMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = 3;
		movementCostMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = 2;
		movementCostMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = 2;
		movementCostMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = 2;
	}
	
	public int[][] getMovementCostMatrix() {
		return movementCostMatrix;
	}
}