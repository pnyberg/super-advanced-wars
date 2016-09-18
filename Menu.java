import java.awt.Color;
import java.awt.Graphics;

public class Menu {
	private boolean visible, power, superPower;
	private int x, y, menuIndex, numberOfRows, tileSize;

	public Menu(int tileSize) {
		x = 0;
		y = 0;
		menuIndex = 0;
		this.tileSize = tileSize;
		visible = false;
		power = false;
		superPower = false;
		numberOfRows = 5;
	}

	public void openMenu(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}

	public void closeMenu() {
		visible = false;
		menuIndex = 0;		
	}

	public void moveArrowUp() {
		numberOfRows = 5 + (power ? 1 : 0) + (superPower ? 1 : 0);

		if (menuIndex > 0) {
			menuIndex--;
		}
	}

	public void moveArrowDown() {
		numberOfRows = 5 + (power ? 1 : 0) + (superPower ? 1 : 0);

		if (menuIndex < (numberOfRows - 1)) {
			menuIndex++;
		}
	}

	// updates if power/super power should be shown in the menu
	public void updatePower(boolean power, boolean superPower) {
		this.power = power;
		this.superPower = superPower;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void paint(Graphics g) {
		int menuRowHeight = 15;
		int menuWidth = (tileSize * 5 / 3);
		int menuHeight = 10 + numberOfRows * menuRowHeight;
		int xAlign = 4;
		int yAlign = 3;

		int arrowWidth = tileSize / 2;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		g.setColor(Color.white);
		g.fillRect(menuX, menuY, menuWidth, menuHeight);
		g.setColor(Color.black);
		g.fillRect(menuX, menuY, menuWidth, 2); 
		g.fillRect(menuX, menuY, 2, menuHeight); 
		g.fillRect(menuX + menuWidth - 2, menuY, 2, menuHeight); 
		g.fillRect(menuX, menuY + menuHeight - 2, menuWidth, 2); 

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
		g.drawString("End", menuX + xAlign, menuY + yAlign + menuRowHeight * (rowHelpIndex + 2));

		int ax1 = menuX + 1 - arrowWidth;
		int ax2 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax3 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax4 = menuX + 1;
		int ax5 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax6 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax7 = menuX + 1 - arrowWidth;

		int ay1 = menuY + yAlign + 3 + menuRowHeight * menuIndex;
		int ay2 = menuY + yAlign + 3 + menuRowHeight * menuIndex;
		int ay3 = menuY + 1 + menuRowHeight * menuIndex;
		int ay4 = menuY + 2 + yAlign + menuRowHeight / 2 + menuRowHeight * menuIndex;
		int ay5 = menuY + 3 + yAlign * 2 + menuRowHeight * (menuIndex + 1);
		int ay6 = menuY + 1 + yAlign + menuRowHeight * (menuIndex + 1);
		int ay7 = menuY + 1 + yAlign + menuRowHeight * (menuIndex + 1);

		int[] ax = {ax1, ax2, ax3, ax4, ax5, ax6, ax7};
		int[] ay = {ay1, ay2, ay3, ay4, ay5, ay6, ay7};

		g.setColor(Color.orange);
		g.fillPolygon(ax, ay, 7);

		g.setColor(Color.black);
		g.drawPolygon(ax, ay, 7);
	}
}