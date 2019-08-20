package map;

import gameObjects.GameState;
import gameObjects.MapDimension;
import map.buildings.BuildingHandler;
import map.structures.StructureHandler;

public class BuildingStructureHandlerObject {
	public BuildingHandler buildingHandler;
	public StructureHandler structureHandler;
	
	public BuildingStructureHandlerObject(GameState gameState, MapDimension mapDimension) {
		buildingHandler = new BuildingHandler(gameState);
		structureHandler = new StructureHandler(gameState, mapDimension);
	}
}
