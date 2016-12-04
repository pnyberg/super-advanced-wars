package tests;

import handlers.*;
import units.*;

import java.awt.Color;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTester {
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

	private static void init() {
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
		DamageHandler.init();

		testInfantryVsUnit();

		System.out.println("All tests succeeded!");
	}

	private static void testXvsY(Unit att, Unit def, boolean expectedSuccess) {
		if(expectedSuccess == DamageHandler.validTarget(att, def)) {
			System.out.println(att.getClass().getTypeName() + " vs " + def.getClass().getTypeName() + " failed!");
		}
	}

	private static void testInfantryVsUnit() {
		testXvsY(infantry, infantry, true);
		testXvsY(infantry, mech, true);
		testXvsY(infantry, recon, true);
		testXvsY(infantry, tank, true);
		testXvsY(infantry, mdTank, true);
		testXvsY(infantry, neotank, true);
		testXvsY(infantry, apc, true);
		testXvsY(infantry, artillery, true);
		testXvsY(infantry, rocket, true);
		testXvsY(infantry, a_air, true);
		testXvsY(infantry, missiles, true);
		testXvsY(infantry, bCopter, true);
		testXvsY(infantry, tCopter, true);

		testXvsY(infantry, fighter, true);
		testXvsY(infantry, fighter, false);

		testXvsY(infantry, bomber, false);
		testXvsY(infantry, battleship, false);
		testXvsY(infantry, lander, false);

//		assertTrue(true);
	}

/*	private static void testMechVsUnit() {
		assertTrue(DamageHandler.validTarget(mech, infantry));
		assertTrue(DamageHandler.validTarget(mech, mech));
		assertTrue(DamageHandler.validTarget(mech, recon));
		assertTrue(DamageHandler.validTarget(mech, tank));
		assertTrue(DamageHandler.validTarget(mech, mdTank));
		assertTrue(DamageHandler.validTarget(mech, neotank));
		assertTrue(DamageHandler.validTarget(mech, apc));
		assertTrue(DamageHandler.validTarget(mech, artillery));
		assertTrue(DamageHandler.validTarget(mech, rocket));
		assertTrue(DamageHandler.validTarget(mech, a_air));
		assertTrue(DamageHandler.validTarget(mech, missiles));
		assertTrue(DamageHandler.validTarget(mech, bCopter));
		assertTrue(DamageHandler.validTarget(mech, tCopter));

		assertFalse(DamageHandler.validTarget(mech, fighter));
		assertFalse(DamageHandler.validTarget(mech, bomber));
		assertFalse(DamageHandler.validTarget(mech, battleship));
		assertFalse(DamageHandler.validTarget(mech, lander));
	}*/
}
