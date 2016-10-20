package menus;

import point.*;
import units.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class UnitMenu extends Menu {
	private boolean 	join, 
						enter, 
						fire, 
						capt, 
						launch, 
						dive, 
						emerge, 
						supply, 
						wait;

	private ArrayList<Unit> cargo;

	public UnitMenu(int tileSize) {
		super(tileSize);

		join = false;
		enter = false;
		fire = false;
		capt = false;
		launch = false;
		dive = false;
		emerge = false;
		supply = false;
		wait = false;

		cargo = new ArrayList<Unit>();
	}

	public void closeMenu() {
		visible = false;
		menuIndex = 0;		

		join = false;
		enter = false;
		fire = false;
		capt = false;
		launch = false;
		dive = false;
		emerge = false;
		supply = false;
		wait = false;

		cargo.clear();
	}

	protected void updateNumberOfRows() {
		numberOfRows = 0;

		if (join) { numberOfRows++; }
		if (capt) { numberOfRows++; }
		if (dive) { numberOfRows++; }
		if (emerge) { numberOfRows++; }
		if (fire) { numberOfRows++; }
		if (launch) { numberOfRows++; }
		if (supply) { numberOfRows++; }
		if (enter) { numberOfRows++; }
		if (wait) { numberOfRows++; }

		if (cargo.size() > 0) {
			numberOfRows += cargo.size();
		}
	}

	public void unitMayJoin() {
		join = true;
	}

	public void unitMayEnter() {
		enter = true;
	}

	public void containedCargo(Unit containedUnit) {
		cargo.add(containedUnit);
	}

	// Adds cargo (they will  appear in the menu)
	public void containedCargo(ArrayList<Unit> containedCargo) {
		for (int i = 0 ; i < containedCargo.size() ; i++) {
			cargo.add(containedCargo.get(i));
		}
	}

	public void unitMayFire() {
		fire = true;
	}

	public void unitMayCapt() {
		capt = true;
	}

	public void unitMayLaunch() {
		launch = true;
	}

	public void unitMayDive() {
		dive = true;
	}

	public void unitMayEmerge() {
		emerge = true;
	}

	public void unitMaySupply() {
		supply = true;
	}

	public void unitMayWait() {
		wait = true;
	}

	// if units can be exited they will always come first
	public boolean atUnitRow() {
		return menuIndex < cargo.size();
	}

	public boolean atFireRow() {
		if (!fire) {
			return false;
		}

		int comparisonIndex = 0;

		if (join) { numberOfRows++; }
		if (capt) { numberOfRows++; }
		if (dive) { numberOfRows++; }
		if (emerge) { numberOfRows++; }

		return menuIndex == comparisonIndex;
	}

	public boolean atEnterRow() {
		if (!enter) {
			return false;
		}

		int comparisonIndex = 0;

		if (join) { comparisonIndex++; }

		return menuIndex == comparisonIndex;
	}

	public void paint(Graphics g) {
		menuHeight = 10 + numberOfRows * menuRowHeight;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		paintMenuBackground(g);

		int rowHelpIndex = 1;

		if (join) {
			g.drawString("Join", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (enter) {
			g.drawString("Enter", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		for (int i = 0 ; i < cargo.size() ; i++) {
			g.drawString("Unit" + (i+1), menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (fire) {
			g.drawString("Fire", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (capt) {
			g.drawString("Capt", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (launch) {
			g.drawString("Launch", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (dive) {
			g.drawString("Dive", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (emerge) {
			g.drawString("Emerge", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (supply) {
			g.drawString("Supply", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (wait) {
			g.drawString("Wait", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		paintArrow(g);
	}
}