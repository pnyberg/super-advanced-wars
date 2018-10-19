package map.area;

public enum TerrainType {
	ROAD(0),
	PLAIN(1),
	WOOD(2),
	MOUNTAIN(3),
	SEA(4),
	CITY(5),
	PORT(6),
	AIRPORT(7),
	FACTORY(8),
	REEF(9),
	SHORE(10),
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