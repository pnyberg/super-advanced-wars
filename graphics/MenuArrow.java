package graphics;

import java.awt.Color;
import java.awt.Graphics;

public class MenuArrow {
	private int tileSize;
	private int menuRowHeight;
	private int yAlign;

	public MenuArrow(int tileSize, int menuRowHeight, int yAlign) {
		this.tileSize = tileSize;
		this.menuRowHeight = menuRowHeight;
		this.yAlign = yAlign;
	}
	
	public void paint(Graphics g, int x, int y, int menuIndex) {
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
