package combat;

import map.area.TerrainType;

public class DefenceValueCalculator {

	public DefenceValueCalculator() {
		
	}
	
	public int getDefenceValue(TerrainType terrainType) {
		if (terrainType == TerrainType.ROAD ||
			terrainType == TerrainType.SEA ||
			terrainType == TerrainType.SHORE) {
			return 0;
		} else if (terrainType == TerrainType.PLAIN ||
					terrainType == TerrainType.REEF) {
			return 1;
		} else if (terrainType == TerrainType.WOOD) {
			return 2;
		} else if (terrainType == TerrainType.CITY ||
					terrainType == TerrainType.FACTORY ||
					terrainType == TerrainType.AIRPORT ||
					terrainType == TerrainType.PORT) {
			return 3;
		} else if (terrainType == TerrainType.MOUNTAIN) {
			return 4;
		} 
		return -1;
	}
}