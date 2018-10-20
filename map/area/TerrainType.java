package map.area;

public enum TerrainType {
	ROAD("Road", 0),
	PLAIN("Plain", 1),
	WOOD("Wood", 2),
	MOUNTAIN("Mtn", 3),
	//HQ("HQ", 4),
	CITY("City", 4),
	FACTORY("Base", 5),
	AIRPORT("Arprt", 6),
	PORT("Port", 7),
	SEA("Sea", 8),
	SHOAL("Shoal", 9),
	REEF("Reef", 10),
	//BRIDGE("Brdg", 11),
	UMI("Umi", 	11),
	MINI_CANNON("Cannon", 12);

	public static final int numberOfAreaTypes = 13;

	private final int terrainTypeIndex;
	private final String showName;

	TerrainType(String showName, int terrainTypeIndex) {
		this.showName = showName;
		this.terrainTypeIndex = terrainTypeIndex;
	}
	
	public boolean isBuilding() {
		return this == CITY
			|| this == FACTORY
			|| this == AIRPORT
			|| this == PORT;
	}
	
	public boolean isStructure() {
		return this == TerrainType.MINI_CANNON; 
	}
	
	public String showName() {
		return showName;
	}
	
	public int terrainTypeIndex() {
		return terrainTypeIndex;
	}
}