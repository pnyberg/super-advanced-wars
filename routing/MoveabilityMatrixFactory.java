package routing;

import map.area.TerrainType;
import unitUtils.MovementType;

public class MoveabilityMatrixFactory {
	private boolean[][] moveabilityMatrix;

	public MoveabilityMatrixFactory() {
		// number of types of units x number of types of terrain
		moveabilityMatrix = new boolean[MovementType.numberOfMovementTypes][TerrainType.numberOfAreaTypes];
		initMoveabilityMatrix();
	}
	
	// the non-moveability alternatives are commented out but shown for clearance
	private void initMoveabilityMatrix() {
		int infantryMovementTypeIndex = MovementType.INFANTRY.movementTypeIndex();
		int mechMovementTypeIndex = MovementType.MECH.movementTypeIndex();
		int treadMovementTypeIndex = MovementType.TREAD.movementTypeIndex();
		int tireMovementTypeIndex = MovementType.TIRE.movementTypeIndex();
		int shipMovementTypeIndex = MovementType.SHIP.movementTypeIndex();
		int transportMovementTypeIndex = MovementType.TRANSPORT.movementTypeIndex();
		int airMovementTypeIndex = MovementType.AIR.movementTypeIndex();
		
		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.ROAD.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.PLAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.WOOD.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		//moveabilityMatrix[treadMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[tireMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.CITY.terrainTypeIndex()] = true;
		
		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.FACTORY.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		
		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[shipMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[transportMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.PORT.terrainTypeIndex()] = true;

		//moveabilityMatrix[infantryMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = false;
		//moveabilityMatrix[mechMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = false;
		//moveabilityMatrix[treadMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = false;
		//moveabilityMatrix[tireMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = false;
		moveabilityMatrix[shipMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[transportMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.SEA.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = false;
		moveabilityMatrix[transportMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.SHOAL.terrainTypeIndex()] = true;

		//moveabilityMatrix[infantryMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = false;
		//moveabilityMatrix[mechMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = false;
		//moveabilityMatrix[treadMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = false;
		//moveabilityMatrix[tireMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = false;
		moveabilityMatrix[shipMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[transportMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.REEF.terrainTypeIndex()] = true;

		moveabilityMatrix[infantryMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[mechMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[treadMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[tireMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = true;
		//moveabilityMatrix[shipMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = false;
		//moveabilityMatrix[transportMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = false;
		moveabilityMatrix[airMovementTypeIndex][TerrainType.UMI.terrainTypeIndex()] = true;
	}
	
	public boolean[][] getMoveabilityMatrix() {
		return moveabilityMatrix;
	}
}
