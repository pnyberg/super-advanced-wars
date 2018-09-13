package units.tireMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.IndirectUnit;
import units.Unit;

public class Missiles extends IndirectUnit {
	private static int price = 12000;
	private static String typeName = "Missiles";

	public Missiles(int x, int y, Color color) {
		super(x, y, color);

		movement = 4;
		movementType = Unit.TIRE;
		unitClass = Unit.VEHICLE;
		minimumRange = 3;
		maximumRange = 5;

		maxFuel = 50;
		maxAmmo = 6;
		replentish();
	}

	public static void setPrice(int price) {
		Missiles.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int cx1 = x * tileSize + 2 * tileSize / 3 + 1;
		int cy1 = y * tileSize + tileSize / 10 - 1;
		int cx2 = x * tileSize + 8 * tileSize / 10;
		int cy2 = y * tileSize + tileSize / 4 + 4;
		int cx3 = x * tileSize + tileSize / 4 - 2;
		int cy3 = y * tileSize + tileSize / 2 + 5;
		int cx4 = x * tileSize + tileSize / 5 - 4;
		int cy4 = y * tileSize + 2 * tileSize / 5 - 2;

		int bodyWidth = 2 * tileSize / 5 + 5;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 3 - 3;
		int bodyAlignY = 7 * tileSize / 20 + 3;

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);
	}
}