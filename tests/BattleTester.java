package tests;

import handlers.*;
import units.*;

import java.awt.Color;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the interaction between all the units but only if there is allowed.
 * Do not test how much damage is given, might want to do that but for now 
 *  it's not tested.
 * However, machine gun and submerged/emerged subs are included.
 *
 * TODO:
 *  - complement test code for subs when Sub is implemented
 *  - test how much ammo units should have (3 for Mech for example)
 * 
 * @author pnyberg
 */

public class BattleTester {
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

	/***
	 * Creates units of every type and implements the damage-matrix (in the DamageHandler) 
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

		DamageHandler.init();
	}

	/***
	 * Tests all the units vs other units
	 * @TODO: implement methods for AAir, Missile and Sub
	 */
	@Test
	public void testUnitVsUnit() {
		testInfantryVsUnit();
		testMechVsUnit();
		testReconVsUnit();
		testTankVsUnit();
		testMDtankVsUnit();
		testNeotankVsUnit();
		testArtilleryVsUnit();
		testRocketVsUnit();
		testAAirVsUnit();
		//testMissileVsUnit();

		//testFighterVsUnit();
		//testBomberVsUnit();
		//testBCopterVsUnit();
		//testTCopterVsUnit();

		testBattleshipVsUnit();
		testCruiserVsUnit();
		//testSubVsUnit();
		
		System.out.println("All tests succeeded!");
	}

	/*******************************
	 *        Unit-methods
	 *******************************/

	private void testInfantryVsUnit() {
		testMachineGunVsUnit(infantry);
	}

	private void testMechVsUnit() {
		testMachineGunVsUnit(mech);
	}

	private void testReconVsUnit() {
		testMachineGunVsUnit(recon);
	}

	private void testTankVsUnit() {
		testTankTypeUnitVsUnit(tank);
	}

	private void testMDtankVsUnit() {
		testTankTypeUnitVsUnit(mdTank);
	}

	private void testNeotankVsUnit() {
		testTankTypeUnitVsUnit(neotank);
	}
	
	private void testArtilleryVsUnit() {
		testGroundIndirectUnitVsUnit(artillery);
	}

	private void testRocketVsUnit() {
		testGroundIndirectUnitVsUnit(rocket);
	}

	private void testAAirVsUnit() {
		// acceptable
		testXvsY(a_air, fighter, true);
		testXvsY(a_air, bomber, true);
		testXvsY(a_air, bCopter, true);
		testXvsY(a_air, tCopter, true);
		
		// not acceptable
		testXvsY(a_air, infantry, false);
		testXvsY(a_air, mech, false);
		testXvsY(a_air, recon, false);
		testXvsY(a_air, tank, false);
		testXvsY(a_air, mdTank, false);
		testXvsY(a_air, neotank, false);
		testXvsY(a_air, apc, false);
		testXvsY(a_air, artillery, false);
		testXvsY(a_air, rocket, false);
		testXvsY(a_air, a_air, false);
		testXvsY(a_air, missiles, false);
		testXvsY(a_air, battleship, false);
		testXvsY(a_air, cruiser, false);
		testXvsY(a_air, lander, false);

		// special case
		testNonCruiserOrSubVsSub(a_air, false);
	}
	
	private void testBattleshipVsUnit() {
		testGroundIndirectUnitVsUnit(battleship);
	}

	private void testCruiserVsUnit() {
		// acceptable
		testXvsY(cruiser, fighter, true);
		testXvsY(cruiser, bomber, true);
		testXvsY(cruiser, bCopter, true);
		testXvsY(cruiser, tCopter, true);
		
		// not acceptable
		testXvsY(cruiser, infantry, false);
		testXvsY(cruiser, mech, false);
		testXvsY(cruiser, recon, false);
		testXvsY(cruiser, tank, false);
		testXvsY(cruiser, mdTank, false);
		testXvsY(cruiser, neotank, false);
		testXvsY(cruiser, apc, false);
		testXvsY(cruiser, artillery, false);
		testXvsY(cruiser, rocket, false);
		testXvsY(cruiser, a_air, false);
		testXvsY(cruiser, missiles, false);
		testXvsY(cruiser, battleship, false);
		testXvsY(cruiser, cruiser, false);
		testXvsY(cruiser, lander, false);

		// special case
		testCruiserOrSubVsSub(cruiser);
	}

	/*******************************
	 *        Helping methods
	 *******************************/

	/**
	 * If expected result isn't matched a text is printed explaining what went wrong
	 * 
	 * @param att
	 * @param def
	 * @param expectedSuccess
	 */
	private void testXvsY(Unit att, Unit def, boolean expectedSuccess) {
		if (expectedSuccess) {
			assertTrue(att.getClass() + " should be able to attack " + def.getClass(), DamageHandler.validTarget(att, def));
		} else {
			assertFalse(att.getClass() + " shouldn't be able to attack " + def.getClass(), DamageHandler.validTarget(att, def));
		}
	}

	/**
	 * Tests machine gun (used for several units)
	 * 
	 * @param att
	 */
	private void testMachineGunVsUnit(Unit att) {
		// if the attacking unit normally has main weapon, empty it
		while(att.hasAmmo()) {
			att.useAmmo();
		}

		// acceptable
		testXvsY(att, infantry, true);
		testXvsY(att, mech, true);
		testXvsY(att, recon, true);
		testXvsY(att, tank, true);
		testXvsY(att, mdTank, true);
		testXvsY(att, neotank, true);
		testXvsY(att, apc, true);
		testXvsY(att, artillery, true);
		testXvsY(att, rocket, true);
		testXvsY(att, a_air, true);
		testXvsY(att, missiles, true);
		testXvsY(att, bCopter, true);
		testXvsY(att, tCopter, true);

		// not acceptable
		testXvsY(att, fighter, false);
		testXvsY(att, bomber, false);
		testXvsY(att, battleship, false);
		testXvsY(att, cruiser, false);
		testXvsY(att, lander, false);

		testNonCruiserOrSubVsSub(att, false);

		// reset the ammo
		att.replentish();
	}

	/**
	 * Tests indirect units that targets ground-units
	 * That is: Artillery, Rocket and Battleship
	 * 
	 * @param att
	 */
	private void testGroundIndirectUnitVsUnit(Unit att) {
		// acceptable
		testXvsY(att, infantry, true);
		testXvsY(att, mech, true);
		testXvsY(att, recon, true);
		testXvsY(att, tank, true);
		testXvsY(att, mdTank, true);
		testXvsY(att, neotank, true);
		testXvsY(att, apc, true);
		testXvsY(att, artillery, true);
		testXvsY(att, rocket, true);
		testXvsY(att, a_air, true);
		testXvsY(att, missiles, true);
		testXvsY(att, battleship, true);
		testXvsY(att, cruiser, true);
		testXvsY(att, lander, true);

		// not acceptable
		testXvsY(att, fighter, false);
		testXvsY(att, bomber, false);
		testXvsY(att, bCopter, false);
		testXvsY(att, tCopter, false);

		testNonCruiserOrSubVsSub(att, true);
	}

	/**
	 * Test tanks against other units (Tank, MDTank and Neotank)
	 * 
	 * @param attacker
	 */
	private void testTankTypeUnitVsUnit(Unit attacker) {
		testMachineGunVsUnit(attacker);

		// acceptable
		testXvsY(attacker, infantry, true);
		testXvsY(attacker, mech, true);
		testXvsY(attacker, recon, true);
		testXvsY(attacker, tank, true);
		testXvsY(attacker, mdTank, true);
		testXvsY(attacker, neotank, true);
		testXvsY(attacker, apc, true);
		testXvsY(attacker, artillery, true);
		testXvsY(attacker, rocket, true);
		testXvsY(attacker, a_air, true);
		testXvsY(attacker, missiles, true);
		testXvsY(attacker, bCopter, true);
		testXvsY(attacker, tCopter, true);
		testXvsY(attacker, battleship, true);
		testXvsY(attacker, cruiser, true);
		testXvsY(attacker, lander, true);

		// not acceptable
		testXvsY(attacker, fighter, false);
		testXvsY(attacker, bomber, false);

		testNonCruiserOrSubVsSub(attacker, true);
	}

	/**
	 * Test all units that isn't Cruiser or Sub against Subs
	 * Shouldn't be able to attack when sub is submerged
	 * 
	 * @param attacker
	 * @param expectedSuccessEmerged
	 */
	private void testNonCruiserOrSubVsSub(Unit attacker, boolean expectedSuccessEmerged) {
//		testXvsY(attacker, sub, expectedSuccessEmerged);
//		sub.dive();
//		testXvsY(attacker, sub, false);
//		sub.emerge();
	}
	
	/**
	 * Tests Cruiser or Sub against Subs, should be able to attack if it has ammo
	 */
	private void testCruiserOrSubVsSub(Unit attacker) {
//		testXvsY(attacker, sub, true);
		//sub.dive();
//		testXvsY(attacker, sub, true);

		// shouldn't be able to attack subs with machine gun (if Cruiser)
		while(attacker.hasAmmo()) {
			attacker.useAmmo();
		}
		
//		testXvsY(attacker, sub, false);
		//sub.emerge();
//		testXvsY(attacker, sub, false);
		
		cruiser.replentish();
	}
}
