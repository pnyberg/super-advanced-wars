package handlers;

import area.TerrainType;
import units.Unit;

public class MoveabilityMatrixFactory {
	private static final int numberOfMovementTypes = Unit.numberOfMovementTypes;
	private static final int numberOfAreaTypes = TerrainType.numberOfAreaTypes;
	
	private boolean[][] moveabilityMatrix;

	public MoveabilityMatrixFactory() {
		moveabilityMatrix = new boolean[numberOfMovementTypes][numberOfAreaTypes]; // number of types of units x number of types of terrain
		initMoveabilityMatrix();
	}
	
	private void initMoveabilityMatrix() {
		moveabilityMatrix[Unit.INFANTRY][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.ROAD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.ROAD.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.PLAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.PLAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.WOOD.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.WOOD.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.MOUNTAIN.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.SHIP][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TRANSPORT][TerrainType.SEA.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.SEA.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.SHIP][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TRANSPORT][TerrainType.REEF.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.REEF.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TRANSPORT][TerrainType.SHORE.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.SHORE.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.CITY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.CITY.terrainTypeIndex()] = true;
		
		moveabilityMatrix[Unit.INFANTRY][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.FACTORY.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.FACTORY.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.SHIP][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TRANSPORT][TerrainType.PORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.PORT.terrainTypeIndex()] = true;

		moveabilityMatrix[Unit.INFANTRY][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.MECH][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.BAND][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.TIRE][TerrainType.AIRPORT.terrainTypeIndex()] = true;
		moveabilityMatrix[Unit.AIR][TerrainType.AIRPORT.terrainTypeIndex()] = true;
	}
	
	public boolean[][] getMoveabilityMatrix() {
		return moveabilityMatrix;
	}
}
