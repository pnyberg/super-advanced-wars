import java.awt.Color;
import java.awt.Graphics;

public class Menu {
	private boolean visible, power, superPower;
	private int x, y, tileSize;

	public Menu(int tileSize) {
		x = 0;
		y = 0;
		this.tileSize = tileSize;
		visible = false;
		power = false;
		superPower = false;
	}

	public void openMenu(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}

	public void closeMenu() {
		visible = false;
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
		int rowHelpIndex = 3;
		int numberOfRows = 5 + (power ? 1 : 0) + (superPower ? 1 : 0);
		int menuWidth = (tileSize * 5 / 3);
		int menuHeight = 10 + numberOfRows * menuRowHeight;
		int xAlign = 4;
		int yAlign = 3;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		g.setColor(Color.white);
		g.fillRect(menuX, menuY, menuWidth, menuHeight);
		g.setColor(Color.black);
		g.fillRect(menuX, menuY, menuWidth, 2); 
		g.fillRect(menuX, menuY, 2, menuHeight); 
		g.fillRect(menuX + menuWidth - 2, menuY, 2, menuHeight); 
		g.fillRect(menuX, menuY + menuHeight - 2, menuWidth, 2); 

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
	}
}