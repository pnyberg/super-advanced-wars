package menus;

import java.awt.Color;
import java.awt.Graphics;

import menus.icons.MenuArrow;

public abstract class Menu {
	protected int x;
	protected int y;
	protected int menuIndex;
	protected int tileSize;
	protected int menuWidth;
	protected boolean visible;
	
	private MenuArrow arrow;

	protected final int menuRowHeight = 15;
	protected final int xAlign = 4;
	protected final int yAlign = 3;

	public Menu(int tileSize) {
		x = 0;
		y = 0;
		menuIndex = 0;
		this.tileSize = tileSize;
		menuWidth = (tileSize * 5 / 3);
		visible = false;
		arrow = new MenuArrow(tileSize, menuRowHeight, yAlign);
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
		if (menuIndex > 0) {
			menuIndex--;
		}
	}

	public void moveArrowDown() {
		if (menuIndex < (getNumberOfRows() - 1)) {
			menuIndex++;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getMenuIndex() {
		return menuIndex;
	}
	
	public abstract int getNumberOfRows();

	public boolean isVisible() {
		return visible;
	}
	
	public boolean atEndRow() {
		return menuIndex == getNumberOfRows()-1;
	}

	protected void paintMenuBackground(Graphics g) {
		int menuHeight = getNumberOfRows() * menuRowHeight + 10;
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
		arrow.paint(g, x, y, menuIndex);
	}
}