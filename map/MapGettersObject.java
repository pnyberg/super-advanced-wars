package map;

public class MapGettersObject {
	public final UnitGetter unitGetter;
	public final BuildingGetter buildingGetter;

	public MapGettersObject(UnitGetter unitGetter, BuildingGetter buildingGetter) {
		this.unitGetter = unitGetter;
		this.buildingGetter = buildingGetter;
	}
}