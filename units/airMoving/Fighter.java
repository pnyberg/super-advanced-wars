package units.airMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.Unit;

public class Fighter extends Unit {
	private static int price = 20000;
	private static String typeName = "Fighter";

	public Fighter(int x, int y, Color color) {
		super(x, y, color);

		movement = 9;
		movementType = Unit.AIR;
		unitClass = Unit.PLANE;

		maxFuel = 99;
		maxAmmo = 9;
		replentish();
	}

	public static void setPrice(int price) {
		Fighter.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int bodyWidth = 2 * tileSize / 5 + 5;
		int bodyHeight = tileSize / 5;
		int bodyAlignX = tileSize / 4 - 1;
		int bodyAlignY = 2 * tileSize / 5 + 3;

		int headSize = tileSize / 4;
		int headAlignX = 3 * tileSize / 5 + 2;
		int headAlignY = bodyAlignY - 2;

		int x1 = x * tileSize + tileSize / 3 - 2;
		int x2 = x * tileSize + 2 * tileSize / 5 - 2;
		int x3 = x * tileSize + 3 * tileSize / 5 - 2;
		int x4 = x * tileSize + 2 * tileSize / 3 - 2;
		int y1 = y * tileSize + 3 * tileSize / 5;
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
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(x * tileSize + headAlignX, y * tileSize + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawOval(x * tileSize + headAlignX, y * tileSize + headAlignY, headSize, headSize);

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