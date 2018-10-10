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
		infantry = new Infantry(-1, -1, Color.white, -1);
		mech = new Mech(-1, -1, Color.white, -1);
		recon = new Recon(-1, -1, Color.white, -1);
		tank = new Tank(-1, -1, Color.white, -1);
		mdTank = new MDTank(-1, -1, Color.white, -1);
		neotank = new Neotank(-1, -1, Color.white, -1);
		apc = new APC(-1, -1, Color.white, -1);
		artillery = new Artillery(-1, -1, Color.white, -1);
		rocket = new Rocket(-1, -1, Color.white, -1);
		a_air = new AAir(-1, -1, Color.white, -1);
		missiles = new Missiles(-1, -1, Color.white, -1);
		fighter = new Fighter(-1, -1, Color.white, -1);
		bomber = new Bomber(-1, -1, Color.white, -1);
		bCopter = new BCopter(-1, -1, Color.white, -1);
		tCopter = new TCopter(-1, -1, Color.white, -1);
		battleship = new Battleship(-1, -1, Color.white, -1);
		cruiser = new Cruiser(-1, -1, Color.white, -1);
		lander = new Lander(-1, -1, Color.white, -1);
//		sub = new Sub(-1, -1, Color.white, -1);
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