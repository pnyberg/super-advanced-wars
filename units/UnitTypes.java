package units;

public enum UnitTypes {
	INFANTRY (0),
	MECH (1),
	RECON (2),
	TANK (3),
	MDTANK (4),
	NEOTANK (5),
	APC_v (6),
	ARTILLERY (7),
	ROCKET (8),
	A_AIR (9),
	MISSILES (10),
	FIGHTER (11),
	BOMBER (12),
	BCOPTER (13),
	TCOPTER (14),
	BATTLESHIP (15),
	CRUISER (16),
	LANDER (17);
//	SUB (18);

	private final int unitIndex;

	UnitTypes(int unitIndex) {
		this.unitIndex = unitIndex;
	}
	
	public int unitIndex() {
		return unitIndex;
	}
}
