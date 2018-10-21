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
	INFANTRY ("Infantry", "Inftry", 1000, 0),
	MECH ("Mech", "Mech", 3000, 1),
	RECON ("Recon", "Recon", 4000, 2),
	TANK ("Tank", "Tank", 7000, 3),
	MDTANK ("Md Tank", "Md Tank", 16000, 4),
	NEOTANK ("Neotank", "Neo", 22000, 5),
	APC ("APC", "APC", 5000, 6),
	ARTILLERY ("Artillery", "Artly", 6000, 7),
	ROCKET ("Rocket", "Rckts", 15000, 8),
	A_AIR ("A-Air", "A-Air", 8000, 9),
	MISSILES ("Missiles", "Mssls", 12000, 10),
	FIGHTER ("Fighter", "Fghtr", 20000, 11),
	BOMBER ("Bomber", "Bmbr", 22000, 12),
	BCOPTER ("B Cptr", "B Cptr", 9000, 13),
	TCOPTER ("T Cptr", "T Cptr", 5000, 14),
	BATTLESHIP ("B Ship", "B Shp", 28000, 15),
	CRUISER ("Cruiser", "Crsr", 18000, 16),
	LANDER ("Lander", "Lndr", 12000, 17);
//	SUB ("Sub", "Sub", 20000, 18);

	public static final int numberOfUnitTypes = 18;

	private final String unitTypeName;
	private final String unitTypeShowName;
	private final int unitPrice;
	private final int unitIndex;
	
	UnitType(String unitTypeName, String unitTypeShowName, int unitPrice, int unitIndex) {
		this.unitTypeName = unitTypeName;
		this.unitTypeShowName = unitTypeShowName;
		this.unitPrice = unitPrice;
		this.unitIndex = unitIndex;
	}

	public String unitTypeName() {
		return unitTypeName;
	}

	public String unitTypeShowName() {
		return unitTypeShowName;
	}

	public int unitPrice() {
		return unitPrice;
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
			return UnitType.APC.unitIndex();
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
