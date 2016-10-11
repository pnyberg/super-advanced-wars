package menus;

import java.awt.Color;
import java.awt.Graphics;

public class MapMenu extends Menu {
	private boolean power, superPower;

	public MapMenu(int tileSize) {
		super(tileSize);

		power = false;
		superPower = false;
		numberOfRows = 5;
	}

	protected void updateNumberOfRows() {
		numberOfRows = 5 + (power ? 1 : 0) + (superPower ? 1 : 0);
	}

	// updates if power/super power should be shown in the menu
	public void updatePower(boolean power, boolean superPower) {
		this.power = power;
		this.superPower = superPower;
	}

	public boolean atEndRow() {
		if (superPower) {
			return menuIndex == 6;
		} else if (power) {
			return menuIndex == 5;
		} else {
			return menuIndex == 4;
		}
	}

	public void paint(Graphics g) {
		menuHeight = 10 + numberOfRows * menuRowHeight;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		paintMenuBackground(g);

		int rowHelpIndex = 3;

		g.drawString("CO", menuX + xAlign, menuY + yAlign + menuRowHeight);
		g.drawString("Intel", menuX + xAlign, menuY + yAlign + menuRowHeight * 2);
		if (power) {
			g.drawString("Power", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}
		if (superPower) {
			g.drawString("Super Power", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}
		g.drawString("Options", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
		g.drawString("Save", menuX + xAlign, menuY + yAlign + menuRowHeight * (rowHelpIndex + 1));
		g.drawString("End turn", menuX + xAlign, menuY + yAlign + menuRowHeight * (rowHelpIndex + 2));

		paintArrow(g);
	}
}