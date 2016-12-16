package tests;

import handlers.*;
import units.*;

import java.awt.Color;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO:
 *  - test when ammo is out
 *  - test when sub is emerged/submerged
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

		testBattleshipVsUnit();
		testCruiserVsUnit();
		
		System.out.println("All tests succeeded!");
	}

	/* === Units === */
	
	/**
	 *  Only want to test if infantry can fire
	 *  Testing the damage from infantries is to tedious, so won't do that
	 */
	private void testInfantryVsUnit() {
		testMachineGunVsUnit(infantry);
	}

	/**
	 *  We want to test the machine gun for the mech
	 *  But we also want to test that the rocket launcher works correctly
	 *  That is, 3 shots then machine gun and never rocket launcher against non-vehicles
	 */
	private void testMechVsUnit() {
		testMachineGunVsUnit(mech);
		
		//TODO: test rocket launcher (amount of ammo, against units)
	}

	/**
	 *  Only want to test if recon can fire
	 *  Testing the damage from recons is to tedious, so won't do that
	 */
	private void testReconVsUnit() {
		testMachineGunVsUnit(recon);
	}

	private void testTankVsUnit() {
		testTankUnitVsUnit(tank);
	}

	private void testMDtankVsUnit() {
		testTankUnitVsUnit(mdTank);
	}

	private void testNeotankVsUnit() {
		testTankUnitVsUnit(neotank);
	}
	
	private void testArtilleryVsUnit() {
		testGroundIndirectUnitVsUnit(artillery);
	}

	private void testRocketVsUnit() {
		testGroundIndirectUnitVsUnit(rocket);
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
		testXvsY(cruiser, lander, false);

		// special case
		testCruiserVsSub();
	}

	/* === Helping methods === */
	
	private void testXvsY(Unit att, Unit def, boolean expectedSuccess) {
		if (expectedSuccess) {
			assertTrue(att.getClass() + " should be able to attack " + def.getClass(), DamageHandler.validTarget(att, def));
		} else {
			assertFalse(att.getClass() + " shouldn't be able to attack " + def.getClass(), DamageHandler.validTarget(att, def));
		}
	}

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
		testXvsY(att, lander, false);

		testNonCruiserVsSub(att, false);

		// reset the ammo
		att.replentish();
	}

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
		testXvsY(att, lander, true);

		// not acceptable
		testXvsY(att, fighter, false);
		testXvsY(att, bomber, false);
		testXvsY(att, bCopter, false);
		testXvsY(att, tCopter, false);

		testNonCruiserVsSub(att, true);

		// reset the ammo
		att.replentish();
	}

	private void testTankUnitVsUnit(Unit attacker) {
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
		testXvsY(attacker, lander, true);

		// not acceptable
		testXvsY(attacker, fighter, false);
		testXvsY(attacker, bomber, false);

		testNonCruiserVsSub(attacker, true);
	}

	private void testNonCruiserVsSub(Unit attacker, boolean expectedSuccessEmerged) {
//		testXvsY(attacker, sub, expectedSuccessEmerged);
//		sub.dive();
//		testXvsY(attacker, sub, false);
//		sub.emerge();
	}
	
	private void testCruiserVsSub() {
//		testXvsY(cruiser, sub, true);
		//sub.dive();
//		testXvsY(cruiser, sub, true);

		// shouldn't be able to attack subs with machine gun
		while(cruiser.hasAmmo()) {
			cruiser.useAmmo();
		}
		
//		testXvsY(cruiser, sub, false);
		//sub.emerge();
//		testXvsY(cruiser, sub, false);
		
		cruiser.replentish();
	}
}
