package units.airMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.Unit;

public class Bomber extends Unit {
	private static int price = 22000;
	private static String typeName = "Bomber";

	public Bomber(int x, int y, Color color) {
		super(x, y, color);

		movement = 7;
		movementType = Unit.AIR;
		unitClass = Unit.PLANE;

		maxFuel = 99;
		maxAmmo = 9;
		replentish();
	}

	public static void setPrice(int price) {
		Bomber.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int bodyWidth = 3 * tileSize / 4;
		int bodyHeight = tileSize / 2;
		int bodyAlignX = 3 * tileSize / 20;
		int bodyAlignY = tileSize / 5 + 3;

		int x1 = x * tileSize + tileSize / 3 - 3;
		int x2 = x * tileSize + 2 * tileSize / 5 - 3;
		int x3 = x * tileSize + 3 * tileSize / 5 + 4;
		int x4 = x * tileSize + 3 * tileSize / 4 + 1;
		int y1 = y * tileSize + 5 * tileSize / 10;
		int y2 = y * tileSize + 9 * tileSize / 10;
		int y3 = y2;
		int y4 = y1;

		int number = 4;
		int[] cx = {x1, x2, x3, x4};
		int[] cy = {y1, y2, y3, y4};

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawOval(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		// wings
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillPolygon(cx, cy, number);

		g.setColor(Color.black);
		g.drawPolygon(cx, cy, number);
	}
}