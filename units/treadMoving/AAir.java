package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.Unit;

public class AAir extends Unit {
	private static int price = 8000;
	private static String typeName = "A-Air";

	public AAir(int x, int y, Color color) {
		super(x, y, color);

		movement = 6;
		movementType = Unit.BAND;
		unitClass = Unit.VEHICLE;

		maxFuel = 60;
		maxAmmo = 9;
		replentish();
	}

	public static void setPrice(int price) {
		AAir.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		int headAlignX = paintX + tileSize / 3 - 1;
		int headAlignY = paintY + tileSize / 5;
		int headWidth = 3 * tileSize / 20 + 2;
		int headHeight = 4 * tileSize / 20 + 2;

		int bodyAlignX = headAlignX;
		int bodyAlignY = headAlignY + headHeight;
		int bodyWidth = 2 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;

		// cannon-points
		int cx1 = paintX + 3 * tileSize / 4;
		int cy1 = paintY + tileSize / 8;
		int cx2 = paintX + 7 * tileSize / 8 - 3;
		int cy2 = paintY + tileSize / 4 - 3;
		int cx3 = paintX + 3 * tileSize / 4 - 9;
		int cy3 = bodyAlignY;
		int cx4 = headAlignX + headWidth;
		int cy4 = bodyAlignY;
		int cx5 = headAlignX + headWidth;
		int cy5 = bodyAlignY - 4;

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4, cx5};
		int[] cannonY = {cy1, cy2, cy3, cy4, cy5};
		int npoints = 5;

		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(headAlignX, headAlignY, headWidth, headHeight); // head
		g.fillRect(bodyAlignX, bodyAlignY, bodyWidth, bodyHeight); // body
		g.fillPolygon(cannonX, cannonY, npoints); // cannon

		g.setColor(Color.black);
		g.drawRect(headAlignX, headAlignY, headWidth, headHeight); // head
		g.drawRect(bodyAlignX, bodyAlignY, bodyWidth, bodyHeight); // body
		g.drawPolygon(cannonX, cannonY, npoints); // cannon
	}
}