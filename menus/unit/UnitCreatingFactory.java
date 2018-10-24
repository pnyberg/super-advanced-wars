/**
 * TODO: 
 *  - Add sub
 */
package menus.unit;

import java.awt.Color;

import units.Unit;
import units.airMoving.*;
import units.footMoving.*;
import units.seaMoving.*;
import units.tireMoving.*;
import units.treadMoving.*;

public class UnitCreatingFactory {
	private int tileSize;
	
	public UnitCreatingFactory(int tileSize) {
		this.tileSize = tileSize;
	}
	
	public Unit createUnit(String unitName, int x, int y, Color color) {
		if (unitName.equals(Infantry.getTypeName())) {
			return new Infantry(x, y, color, tileSize);
		} else if (unitName.equals(Mech.getTypeName())) {
			return new Mech(x, y, color, tileSize);
		} else if (unitName.equals(Recon.getTypeName())) {
			return new Recon(x, y, color, tileSize);
		} else if (unitName.equals(Tank.getTypeName())) {
			return new Tank(x, y, color, tileSize);
		} else if (unitName.equals(MDTank.getTypeName())) {
			return new MDTank(x, y, color, tileSize);
		} else if (unitName.equals(Neotank.getTypeName())) {
			return new Neotank(x, y, color, tileSize);
		} else if (unitName.equals(APC.getTypeName())) {
			return new APC(x, y, color, tileSize);
		} else if (unitName.equals(Artillery.getTypeName())) {
			return new Artillery(x, y, color, tileSize);
		} else if (unitName.equals(Rocket.getTypeName())) {
			return new Rocket(x, y, color, tileSize);
		} else if (unitName.equals(AAir.getTypeName())) {
			return new AAir(x, y, color, tileSize);
		} else if (unitName.equals(Missiles.getTypeName())) {
			return new Missiles(x, y, color, tileSize);
		} else if (unitName.equals(Fighter.getTypeName())) {
			return new Fighter(x, y, color, tileSize);
		} else if (unitName.equals(Bomber.getTypeName())) {
			return new Bomber(x, y, color, tileSize);
		} else if (unitName.equals(BCopter.getTypeName())) {
			return new BCopter(x, y, color, tileSize);
		} else if (unitName.equals(TCopter.getTypeName())) {
			return new TCopter(x, y, color, tileSize);
		} else if (unitName.equals(Battleship.getTypeName())) {
			return new Battleship(x, y, color, tileSize);
		} else if (unitName.equals("Cruiser")) {
			return new Cruiser(x, y, color, tileSize);
		} else if (unitName.equals(Lander.getTypeName())) {
			return new Lander(x, y, color, tileSize);
//		} else if (unitName.equals("Sub")) {
//			return new Sub(x, y, color, tileSize);
		}

		return null;
	}
}