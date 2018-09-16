package menus.map;

import java.awt.Graphics;

import menus.Menu;

public class MapMenu extends Menu {
	private String[] menuTexts = {	"CO", 
									"Intel", 
									"Power", 
									"Super Power", 
									"Options", 
									"Save", 
									"End turn"
								};
	private boolean power, superPower;

	public MapMenu(int tileSize) {
		super(tileSize);

		power = false;
		superPower = false;
	}

	public void setPower(boolean power) {
		this.power = power;
	}
	
	public void setSuperPower(boolean superPower) {
		this.superPower = superPower;
	}
	
	public int getNumberOfRows() {
		return menuTexts.length + (power ? 0 : -1) + (superPower ? 0 : -1);
	}

	public void paint(Graphics g) {
		int menuX = x * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2 + dimensionValues.getAlignX();
		int menuY = y * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2 + dimensionValues.getAlignY();

		paintMenuBackground(g);

		int rowHelpIndex = 1;
		for (int k = 0 ; k < menuTexts.length ; k++) {
			if (k == 2 && !power) {
				continue;
			}
			if (k == 3 && !superPower) {
				continue;
			}
			g.drawString(menuTexts[k], menuX, menuY + dimensionValues.getMenuRowHeight() * rowHelpIndex);
			rowHelpIndex++;
		}

		paintArrow(g);
	}
}