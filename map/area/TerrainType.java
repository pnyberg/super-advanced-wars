package map.area;

public enum TerrainType {
	ROAD("Road", 0, 0),
	PLAIN("Plain", 1, 1),
	WOOD("Wood", 2, 2),
	MOUNTAIN("Mtn", 3, 3),
	//HQ("HQ", 4, 4),
	CITY("City", 3, 4),
	FACTORY("Base", 3, 5),
	AIRPORT("Arprt", 3, 6),
	PORT("Port", 3, 7),
	SEA("Sea", 0, 8),
	SHOAL("Shoal", 0, 9),
	REEF("Reef", 1, 10),
	//BRIDGE("Brdg", 0, 11),
	UMI("Umi", 	1, 11),
	MINI_CANNON("Cannon", 0, 12);

	public static final int numberOfAreaTypes = 13;

	private final String showName;
	private final int defenceValue;
	private final int terrainTypeIndex;

	TerrainType(String showName, int defenceValue, int terrainTypeIndex) {
		this.showName = showName;
		this.defenceValue = defenceValue;
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
	
	public int defenceValue() {
		return defenceValue;
	}
	
	public int terrainTypeIndex() {
		return terrainTypeIndex;
	}
}