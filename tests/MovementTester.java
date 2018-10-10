package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

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

public class MovementTester {
	private static Infantry infantry;
	private static Mech mech;
	private static Recon recon;
	private static Tank tank;
	private static MDTank mdTank;
	private static Neotank neotank;
	private static APC apc;
	private static Artillery artillery;
	private static Rocket rocket;
	private static AAir a_air;
	private static Missiles missiles;
	private static Fighter fighter;
	private static Bomber bomber;
	private static BCopter bCopter;
	private static TCopter tCopter;
	private static Battleship battleship;
	private static Cruiser cruiser;
	private static Lander lander;
	//private static Sub sub;

	private static int[][] testMap;

	/***
	 * Creates units of every type and implements test-map
	 */
	@Before
	public void init() {
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
		
		implementTestMap();
	}
	
	/***
	 * TODO:
	 * - test from all area types, via all area types to all area types
	 *    basically handmake for every unit
	 * 
	 **/
	private void implementTestMap() {
		testMap = new int[7][10];
	}
	
	@Test
	public void testTerrainMovement() {
		fail("Not yet implemented");
	}
}
