package menus;

import map.area.TerrainType;

public class BuildingMenuState extends MenuState {
	private TerrainType currentBuildingMenuType;

	public BuildingMenuState() {
		super();
		currentBuildingMenuType = null;
	}
	
	public void resetBuildingMenuType() {
		currentBuildingMenuType = null;
	}
	
	public void setBuildingMenuType(TerrainType terrainType) {
		currentBuildingMenuType = terrainType;
	}
	
	public TerrainType getBuildingMenuType() {
		return currentBuildingMenuType;
	}
}
