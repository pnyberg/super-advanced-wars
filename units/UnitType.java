package units;

import units.airMoving.BCopter;
import units.airMoving.Bomber;
import units.airMoving.Fighter;
import units.airMoving.TCopter;
import units.footMoving.Infantry;
import units.footMoving.Mech;
import units.seaMoving.Battleship;
import units.seaMoving.Cruiser;
import units.seaMoving.Lander;
import units.tireMoving.Missiles;
import units.tireMoving.Recon;
import units.tireMoving.Rocket;
import units.treadMoving.AAir;
import units.treadMoving.APC;
import units.treadMoving.Artillery;
import units.treadMoving.MDTank;
import units.treadMoving.Neotank;
import units.treadMoving.Tank;

public enum UnitType {
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

	public static final int numberOfUnitTypes = 18;

	private final int unitIndex;

	UnitType(int unitIndex) {
		this.unitIndex = unitIndex;
	}
	
	public int unitIndex() {
		return unitIndex;
	}

	public static int getTypeFromUnit(Unit unit) {
		if (unit instanceof Infantry) {
			return UnitType.INFANTRY.unitIndex();
		} else if (unit instanceof Mech) {
			return UnitType.MECH.unitIndex();
		} else if (unit instanceof Recon) {
			return UnitType.RECON.unitIndex();
		} else if (unit instanceof Tank) {
			return UnitType.TANK.unitIndex();
		} else if (unit instanceof MDTank) {
			return UnitType.MDTANK.unitIndex();
		} else if (unit instanceof Neotank) {
			return UnitType.NEOTANK.unitIndex();
		} else if (unit instanceof APC) {
			return UnitType.APC_v.unitIndex();
		} else if (unit instanceof Artillery) {
			return UnitType.ARTILLERY.unitIndex();
		} else if (unit instanceof Rocket) {
			return UnitType.ROCKET.unitIndex();
		} else if (unit instanceof AAir) {
			return UnitType.A_AIR.unitIndex();
		} else if (unit instanceof Missiles) {
			return UnitType.MISSILES.unitIndex();
		} else if (unit instanceof Fighter) {
			return UnitType.FIGHTER.unitIndex();
		} else if (unit instanceof Bomber) {
			return UnitType.BOMBER.unitIndex();
		} else if (unit instanceof BCopter) {
			return UnitType.BCOPTER.unitIndex();
		} else if (unit instanceof TCopter) {
			return UnitType.TCOPTER.unitIndex();
		} else if (unit instanceof Battleship) {
			return UnitType.BATTLESHIP.unitIndex();
		} else if (unit instanceof Cruiser) {
			return UnitType.CRUISER.unitIndex();
		} else if (unit instanceof Lander) {
			return UnitType.LANDER.unitIndex();
/*		} else if (unit instanceof Sub) {
			return UnitTypes.SUB.unitIndex();*/
		}
		return -1;
	}
}
