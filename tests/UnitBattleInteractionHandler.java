package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import combat.AttackValueCalculator;
import combat.DamageHandler;
import combat.DefenceValueCalculator;
import main.HeroHandler;
import map.GameMap;
import unitUtils.UnitWorthCalculator;
import units.Unit;

public class UnitBattleInteractionHandler {
	private DamageHandler damageHandler;
	private UnitCollection unitCollection;

	public UnitBattleInteractionHandler(UnitCollection unitCollection) {
		//damageHandler = new DamageHandler(new HeroHandler(), new GameMap(1, 1, 1), new AttackValueCalculator(), new DefenceValueCalculator(), new UnitWorthCalculator());
		this.unitCollection = unitCollection;
	}

	public void testAcceptedAndNotAcceptedUnitInteractions(Unit attacker, Unit[] acceptedUnits, Unit[] notAcceptedUnits) {
		for (Unit acceptedUnit : acceptedUnits) {
			testXvsY(attacker, acceptedUnit, true);
		}
		for (Unit notAcceptedUnit : notAcceptedUnits) {
			testXvsY(attacker, notAcceptedUnit, false);
		}
	}

	/**
	 * Tests machine gun (used for several units)
	 * 
	 * @param attacker
	 */
	public void testMachineGunVsUnit(Unit attacker) {
		// if the attacking unit normally has main weapon, empty it
		while(attacker.getUnitSupply().hasAmmo()) {
			attacker.getUnitSupply().useAmmo();
		}

		Unit[] acceptedUnits = unitCollection.getNonPlaneNonSeaUnits();
		Unit[] notAcceptedUnits = unitCollection.getPlaneAndSeaUnits();
		testAcceptedAndNotAcceptedUnitInteractions(attacker, acceptedUnits, notAcceptedUnits);

		testNonCruiserOrSubVsSub(attacker, false);

		// reset the ammo
		attacker.getUnitSupply().replentish();
	}

	/**
	 * Tests indirect units that targets ground-units
	 * That is: Artillery, Rocket and Battleship
	 * 
	 * @param attacker
	 */
	public void testGroundIndirectUnitVsUnit(Unit attacker) {
		Unit[] acceptedUnits = unitCollection.getGroundUnits();
		Unit[] notAcceptedUnits = unitCollection.getAirUnits();
		testAcceptedAndNotAcceptedUnitInteractions(attacker, acceptedUnits, notAcceptedUnits);

		testNonCruiserOrSubVsSub(attacker, true);
	}

	public void testAirIndirectUnitVsUnit(Unit attacker) {
		Unit[] acceptedUnits = unitCollection.getAirUnits();
		Unit[] notAcceptedUnits = unitCollection.getGroundUnits();
		testAcceptedAndNotAcceptedUnitInteractions(attacker, acceptedUnits, notAcceptedUnits);

		testNonCruiserOrSubVsSub(attacker, false);
	}

	/**
	 * Test tanks against other units (Tank, MDTank and Neotank)
	 * 
	 * @param attacker
	 */
	public void testTankTypeUnitVsUnit(Unit attacker) {
		testMachineGunVsUnit(attacker);

		Unit[] acceptedUnits = unitCollection.getNonPlaneUnits();
		Unit[] notAcceptedUnits = unitCollection.getPlaneUnits();
		testAcceptedAndNotAcceptedUnitInteractions(attacker, acceptedUnits, notAcceptedUnits);

		testNonCruiserOrSubVsSub(attacker, true);
	}

	/**
	 * If expected result isn't matched a text is printed explaining what went wrong
	 * 
	 * @param att
	 * @param def
	 * @param expectedSuccess
	 */
	private void testXvsY(Unit att, Unit def, boolean expectedSuccess) {
		if (expectedSuccess) {
			assertTrue(att.getClass() + " should be able to attack " + def.getClass(), damageHandler.validTarget(att, def));
		} else {
			assertFalse(att.getClass() + " shouldn't be able to attack " + def.getClass(), damageHandler.validTarget(att, def));
		}
	}
	
	/**
	 * Test all units that isn't Cruiser or Sub against Subs
	 * Shouldn't be able to attack when sub is submerged
	 * 
	 * @param attacker
	 * @param expectedSuccessEmerged
	 */
	public void testNonCruiserOrSubVsSub(Unit attacker, boolean expectedSuccessEmerged) {
//		testXvsY(attacker, sub, expectedSuccessEmerged);
//		sub.dive();
//		testXvsY(attacker, sub, false);
//		sub.emerge();
	}
	
	/**
	 * Tests Cruiser or Sub against Subs, should be able to attack if it has ammo
	 */
	public void testCruiserOrSubVsSub(Unit attacker) {
//		testXvsY(attacker, sub, true);
		//sub.dive();
//		testXvsY(attacker, sub, true);

		// shouldn't be able to attack subs with machine gun (if Cruiser)
		while(attacker.getUnitSupply().hasAmmo()) {
			attacker.getUnitSupply().useAmmo();
		}
		
//		testXvsY(attacker, sub, false);
		//sub.emerge();
//		testXvsY(attacker, sub, false);
		
		unitCollection.cruiser.getUnitSupply().replentish();
	}
}