package map;

import map.buildings.BuildingHandler;

public class MapGettersObject {
	public final UnitGetter unitGetter;
	public final BuildingHandler buildingGetter;

	public MapGettersObject(UnitGetter unitGetter, BuildingHandler buildingGetter) {
		this.unitGetter = unitGetter;
		this.buildingGetter = buildingGetter;
	}
}