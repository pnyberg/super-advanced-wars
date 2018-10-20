package routing;

import map.area.TerrainType;
import units.MovementType;

public class MoveabilityMatrixFactory {
	private boolean[][] moveabilityMatrix;

	public MoveabilityMatrixFactory() {
		moveabilityMatrix = new boolean[MovementType.numberOfMovementTypes][TerrainType.numberOfAreaTypes]; // number of types of units x number of types of terrain
		initMoveabilityMatrix();
	}
	
	private void initMoveabilityMatrix() {
		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.ROAD.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.PLAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.WOOD.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.CITY.terrainTypeIndex()] = true;
		
		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.FACTORY.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		
		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.PORT.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.SEA.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.SHOAL.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.SHIP.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TRANSPORT.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.REEF.terrainTypeIndex()] = true;

		moveabilityMatrix[MovementType.INFANTRY.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.MECH.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.BAND.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.TIRE.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
		moveabilityMatrix[MovementType.AIR.movementTypeIndex()][TerrainType.UMI.terrainTypeIndex()] = true;
	}
	
	public boolean[][] getMoveabilityMatrix() {
		return moveabilityMatrix;
	}
}
