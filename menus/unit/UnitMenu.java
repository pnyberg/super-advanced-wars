/**
 * TODO: 
 *  - write cargo as actual unit and not "Unit " + [number]
 */
package menus.unit;

import units.*;

import java.awt.Graphics;
import java.util.ArrayList;

import menus.Menu;

public class UnitMenu extends Menu {
	private final String[] unitMenuRowEntryText = {	"Join", 
													"Enter", 
													"Fire", 
													"Capt", 
													"Launch", 
													"Dive", 
													"Emerge", 
													"Supply", 
													"Wait"
												};
	private UnitMenuRowEntryBooleanHandler unitMenuRowEntryBooleanHandler;
	private ArrayList<Unit> cargo;

	public UnitMenu(int tileSize) {
		super(tileSize);

		cargo = new ArrayList<Unit>();
		unitMenuRowEntryBooleanHandler = new UnitMenuRowEntryBooleanHandler();
	}

	public void closeMenu() {
		visible = false;
		menuIndex = 0;		

		unitMenuRowEntryBooleanHandler.clear();
		cargo.clear();
	}

	public void containedCargo(Unit containedUnit) {
		cargo.add(containedUnit);
	}

	public void containedCargo(ArrayList<Unit> containedCargo) {
		for (int i = 0 ; i < containedCargo.size() ; i++) {
			cargo.add(containedCargo.get(i));
		}
	}

	public int getNumberOfRows() {
		int numberOfRows = unitMenuRowEntryBooleanHandler.getNumberOfExistingRows();

		if (cargo.size() > 0) {
			numberOfRows += cargo.size();
		}
		
		return numberOfRows;
	}

	public boolean atUnitRow() {
		return menuIndex < cargo.size();
	}

	public boolean atJoinRow() {
		return unitMenuRowEntryBooleanHandler.join == true;
	}

	public boolean atEnterRow() {
		if (!unitMenuRowEntryBooleanHandler.enter == true) {
			return false;
		}

		return menuIndex == 0;
	}
	
	public boolean atFireRow() {
		if (!unitMenuRowEntryBooleanHandler.fire == true) {
			return false;
		}

		return menuIndex == 0;
	}
	
	public boolean atCaptRow() {
		if (!unitMenuRowEntryBooleanHandler.capt == true) {
			return false;
		}
		if (unitMenuRowEntryBooleanHandler.fire == true) {
			return menuIndex == 1;
		} else {
			return menuIndex == 0;
		}
	}

	public boolean atSupplyRow() {
		if (!unitMenuRowEntryBooleanHandler.supply == true) {
			return false;
		}

		return menuIndex == cargo.size();
	}

	public UnitMenuRowEntryBooleanHandler getUnitMenuRowEntryBooleanHandler() {
		return unitMenuRowEntryBooleanHandler;
	}

	public void paint(Graphics g) {
		int xAlign = dimensionValues.getTileSize() / 2 + 2 * dimensionValues.getAlignX();
		int yAlign = dimensionValues.getTileSize() / 2 + dimensionValues.getAlignY();

		paintMenuBackground(g);

		int rowHelpIndex = 1;
		boolean[] unitMenuRowEntryBooleans = unitMenuRowEntryBooleanHandler.getAsBooleanArray();
		for (int k = 0 ; k < unitMenuRowEntryBooleans.length ; k++) {
			if (k == 2 && cargo.size() > 0) {
				for (int i = 0 ; i < cargo.size() ; i++) {
					// TODO: write the actual unit (type)
					g.drawString("Unit" + (i+1), x + xAlign, y + yAlign + dimensionValues.getMenuRowHeight() * rowHelpIndex);
					rowHelpIndex++;
				}
			}
			if (unitMenuRowEntryBooleans[k]) {
				g.drawString(unitMenuRowEntryText[k], x + xAlign, y + yAlign + dimensionValues.getMenuRowHeight() * rowHelpIndex);
				rowHelpIndex++;
			}
		}
		paintArrow(g);
	}
}