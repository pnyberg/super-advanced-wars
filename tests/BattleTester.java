package tests;

import handlers.*;
import units.*;

import java.awt.Color;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
	//private static Cruiser cruiser;
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
//		cruiser = new Cruiser(-1, -1, Color.white);
		lander = new Lander(-1, -1, Color.white);
//		sub = new Sub(-1, -1, Color.white);
	}

	@Test
	public void testUnitVsUnit() {
		init();
		DamageHandler.init();

		testInfantryVsUnit();
		testMechVsUnit();
		testReconVsUnit();
		testTankVsUnit();
		testMDtankVsUnit();
		testNeotankVsUnit();
		
		System.out.println("All tests succeeded!");
	}

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

		// reset the ammo
		att.replentish();
	}

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
		testMachineGunVsUnit(tank);

		// acceptable
		testXvsY(tank, infantry, true);
		testXvsY(tank, mech, true);
		testXvsY(tank, recon, true);
		testXvsY(tank, tank, true);
		testXvsY(tank, mdTank, true);
		testXvsY(tank, neotank, true);
		testXvsY(tank, apc, true);
		testXvsY(tank, artillery, true);
		testXvsY(tank, rocket, true);
		testXvsY(tank, a_air, true);
		testXvsY(tank, missiles, true);
		testXvsY(tank, bCopter, true);
		testXvsY(tank, tCopter, true);
		testXvsY(tank, battleship, true);
		testXvsY(tank, lander, true);

		// not acceptable
		testXvsY(tank, fighter, false);
		testXvsY(tank, bomber, false);
	}

	private void testMDtankVsUnit() {
		testMachineGunVsUnit(mdTank);

		// acceptable
		testXvsY(mdTank, infantry, true);
		testXvsY(mdTank, mech, true);
		testXvsY(mdTank, recon, true);
		testXvsY(mdTank, tank, true);
		testXvsY(mdTank, mdTank, true);
		testXvsY(mdTank, neotank, true);
		testXvsY(mdTank, apc, true);
		testXvsY(mdTank, artillery, true);
		testXvsY(mdTank, rocket, true);
		testXvsY(mdTank, a_air, true);
		testXvsY(mdTank, missiles, true);
		testXvsY(mdTank, bCopter, true);
		testXvsY(mdTank, tCopter, true);
		testXvsY(mdTank, battleship, true);
		testXvsY(mdTank, lander, true);

		// not acceptable
		testXvsY(mdTank, fighter, false);
		testXvsY(mdTank, bomber, false);
	}

	private void testNeotankVsUnit() {
		testMachineGunVsUnit(neotank);

		// acceptable
		testXvsY(neotank, infantry, true);
		testXvsY(neotank, mech, true);
		testXvsY(neotank, recon, true);
		testXvsY(neotank, tank, true);
		testXvsY(neotank, mdTank, true);
		testXvsY(neotank, neotank, true);
		testXvsY(neotank, apc, true);
		testXvsY(neotank, artillery, true);
		testXvsY(neotank, rocket, true);
		testXvsY(neotank, a_air, true);
		testXvsY(neotank, missiles, true);
		testXvsY(neotank, bCopter, true);
		testXvsY(neotank, tCopter, true);
		testXvsY(neotank, battleship, true);
		testXvsY(neotank, lander, true);

		// not acceptable
		testXvsY(neotank, fighter, false);
		testXvsY(neotank, bomber, false);
	}
}
