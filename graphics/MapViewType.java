package graphics;

public enum MapViewType {
	MAIN_MAP_MENU_VIEW(0),
	CO_MAP_MENU_VIEW(1);

	private final int mapViewIndex;

	MapViewType(int mapViewIndex) {
		this.mapViewIndex = mapViewIndex;
	}
	
	public int mapViewIndex() {
		return mapViewIndex;
	}
}