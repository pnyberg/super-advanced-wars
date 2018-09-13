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
