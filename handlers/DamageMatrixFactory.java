/**
 * TODO:
 *  - add Sub
 *  - read values from file
 */
package handlers;

import units.Unit;
import units.UnitTypes;

public class DamageMatrixFactory {
	private static final int number = Unit.numberOfUnitTypes;
	
	private int[][][] damageMatrix;

	public int[][][] getDamageMatrix() {
		damageMatrix = new int[number][number][2]; // the last one is for secondary weapons
		implementDamageMatrix();
		return damageMatrix;
	}
	
	private void implementDamageMatrix() {
		for (int i = 0 ; i < number ; i++) {
			damageMatrix[UnitTypes.INFANTRY.unitIndex()][i][0] = -1;
			damageMatrix[UnitTypes.RECON.unitIndex()][i][0] = -1;
			damageMatrix[UnitTypes.APC_v.unitIndex()][i][0] = -1;
			damageMatrix[UnitTypes.APC_v.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.ARTILLERY.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.ROCKET.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.A_AIR.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.MISSILES.unitIndex()][i][1] = -1;

			damageMatrix[UnitTypes.FIGHTER.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.BOMBER.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.TCOPTER.unitIndex()][i][0] = -1;
			damageMatrix[UnitTypes.TCOPTER.unitIndex()][i][1] = -1;

			damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][i][1] = -1;
			damageMatrix[UnitTypes.LANDER.unitIndex()][i][0] = -1;
			damageMatrix[UnitTypes.LANDER.unitIndex()][i][1] = -1;
		}

//------------ Ground ----------------

		// @infantry
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 12;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 5;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 14;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 15;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 25;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 5;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 25;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = 7;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 30;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.INFANTRY.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @mech
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 18;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 15;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 15;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 20;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 70;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 32;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 6;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 9;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.MECH.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @recon
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 70;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 4;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 28;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 10;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.RECON.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @tank
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 25; // will not be used
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 75;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 25; // will not be used
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 70;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 40;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 15;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 15;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 70;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 5;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 30;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 10;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 40;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 5;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 10;
		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 1;
//		damageMatrix[UnitTypes.TANK.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @MDtank
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 30; // will not be used
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 30; // will not be used
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 95;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 8;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 45;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 7;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 12;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 45;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 10;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 45;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 35;
		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 10;
//		damageMatrix[UnitTypes.MDTANK.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @neotank
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 35; // will not be used
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 125;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 35; // will not be used
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 115;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 125;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 10;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 125;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 115;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 125;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 75;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 115;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 17;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 125;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 22;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 15;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 50;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 40;
		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 15;
//		damageMatrix[UnitTypes.NEOTANK.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

		// @artillery
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 70;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 45;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 40;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 70;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 40;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 55;
//		damageMatrix[UnitTypes.ARTILLERY.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 60;

		// @rocket
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 95;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 50;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 60;
//		damageMatrix[UnitTypes.ROCKET.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 85;

		// @a-air
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 60;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 25;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 10;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 5;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 50;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 50;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 45;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.A_AIR.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;

		// @missiles
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.RECON.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.TANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = 100;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = 100;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = 120;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.MISSILES.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;

//------------ Air ----------------

		// @fighter
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.RECON.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.TANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = 100;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = 100;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = 100;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = -1;
//		damageMatrix[UnitTypes.FIGHTER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = -1;

		// @bomber
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 110;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 110;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 95;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 95;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 105;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 75;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 95;
//		damageMatrix[UnitTypes.BOMBER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 95;

		// @bcopter
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = 75;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.MECH.unitIndex()][1] = 75;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.RECON.unitIndex()][1] = 30;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.TANK.unitIndex()][1] = 6;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 25;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 20;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = 1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 60;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = 20;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = 25;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 25;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = 6;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 65;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = 35;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 95;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 25;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 25;
		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 25;
//		damageMatrix[UnitTypes.BCOPTER.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;

//------------ Sea ----------------

		// @battleship
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = 95;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.MECH.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.RECON.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.TANK.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = 55;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = 50;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = 80;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = 85;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = 90;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = 50;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = 95;
		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = 95;
//		damageMatrix[UnitTypes.BATTLESHIP.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 95;

		// @cruiser
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.INFANTRY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.INFANTRY.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.MECH.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.MECH.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.RECON.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.RECON.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.TANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.TANK.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.MDTANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.MDTANK.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.NEOTANK.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.NEOTANK.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.APC_v.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.APC_v.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.ARTILLERY.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.ROCKET.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.ROCKET.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.A_AIR.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.A_AIR.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.MISSILES.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.MISSILES.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.FIGHTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.FIGHTER.unitIndex()][1] = 55;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.BOMBER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.BOMBER.unitIndex()][1] = 65;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.BCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.BCOPTER.unitIndex()][1] = 115;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.TCOPTER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.TCOPTER.unitIndex()][1] = 115;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.BATTLESHIP.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.CRUISER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.CRUISER.unitIndex()][1] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.LANDER.unitIndex()][0] = -1;
		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.LANDER.unitIndex()][1] = -1;
//		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.SUB.unitIndex()][0] = 90;
//		damageMatrix[UnitTypes.CRUISER.unitIndex()][UnitTypes.SUB.unitIndex()][1] = -1;
	}
}
