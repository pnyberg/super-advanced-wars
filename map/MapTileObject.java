package map;

import map.area.Area;
import map.buildings.Building;
import map.structures.Structure;

public class MapTileObject {
	public Area area;
	public Building building;
	public Structure structure;
	
	public MapTileObject(Area area, Building building, Structure structure) {
		this.area = area;
		this.building = building;
		this.structure = structure;
	}
}
