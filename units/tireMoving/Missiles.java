package units.tireMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.IndirectUnit;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Missiles extends IndirectUnit {
	private static int price = 12000;
	private static String typeName = "Missiles";

	public Missiles(int x, int y, Color color, int tileSize) {
		super(UnitType.MISSILES, x, y, color, tileSize);

		movement = 4;
		movementType = MovementType.TIRE;
		unitClass = UnitCategory.VEHICLE;
		minimumRange = 3;
		maximumRange = 5;
		unitSupply = new UnitSupply(50, 6);
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
		int cx1 = point.getX() + 2 * tileSize / 3 + 1;
		int cy1 = point.getY() + tileSize / 10 - 1;
		int cx2 = point.getX() + 8 * tileSize / 10;
		int cy2 = point.getY() + tileSize / 4 + 4;
		int cx3 = point.getX() + tileSize / 4 - 2;
		int cy3 = point.getY() + tileSize / 2 + 5;
		int cx4 = point.getX() + tileSize / 5 - 4;
		int cy4 = point.getY() + 2 * tileSize / 5 - 2;

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
		g.fillRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);

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