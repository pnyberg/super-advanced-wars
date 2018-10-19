package units;

public enum MovementType {
	INFANTRY(0),
	MECH(1),
	BAND(2),
	TIRE(3),
	SHIP(4),
	TRANSPORT(5),
	AIR(6);

	public static final int numberOfMovementTypes = 7;
	
	private int movementTypeIndex;

	MovementType(int movementTypeIndex) {
		this.movementTypeIndex = movementTypeIndex;
	}
	
	public int movementTypeIndex() {
		return movementTypeIndex;
	}
	
	public boolean isLandMovementType() {
		return movementTypeIndex == 0
			|| movementTypeIndex == 1
			|| movementTypeIndex == 2
			|| movementTypeIndex == 3;
	}

	public boolean isAirMovementType() {
		return movementTypeIndex == 6;
	}

	public boolean isSeaMovementType() {
		return movementTypeIndex == 4
			|| movementTypeIndex == 5;
	}
}