/**
 * TODO: 
 *  - write cargo as actual unit and not "Unit " + [number]
 */
package menus.unit;

import units.*;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.GameState;
import map.UnitGetter;
import menus.Menu;
import menus.UnitMenuState;
import unitUtils.UnitType;

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
	private int iconSize;

	public UnitMenu(int tileSize, GameState gameState) {
		super(gameState.getUnitMenuState(), tileSize);
		iconSize = tileSize / 4;
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

	public int getNumberOfActiveRows() {
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
					int unitMenuItemPosY = y + yAlign + dimensionValues.getMenuRowHeight() * rowHelpIndex;
					paintUnitIcon(g, cargo.get(i), x + xAlign, unitMenuItemPosY - iconSize);
					g.drawString("Drop", x + xAlign + iconSize + 2, unitMenuItemPosY);
					rowHelpIndex++;
				}
			}
			if (unitMenuRowEntryBooleans[k]) {
				int unitMenuItemPosY = y + yAlign + dimensionValues.getMenuRowHeight() * rowHelpIndex;
				g.drawString(unitMenuRowEntryText[k], x + xAlign + iconSize + 2, unitMenuItemPosY);
				rowHelpIndex++;
			}
		}
		paintArrow(g);
	}
	
	private void paintUnitIcon(Graphics g, Unit unit, int x, int y) {
		String unitTypeName = UnitType.getUnitTypeNameFromUnit(unit);
		UnitCreatingFactory unitCreatingFactory = new UnitCreatingFactory(iconSize);
		Unit iconUnit = unitCreatingFactory.createUnit(unitTypeName, x, y, unit.getColor());
		iconUnit.paint(g, iconSize);
	}
}