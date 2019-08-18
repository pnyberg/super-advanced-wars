package unitUtils;

public enum MovementType {
	INFANTRY(0),
	MECH(1),
	TREAD(2),
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
		return movementTypeIndex == INFANTRY.movementTypeIndex
			|| movementTypeIndex == MECH.movementTypeIndex
			|| movementTypeIndex == TREAD.movementTypeIndex
			|| movementTypeIndex == TIRE.movementTypeIndex;
	}

	public boolean isAirMovementType() {
		return movementTypeIndex == AIR.movementTypeIndex;
	}

	public boolean isSeaMovementType() {
		return movementTypeIndex == SHIP.movementTypeIndex
			|| movementTypeIndex == TRANSPORT.movementTypeIndex;
	}
}