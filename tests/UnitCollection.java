package tests;

import java.awt.Color;

import units.Unit;
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

public class UnitCollection {
	public Infantry infantry;
	public Mech mech;
	public Recon recon;
	public Tank tank;
	public MDTank mdTank;
	public Neotank neotank;
	public APC apc;
	public Artillery artillery;
	public Rocket rocket;
	public AAir a_air;
	public Missiles missiles;
	public Fighter fighter;
	public Bomber bomber;
	public BCopter bCopter;
	public TCopter tCopter;
	public Battleship battleship;
	public Cruiser cruiser;
	public Lander lander;
	//public Sub sub;
	
	public UnitCollection() {
		infantry = new Infantry(-1, -1, Color.white);
		mech = new Mech(-1, -1, Color.white);
		recon = new Recon(-1, -1, Color.white);
		tank = new Tank(-1, -1, Color.white);
		mdTank = new MDTank(-1, -1, Color.white);
		neotank = new Neotank(-1, -1, Color.white);
		apc = new APC(-1, -1, Color.white);
		artillery = new Artillery(-1, -1, Color.white);
		rocket = new Rocket(-1, -1, Color.white);
		a_air = new AAir(-1, -1, Color.white);
		missiles = new Missiles(-1, -1, Color.white);
		fighter = new Fighter(-1, -1, Color.white);
		bomber = new Bomber(-1, -1, Color.white);
		bCopter = new BCopter(-1, -1, Color.white);
		tCopter = new TCopter(-1, -1, Color.white);
		battleship = new Battleship(-1, -1, Color.white);
		cruiser = new Cruiser(-1, -1, Color.white);
		lander = new Lander(-1, -1, Color.white);
//		sub = new Sub(-1, -1, Color.white);
	}
	
	public Unit[] getGroundUnits() {
		return new Unit[]{
			infantry, mech, recon, tank, mdTank, neotank,
			apc, artillery, rocket, a_air, missiles, 
			battleship, cruiser, lander
		};
	}
	
	public Unit[] getNonPlaneUnits() {
		return new Unit[]{
				infantry, mech, recon, tank, mdTank, neotank,
				apc, artillery, rocket, a_air, missiles, 
				bCopter, tCopter, 
				battleship, cruiser, lander
			};
	}

	public Unit[] getAirUnits() {
		return new Unit[]{
				fighter, bomber, bCopter, tCopter 
			};
	}

	public Unit[] getPlaneUnits() {
		return new Unit[]{
				fighter, bomber 
			};
	}
	
	public Unit[] getSeaUnits() {
		return new Unit[]{
				battleship, cruiser, lander
			};
	}

	public Unit[] getNonSeaUnits() {
		return new Unit[]{
				infantry, mech, recon, tank, mdTank, neotank,
				apc, artillery, rocket, a_air, missiles, 
				fighter, bomber, bCopter, tCopter
			};
	}
	
	public Unit[] getPlaneAndSeaUnits() {
		return new Unit[]{
				fighter, bomber,
				battleship, cruiser, lander
			};
	}

	public Unit[] getNonPlaneNonSeaUnits() {
		return new Unit[]{
				infantry, mech, recon, tank, mdTank, neotank,
				apc, artillery, rocket, a_air, missiles, 
				bCopter, tCopter
			};
	}
}