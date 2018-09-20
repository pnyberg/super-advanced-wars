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
package tests;

import units.*;

import org.junit.Before;
import org.junit.Test;

public class ValidAttackTester {
	private UnitCollection unitCollection;
	private UnitBattleInteractionHandler unitBattleInteractionHandler;

	@Before
	public void init() {
		unitCollection = new UnitCollection();
		unitBattleInteractionHandler = new UnitBattleInteractionHandler(unitCollection);
	}

	/***
	 * Tests all the units vs other units
	 * @TODO: implement methods for Sub
	 */
	@Test
	public void testUnitVsUnit() {
		//testBomberVsUnit();
		//testBCopterVsUnit();
		//testTCopterVsUnit();
		//testSubVsUnit();
	}

	@Test
	public void testInfantryVsUnit() {
		unitBattleInteractionHandler.testMachineGunVsUnit(unitCollection.infantry);
	}

	@Test
	public void testMechVsUnit() {
		//testRocketLauncherVsUnit();
		unitBattleInteractionHandler.testMachineGunVsUnit(unitCollection.mech);
	}

	@Test
	public void testReconVsUnit() {
		unitBattleInteractionHandler.testMachineGunVsUnit(unitCollection.recon);
	}

	@Test
	public void testTankVsUnit() {
		unitBattleInteractionHandler.testTankTypeUnitVsUnit(unitCollection.tank);
	}

	@Test
	public void testMDtankVsUnit() {
		unitBattleInteractionHandler.testTankTypeUnitVsUnit(unitCollection.mdTank);
	}

	@Test
	public void testNeotankVsUnit() {
		unitBattleInteractionHandler.testTankTypeUnitVsUnit(unitCollection.neotank);
	}
	
	@Test
	public void testArtilleryVsUnit() {
		unitBattleInteractionHandler.testGroundIndirectUnitVsUnit(unitCollection.artillery);
	}

	@Test
	public void testRocketVsUnit() {
		unitBattleInteractionHandler.testGroundIndirectUnitVsUnit(unitCollection.rocket);
	}

	@Test
	public void testAAirVsUnit() {
		Unit[] acceptedUnits = unitCollection.getNonSeaUnits();
		Unit[] notAcceptedUnits = unitCollection.getSeaUnits();
		unitBattleInteractionHandler.testAcceptedAndNotAcceptedUnitInteractions(unitCollection.a_air, acceptedUnits, notAcceptedUnits);

		unitBattleInteractionHandler.testNonCruiserOrSubVsSub(unitCollection.a_air, false);
	}
	
	@Test
	public void testMissilesVsUnit() {
		unitBattleInteractionHandler.testAirIndirectUnitVsUnit(unitCollection.missiles);
	}

	@Test
	public void testFighterVsUnit() {
		unitBattleInteractionHandler.testAirIndirectUnitVsUnit(unitCollection.fighter);
	}
	
	@Test
	public void testBattleshipVsUnit() {
		unitBattleInteractionHandler.testGroundIndirectUnitVsUnit(unitCollection.battleship);
	}

	@Test
	public void testCruiserVsUnit() {
		Unit[] acceptedUnits = unitCollection.getAirUnits();
		Unit[] notAcceptedUnits = unitCollection.getGroundUnits();
		unitBattleInteractionHandler.testAcceptedAndNotAcceptedUnitInteractions(unitCollection.cruiser, acceptedUnits, notAcceptedUnits);

		// special case
		unitBattleInteractionHandler.testCruiserOrSubVsSub(unitCollection.cruiser);
	}
}