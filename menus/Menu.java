/**
 * TODO: take DimensionValues as parameter instead
 */
package menus;

import java.awt.Color;
import java.awt.Graphics;

import graphics.MenuArrow;

public abstract class Menu {
	protected int x;
	protected int y;
	protected int tileSize;
	protected int menuIndex;
	protected boolean visible;
	protected DimensionValues dimensionValues;	
	private MenuArrow arrow;

	public Menu(int tileSize) {
		x = 0;
		y = 0;
		this.tileSize = tileSize;
		menuIndex = 0;
		visible = false;
		dimensionValues = new DimensionValues();
		dimensionValues.tileSize = tileSize;
		dimensionValues.xAlign = 4;
		dimensionValues.yAlign = 3;
		dimensionValues.menuRowWidth = 80;
		dimensionValues.menuRowHeight = 15;
		arrow = new MenuArrow(dimensionValues);
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
		} else {
			menuIndex = getNumberOfRows() - 1;
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
		int menuHeight = getNumberOfRows() * dimensionValues.getMenuRowHeight() + 10;
		int menuX = x * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2;
		int menuY = y * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2;

		g.setColor(Color.white);
		g.fillRect(menuX, menuY, dimensionValues.getMenuRowWidth(), menuHeight);
		g.setColor(Color.black);
		g.fillRect(menuX, menuY, dimensionValues.getMenuRowWidth(), 2); 
		g.fillRect(menuX, menuY, 2, menuHeight); 
		g.fillRect(menuX + dimensionValues.getMenuRowWidth() - 2, menuY, 2, menuHeight); 
		g.fillRect(menuX, menuY + menuHeight - 2, dimensionValues.getMenuRowWidth(), 2);
	}

	protected void paintArrow(Graphics g) {
		arrow.paint(g, x, y, menuIndex);
	}
}