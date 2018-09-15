package graphics;

import java.awt.Color;
import java.awt.Graphics;

import menus.DimensionValues;

public class MenuArrow {
	private DimensionValues dimensionValues;

	public MenuArrow(DimensionValues dimensionValues) {
		this.dimensionValues = dimensionValues;
	}
	
	public void paint(Graphics g, int x, int y, int menuIndex) {
		int arrowWidth = dimensionValues.getTileSize() / 2;

		int menuX = x * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2;
		int menuY = y * dimensionValues.getTileSize() + dimensionValues.getTileSize() / 2;

		int ax1 = menuX + 1 - arrowWidth;
		int ax2 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax3 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax4 = menuX + 1;
		int ax5 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax6 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax7 = menuX + 1 - arrowWidth;

		int ay1 = menuY + dimensionValues.getAlignY() + 3 + dimensionValues.getMenuRowHeight() * menuIndex;
		int ay2 = menuY + dimensionValues.getAlignY() + 3 + dimensionValues.getMenuRowHeight() * menuIndex;
		int ay3 = menuY + 1 + dimensionValues.getMenuRowHeight() * menuIndex;
		int ay4 = menuY + 2 + dimensionValues.getAlignY() + dimensionValues.getMenuRowHeight() / 2 + dimensionValues.getMenuRowHeight() * menuIndex;
		int ay5 = menuY + 3 + dimensionValues.getAlignY() * 2 + dimensionValues.getMenuRowHeight() * (menuIndex + 1);
		int ay6 = menuY + 1 + dimensionValues.getAlignY() + dimensionValues.getMenuRowHeight() * (menuIndex + 1);
		int ay7 = menuY + 1 + dimensionValues.getAlignY() + dimensionValues.getMenuRowHeight() * (menuIndex + 1);

		int[] ax = {ax1, ax2, ax3, ax4, ax5, ax6, ax7};
		int[] ay = {ay1, ay2, ay3, ay4, ay5, ay6, ay7};

		g.setColor(Color.orange);
		g.fillPolygon(ax, ay, 7);

		g.setColor(Color.black);
		g.drawPolygon(ax, ay, 7);
	}
}
