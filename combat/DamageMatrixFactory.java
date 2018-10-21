/**
 * TODO:
 *  - add Sub
 *  - read values from file
 */
package combat;

import units.UnitType;

public class DamageMatrixFactory {
	private int[][][] damageMatrix;
	
	public DamageMatrixFactory() {
		damageMatrix = new int[UnitType.numberOfUnitTypes][UnitType.numberOfUnitTypes][2]; // the last one is for secondary weapons
		implementDamageMatrix();
	}
	
	private void implementDamageMatrix() {
		for (int i = 0 ; i < UnitType.numberOfUnitTypes ; i++) {
			damageMatrix[UnitType.INFANTRY.unitIndex()][i][0] = -1;
			damageMatrix[UnitType.RECON.unitIndex()][i][0] = -1;
			damageMatrix[UnitType.APC.unitIndex()][i][0] = -1;
			damageMatrix[UnitType.APC.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.ARTILLERY.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.ROCKET.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.A_AIR.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.MISSILES.unitIndex()][i][1] = -1;

			damageMatrix[UnitType.FIGHTER.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.BOMBER.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.TCOPTER.unitIndex()][i][0] = -1;
			damageMatrix[UnitType.TCOPTER.unitIndex()][i][1] = -1;

			damageMatrix[UnitType.BATTLESHIP.unitIndex()][i][1] = -1;
			damageMatrix[UnitType.LANDER.unitIndex()][i][0] = -1;
			damageMatrix[UnitType.LANDER.unitIndex()][i][1] = -1;
		}

//------------ Ground ----------------

		// @infantry
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 55;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.MECH.unitIndex()][1] = 45;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.RECON.unitIndex()][1] = 12;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.TANK.unitIndex()][1] = 5;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.APC.unitIndex()][1] = 14;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 15;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 25;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 5;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 25;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.CRUISER.unitIndex()][1] = 7;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 30;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.INFANTRY.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @mech
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 65;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.MECH.unitIndex()][1] = 55;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.RECON.unitIndex()][0] = 85;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.RECON.unitIndex()][1] = 18;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.TANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 15;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 15;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.APC.unitIndex()][0] = 75;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.APC.unitIndex()][1] = 20;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 70;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 32;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 35;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 65;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 6;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 85;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 35;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 9;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 35;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.LANDER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MECH.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @recon
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 70;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.MECH.unitIndex()][1] = 65;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.RECON.unitIndex()][1] = 35;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.APC.unitIndex()][1] = 45;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 45;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 55;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 4;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 28;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 10;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 35;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.RECON.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @tank
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 25; // will not be used
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 75;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.MECH.unitIndex()][0] = 25; // will not be used
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.MECH.unitIndex()][1] = 70;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.RECON.unitIndex()][0] = 85;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.RECON.unitIndex()][1] = 40;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.TANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 15;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 15;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.APC.unitIndex()][0] = 75;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.APC.unitIndex()][1] = 45;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 70;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 45;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 55;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 65;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 5;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 85;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 30;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 10;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 40;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 5;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.LANDER.unitIndex()][0] = 10;
		damageMatrix[UnitType.TANK.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 1;
//		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @MDtank
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 30; // will not be used
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.MECH.unitIndex()][0] = 30; // will not be used
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.MECH.unitIndex()][1] = 95;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.RECON.unitIndex()][0] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.RECON.unitIndex()][1] = 45;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.TANK.unitIndex()][0] = 85;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.TANK.unitIndex()][1] = 8;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 45;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.APC.unitIndex()][0] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.APC.unitIndex()][1] = 45;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 45;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 55;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 7;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 105;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 35;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 12;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 45;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 10;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 45;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.LANDER.unitIndex()][0] = 35;
		damageMatrix[UnitType.MDTANK.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 10;
//		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @neotank
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 35; // will not be used
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 125;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.MECH.unitIndex()][0] = 35; // will not be used
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.MECH.unitIndex()][1] = 115;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.RECON.unitIndex()][0] = 125;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.RECON.unitIndex()][1] = 65;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.TANK.unitIndex()][0] = 105;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.TANK.unitIndex()][1] = 10;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 75;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.APC.unitIndex()][0] = 125;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.APC.unitIndex()][1] = 65;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 115;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 65;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 125;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 75;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 115;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 17;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 125;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 55;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 22;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 55;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 15;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 50;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.LANDER.unitIndex()][0] = 40;
		damageMatrix[UnitType.NEOTANK.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 15;
//		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @artillery
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 90;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.MECH.unitIndex()][0] = 85;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.RECON.unitIndex()][0] = 80;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.TANK.unitIndex()][0] = 70;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 45;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 40;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.APC.unitIndex()][0] = 70;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 75;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 80;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 75;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 80;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 40;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 65;
		damageMatrix[UnitType.ARTILLERY.unitIndex()][UnitType.LANDER.unitIndex()][0] = 55;
//		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 60;

		// @rocket
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 95;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.MECH.unitIndex()][0] = 90;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.RECON.unitIndex()][0] = 90;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.TANK.unitIndex()][0] = 80;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 50;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.APC.unitIndex()][0] = 80;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 80;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 85;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 90;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 55;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 85;
		damageMatrix[UnitType.ROCKET.unitIndex()][UnitType.LANDER.unitIndex()][0] = 60;
//		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 85;

		// @a-air
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 105;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.MECH.unitIndex()][0] = 105;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.RECON.unitIndex()][0] = 60;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.TANK.unitIndex()][0] = 25;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 10;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 5;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.APC.unitIndex()][0] = 50;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 50;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 55;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 45;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 55;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = 65;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.BOMBER.unitIndex()][0] = 75;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitType.A_AIR.unitIndex()][UnitType.LANDER.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;

		// @missiles
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.RECON.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.TANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.MDTANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.APC.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.ROCKET.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.A_AIR.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.MISSILES.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = 100;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.BOMBER.unitIndex()][0] = 100;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitType.MISSILES.unitIndex()][UnitType.LANDER.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;

//------------ Air ----------------

		// @fighter
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.RECON.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.TANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.MDTANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.APC.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.ROCKET.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.A_AIR.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.MISSILES.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = 55;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.BOMBER.unitIndex()][0] = 100;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = 100;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = 100;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitType.FIGHTER.unitIndex()][UnitType.LANDER.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;

		// @bomber
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 110;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.MECH.unitIndex()][0] = 110;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.RECON.unitIndex()][0] = 105;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.TANK.unitIndex()][0] = 105;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 95;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 90;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.APC.unitIndex()][0] = 105;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 105;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 105;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 95;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 105;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 75;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 85;
		damageMatrix[UnitType.BOMBER.unitIndex()][UnitType.LANDER.unitIndex()][0] = 95;
//		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 95;

		// @bcopter
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = 75;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.MECH.unitIndex()][1] = 75;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.RECON.unitIndex()][0] = 55;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.RECON.unitIndex()][1] = 30;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.TANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 25;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 20;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.APC.unitIndex()][0] = 60;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.APC.unitIndex()][1] = 20;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 65;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = 25;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 65;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.ROCKET.unitIndex()][1] = 35;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 25;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.A_AIR.unitIndex()][1] = 6;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 65;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.MISSILES.unitIndex()][1] = 35;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 65;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 95;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 25;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 55;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.LANDER.unitIndex()][0] = 25;
		damageMatrix[UnitType.BCOPTER.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 25;
//		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

//------------ Sea ----------------

		// @battleship
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = 95;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.MECH.unitIndex()][0] = 90;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.RECON.unitIndex()][0] = 90;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.TANK.unitIndex()][0] = 80;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.MDTANK.unitIndex()][0] = 55;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = 50;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.APC.unitIndex()][0] = 80;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = 80;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.A_AIR.unitIndex()][0] = 85;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.MISSILES.unitIndex()][0] = 90;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = 50;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.CRUISER.unitIndex()][0] = 95;
		damageMatrix[UnitType.BATTLESHIP.unitIndex()][UnitType.LANDER.unitIndex()][0] = 95;
//		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 95;

		// @cruiser
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.INFANTRY.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.MECH.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.RECON.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.RECON.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.TANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.TANK.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.MDTANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.MDTANK.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.NEOTANK.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.NEOTANK.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.APC.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.APC.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.ARTILLERY.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.ARTILLERY.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.ROCKET.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.ROCKET.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.A_AIR.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.A_AIR.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.MISSILES.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.MISSILES.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.FIGHTER.unitIndex()][1] = 55;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.BOMBER.unitIndex()][1] = 65;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.BCOPTER.unitIndex()][1] = 115;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.TCOPTER.unitIndex()][1] = 115;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.LANDER.unitIndex()][0] = -1;
		damageMatrix[UnitType.CRUISER.unitIndex()][UnitType.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 90;
//		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;
	}

	public int[][][] getDamageMatrix() {
		return damageMatrix;
	}
}