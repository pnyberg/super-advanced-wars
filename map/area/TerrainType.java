package map.area;

public enum TerrainType {
	ROAD(0),
	PLAIN(1),
	WOOD(2),
	MOUNTAIN(3),
	CITY(4),
	FACTORY(5),
	AIRPORT(6),
	PORT(7),
	SEA(8),
	SHORE(9),
	REEF(10),
	UMI(11),
	MINI_CANNON(12);

	public static final int numberOfAreaTypes = 13;

	private final int terrainTypeIndex;

	TerrainType(int terrainTypeIndex) {
		this.terrainTypeIndex = terrainTypeIndex;
	}
	
	public int terrainTypeIndex() {
		return terrainTypeIndex;
	}
}