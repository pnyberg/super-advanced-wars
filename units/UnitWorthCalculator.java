package units;

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

public class UnitWorthCalculator {

	public int getFullHealthUnitWorth(Unit unit) {
		int worth = 0;
		if (unit instanceof Infantry) {
			worth = Infantry.getPrice();
		} else if (unit instanceof Mech) {
			worth = Mech.getPrice();
		} else if (unit instanceof Recon) {
			worth = Recon.getPrice();
		} else if (unit instanceof Tank) {
			worth = Tank.getPrice();
		} else if (unit instanceof MDTank) {
			worth = MDTank.getPrice();
		} else if (unit instanceof Neotank) {
			worth = Neotank.getPrice();
		} else if (unit instanceof APC) {
			worth = APC.getPrice();
		} else if (unit instanceof Artillery) {
			worth = Artillery.getPrice();
		} else if (unit instanceof Rocket) {
			worth = Rocket.getPrice();
		} else if (unit instanceof AAir) {
			worth = AAir.getPrice();
		} else if (unit instanceof Missiles) {
			worth = Missiles.getPrice();
		} else if (unit instanceof Fighter) {
			worth = Fighter.getPrice();
		} else if (unit instanceof Bomber) {
			worth = Bomber.getPrice();
		} else if (unit instanceof BCopter) {
			worth = BCopter.getPrice();
		} else if (unit instanceof TCopter) {
			worth = TCopter.getPrice();
		} else if (unit instanceof Battleship) {
			worth = Battleship.getPrice();
		} else if (unit instanceof Cruiser) {
			worth = Cruiser.getPrice();
		} else if (unit instanceof Lander) {
			worth = Lander.getPrice();
/*		} else if (unit instanceof Sub) {
			worth = Sub.getPrice();
*/
		}
		
		return worth;
	}
	
	public int getUnitWorth(Unit unit) {
		int worth = getFullHealthUnitWorth(unit);
		worth *= unit.getUnitHealth().getShowHP();
		worth /= 10;
		return worth;
	}
}