import java.awt.Color;
import java.awt.Graphics;

public abstract class Menu {
	protected boolean visible;
	protected int x, y, menuIndex, numberOfRows, tileSize;

	protected final int menuRowHeight = 15;
	protected final int xAlign = 4;
	protected final int yAlign = 3;

	public Menu(int tileSize) {
		x = 0;
		y = 0;
		menuIndex = 0;
		this.tileSize = tileSize;
		visible = false;
		numberOfRows = 0;
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
		updateNumberOfRows();

		if (menuIndex > 0) {
			menuIndex--;
		}
	}

	public void moveArrowDown() {
		updateNumberOfRows();

		if (menuIndex < (numberOfRows - 1)) {
			menuIndex++;
		}
	}

	protected abstract void updateNumberOfRows();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	protected void paintMenuBackground(Graphics g) {
		int menuWidth = (tileSize * 5 / 3);
		int menuHeight = 10 + numberOfRows * menuRowHeight;
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
	}

	protected void paintArrow(Graphics g) {
		int menuWidth = (tileSize * 5 / 3);
		int menuHeight = 10 + numberOfRows * menuRowHeight;
		int arrowWidth = tileSize / 2;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

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