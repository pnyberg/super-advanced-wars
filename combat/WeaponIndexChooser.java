/**
 * TODO: add Sub
 */
package combat;

import units.Unit;
import units.airMoving.*;
import units.footMoving.*;
import units.seaMoving.*;
import units.tireMoving.*;
import units.treadMoving.*;

public class WeaponIndexChooser {
	public WeaponIndexChooser() {}

	public int getWeaponIndex(Unit attacker, Unit defender) {
		boolean tankOrMechOrCopterVsFootOrCopter = 
										   defender instanceof Infantry 
										|| defender instanceof Mech 
										|| defender instanceof BCopter
										|| defender instanceof TCopter;
		if (attacker instanceof Infantry) {
			return 1;
		} else if (attacker instanceof Mech) {
			if (tankOrMechOrCopterVsFootOrCopter) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof Recon) {
			return 1;
		} else if (attacker instanceof Tank) {
			if (tankOrMechOrCopterVsFootOrCopter) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof MDTank) {
			if (tankOrMechOrCopterVsFootOrCopter) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof Neotank) {
			if (tankOrMechOrCopterVsFootOrCopter) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof APC) {
			// Do nothing
		} else if (attacker instanceof Artillery) {
			return 0;
		} else if (attacker instanceof Rocket) {
			return 0;
		} else if (attacker instanceof AAir) {
			return 0;
		} else if (attacker instanceof Missiles) {
			return 0;
		} else if (attacker instanceof Fighter) {
			return 0;
		} else if (attacker instanceof Bomber) {
			return 0;
		} else if (attacker instanceof BCopter) {
			if (tankOrMechOrCopterVsFootOrCopter) {
				return 1;
			}
			return 0;
		} else if (attacker instanceof Battleship) {
			return 0;
		} else if (attacker instanceof Cruiser) {
/*			if (defender instanceof Sub) {
				return 0;
			}*/
			return 1;
		} else if (attacker instanceof Lander) {
			// Do nothing
/*		} else if (attacker instanceof Sub) {
			return 1;*/
		}

		return -1;
	}
}