package unitUtils;

public enum UnitCategory {
	FOOTMAN(0),
	VEHICLE(1),
	PLANE(2),
	COPTER(3),
	BOAT(4),
	SUB(5);

	private final int unitCategoryIndex;

	UnitCategory(int unitCategoryIndex) {
		this.unitCategoryIndex = unitCategoryIndex;
	}
	
	public int unitCategoryIndex() {
		return unitCategoryIndex;
	}
}