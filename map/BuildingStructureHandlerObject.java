package map;

import map.buildings.BuildingHandler;
import map.structures.StructureHandler;

public class BuildingStructureHandlerObject {
	public BuildingHandler buildingHandler;
	public StructureHandler structureHandler;
	
	public BuildingStructureHandlerObject(BuildingHandler buildingHandler, StructureHandler structureHandler) {
		this.buildingHandler = buildingHandler;
		this.structureHandler = structureHandler;
	}
}
