/**
 * TODO:
 *  - take DimensionValues as parameter instead
 */
package menus;

import java.awt.Color;
import java.awt.Graphics;

import graphics.MenuArrow;

public abstract class Menu {
	protected int tileSize;
	protected MenuState menuState;
	protected DimensionValues dimensionValues;	
	private MenuArrow arrow;

	public Menu(MenuState menuState, int tileSize) {
		this.tileSize = tileSize;
		this.menuState = menuState;
		dimensionValues = new DimensionValues();
		dimensionValues.tileSize = tileSize;
		dimensionValues.xAlign = 4;
		dimensionValues.yAlign = 3;
		dimensionValues.menuRowWidth = 80;
		dimensionValues.menuRowHeight = 15;
		arrow = new MenuArrow(dimensionValues);
	}

	public void openMenu(int x, int y) {
		menuState.setPosition(x, y);
		menuState.setVisible(true);
	}

	public void closeMenu() {
		menuState.setVisible(false);
		menuState.resetMenuIndex();
	}

	public void moveArrowUp() {
		menuState.decrementMenuIndexIfPossible();
	}

	public void moveArrowDown() {
		menuState.incrementMenuIndexIfPossible(getNumberOfRows());
	}

	public int getMenuIndex() {
		return menuState.getMenuIndex();
	}
	
	public abstract int getNumberOfRows();

	public boolean isVisible() {
		return menuState.isVisible();
	}
	
	public boolean atEndRow() {
		return getMenuIndex() == getNumberOfRows()-1;
	}

	protected void paintMenuBackground(Graphics g) {
		int x = menuState.getX();
		int y = menuState.getY();
		int menuHeight = getNumberOfRows() * dimensionValues.getMenuRowHeight() + 10;
		int xAlign = dimensionValues.getTileSize() / 2;
		int yAlign = dimensionValues.getTileSize() / 2;

		g.setColor(Color.white);
		g.fillRect(x + xAlign, y + yAlign, dimensionValues.getMenuRowWidth(), menuHeight);
		g.setColor(Color.black);
		g.fillRect(x + xAlign, y + yAlign, dimensionValues.getMenuRowWidth(), 2); 
		g.fillRect(x + xAlign, y + yAlign, 2, menuHeight); 
		g.fillRect(x + xAlign + dimensionValues.getMenuRowWidth() - 2, y + yAlign, 2, menuHeight); 
		g.fillRect(x + xAlign, y + yAlign + menuHeight - 2, dimensionValues.getMenuRowWidth(), 2);
	}

	protected void paintArrow(Graphics g) {
		int x = menuState.getX();
		int y = menuState.getY();
		int menuIndex = menuState.getMenuIndex();
		arrow.paint(g, x, y, menuIndex);
	}
}