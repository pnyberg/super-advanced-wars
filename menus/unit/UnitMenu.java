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
													"Wait"};
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

	public boolean atFireRow() {
		if (!unitMenuRowEntryBooleanHandler.mayFire()) {
			return false;
		}

		return menuIndex == 0;
	}

	public boolean atSupplyRow() {
		if (!unitMenuRowEntryBooleanHandler.maySupply()) {
			return false;
		}

		return menuIndex == cargo.size();
	}

	public boolean atEnterRow() {
		if (!unitMenuRowEntryBooleanHandler.mayEnter()) {
			return false;
		}

		return menuIndex == 0;
	}
	
	public boolean atJoinRow() {
		return unitMenuRowEntryBooleanHandler.mayJoin();
	}

	public UnitMenuRowEntryBooleanHandler getUnitMenuRowEntryBooleanHandler() {
		return unitMenuRowEntryBooleanHandler;
	}

	public void paint(Graphics g) {
		int menuX = x * tileSize + tileSize / 2 + xAlign;
		int menuY = y * tileSize + tileSize / 2 + yAlign;

		paintMenuBackground(g);

		int rowHelpIndex = 1;
		boolean[] unitMenuRowEntryBooleans = unitMenuRowEntryBooleanHandler.getAsBooleanArray();
		for (int k = 0 ; k < unitMenuRowEntryBooleans.length ; k++) {
			if (k == 2 && cargo.size() > 0) {
				for (int i = 0 ; i < cargo.size() ; i++) {
					g.drawString("Unit" + (i+1), menuX, menuY + menuRowHeight * rowHelpIndex);
					rowHelpIndex++;
				}
			}
			if (unitMenuRowEntryBooleans[k]) {
				g.drawString(unitMenuRowEntryText[k], menuX + xAlign, menuY + menuRowHeight * rowHelpIndex);
				rowHelpIndex++;
			}
		}

		paintArrow(g);
	}
}