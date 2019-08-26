package unitUtils;

import units.Unit;
import units.airMoving.*;
import units.footMoving.*;
import units.seaMoving.*;
import units.tireMoving.*;
import units.treadMoving.*;

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
	public static final UnitType[] allUnitTypes = { INFANTRY, MECH, RECON,
													TANK, MDTANK, NEOTANK, APC,
													ARTILLERY, ROCKET, A_AIR, MISSILES,
													FIGHTER, BOMBER, BCOPTER, TCOPTER,
													BATTLESHIP, CRUISER, LANDER};

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
	
	public boolean isTransportUnit() {
		return unitIndex == APC.unitIndex
			|| unitIndex == TCOPTER.unitIndex
			|| unitIndex == CRUISER.unitIndex
			|| unitIndex == LANDER.unitIndex;
	}

	public static int getUnitIndexFromUnit(Unit unit) {
		UnitType unitType = getUnitTypeFromUnit(unit);
		if (unitType == null) {
			return -1;
		}
		return unitType.unitIndex();
	}

	public static String getUnitTypeNameFromUnit(Unit unit) {
		UnitType unitType = getUnitTypeFromUnit(unit);
		if (unitType == null) {
			return null;
		}
		return unitType.unitTypeName();
	}
	
	private static UnitType getUnitTypeFromUnit(Unit unit) {
		if (unit instanceof Infantry) {
			return UnitType.INFANTRY;
		} else if (unit instanceof Mech) {
			return UnitType.MECH;
		} else if (unit instanceof Recon) {
			return UnitType.RECON;
		} else if (unit instanceof Tank) {
			return UnitType.TANK;
		} else if (unit instanceof MDTank) {
			return UnitType.MDTANK;
		} else if (unit instanceof Neotank) {
			return UnitType.NEOTANK;
		} else if (unit instanceof APC) {
			return UnitType.APC;
		} else if (unit instanceof Artillery) {
			return UnitType.ARTILLERY;
		} else if (unit instanceof Rocket) {
			return UnitType.ROCKET;
		} else if (unit instanceof AAir) {
			return UnitType.A_AIR;
		} else if (unit instanceof Missiles) {
			return UnitType.MISSILES;
		} else if (unit instanceof Fighter) {
			return UnitType.FIGHTER;
		} else if (unit instanceof Bomber) {
			return UnitType.BOMBER;
		} else if (unit instanceof BCopter) {
			return UnitType.BCOPTER;
		} else if (unit instanceof TCopter) {
			return UnitType.TCOPTER;
		} else if (unit instanceof Battleship) {
			return UnitType.BATTLESHIP;
		} else if (unit instanceof Cruiser) {
			return UnitType.CRUISER;
		} else if (unit instanceof Lander) {
			return UnitType.LANDER;
/*		} else if (unit instanceof Sub) {
			return UnitTypes.SUB;*/
		}
		return null;
	}
}