package routing;

import map.area.TerrainType;
import unitUtils.MovementType;

public class MoveabilityMatrixFactory {
	private boolean[][] moveabilityMatrix;

	public MoveabilityMatrixFactory() {
		moveabilityMatrix = new boolean[MovementType.numberOfMovementTypes][TerrainType.numberOfAreaTypes]; // number of types of units x number of types of terrain
		initMoveabilityMatrix();
	}
	
	private void initMoveabilityMatrix() {
		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		
		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		
		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;

		//moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;

		//moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TREAD.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		//moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = false;
		//moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = false;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
	}
	
	public boolean[][] getMoveabilityMatrix() {
		return moveabilityMatrix;
	}
}
