package handlers;

import area.TerrainType;
import units.Unit;

public class MovementCostMatrixFactory {
	private int[][] movementCostMatrix;
	
	public MovementCostMatrixFactory() {
		initMovementCostMatrix();
	}

	private void initMovementCostMatrix() {
		// number of types of units x number of types of terrain
		movementCostMatrix = new int[MapHandler.numberOfMovementTypes][MapHandler.numberOfAreaTypes];

		for (int unitIndex = 0 ; unitIndex < movementCostMatrix.length ; unitIndex++) {
			for (int terrainIndex = 0 ; terrainIndex < movementCostMatrix[0].length ; terrainIndex++) {
				movementCostMatrix[unitIndex][terrainIndex] = 1;
			}
		}

		movementCostMatrix[Unit.TIRE][TerrainType.PLAIN.terrainTypeIndex()] = 2;
		movementCostMatrix[Unit.BAND][TerrainType.WOOD.terrainTypeIndex()] = 2;
		movementCostMatrix[Unit.TIRE][TerrainType.WOOD.terrainTypeIndex()] = 3;
		movementCostMatrix[Unit.INFANTRY][TerrainType.MOUNTAIN.terrainTypeIndex()] = 2;
		movementCostMatrix[Unit.SHIP][TerrainType.REEF.terrainTypeIndex()] = 2;
		movementCostMatrix[Unit.TRANSPORT][TerrainType.REEF.terrainTypeIndex()] = 2;
	}
	
	public int getMovementCost(int movementType, TerrainType terrainType) {
		return movementCostMatrix[movementType][terrainType.terrainTypeIndex()];
	}
}