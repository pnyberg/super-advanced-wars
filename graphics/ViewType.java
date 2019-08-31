package graphics;

public enum ViewType {
	MAP_VIEW(0),
	CO_VIEW(1),
	STATUS_VIEW(2),
	UNIT_LIST_VIEW(3),
	RULES_VIEW(4),
	UNIT_INFO_VIEW(5),
	TERRAIN_INFO_VIEW(6);

	private final int viewIndex;

	ViewType(int viewIndex) {
		this.viewIndex = viewIndex;
	}
	
	public int viewIndex() {
		return viewIndex;
	}
}