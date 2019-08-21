/**
 * TODO: 
 *  - write cargo as actual unit and not "Unit " + [number]
 */
package menus.unit;

import units.*;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.GameState;
import menus.Menu;
import menus.UnitMenuState;

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

	public UnitMenu(int tileSize, GameState gameState) {
		super(gameState.getUnitMenuState(), tileSize);
	}

	public void closeMenu() {
		super.closeMenu();
		((UnitMenuState)menuState).resetRowEntries();
	}

	public void addContainedCargoRow(Unit containedUnit) {
		((UnitMenuState)menuState).addCargoRow(containedUnit);
	}

	public void addContainedCargoRows(ArrayList<Unit> containedCargo) {
		for (int i = 0 ; i < containedCargo.size() ; i++) {
			addContainedCargoRow(containedCargo.get(i));
		}
	}
	
	public void enableCaptOption() {
		((UnitMenuState)menuState).setCaptOption(true);
	}

	public void enableDiveOption() {
		((UnitMenuState)menuState).setDiveOption(true);
	}

	public void enableEmergeOption() {
		((UnitMenuState)menuState).setEmergeOption(true);
	}

	public void enableEnterOption() {
		((UnitMenuState)menuState).setEnterOption(true);
	}

	public void enableFireOption() {
		((UnitMenuState)menuState).setFireOption(true);
	}

	public void enableJoinOption() {
		((UnitMenuState)menuState).setJoinOption(true);
	}

	public void enableLaunchOption() {
		((UnitMenuState)menuState).setLaunchOption(true);
	}

	public void enableSupplyOption() {
		((UnitMenuState)menuState).setSupplyOption(true);
	}

	public void enableWaitOption() {
		((UnitMenuState)menuState).setWaitOption(true);
	}

	public int getNumberOfRows() {
		return ((UnitMenuState)menuState).getNumberOfExistingRows();
	}

	public boolean atUnitRow() {
		return ((UnitMenuState)menuState).atUnitRow();
	}

	public boolean atJoinRow() {
		return ((UnitMenuState)menuState).atJoinRow();
	}

	public boolean atEnterRow() {
		return ((UnitMenuState)menuState).atEnterRow();
	}
	
	public boolean atFireRow() {
		return ((UnitMenuState)menuState).atFireRow();
	}
	
	public boolean atCaptRow() {
		return ((UnitMenuState)menuState).atCaptRow();
	}

	public boolean atSupplyRow() {
		return ((UnitMenuState)menuState).atSupplyRow();
	}

	public void paint(Graphics g) {
		int x = menuState.getX();
		int y = menuState.getY();
		int xAlign = dimensionValues.getTileSize() / 2 + 2 * dimensionValues.getAlignX();
		int yAlign = dimensionValues.getTileSize() / 2 + dimensionValues.getAlignY();

		paintMenuBackground(g);

		int rowHelpIndex = 1;
		boolean[] unitMenuRowEntryBooleans = ((UnitMenuState)menuState).getRowEntryShowingStatus();
		ArrayList<Unit> cargo = ((UnitMenuState)menuState).getCargo();
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