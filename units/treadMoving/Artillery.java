package units.treadMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.IndirectUnit;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Artillery extends IndirectUnit {
	private static int price = 6000;
	private static String typeName = "Artillery";

	public Artillery(int x, int y, Color color, int tileSize) {
		super(x, y, color, tileSize);

		movement = 5;
		movementType = MovementType.BAND;
		unitClass = UnitCategory.VEHICLE;
		minimumRange = 2;
		maximumRange = 3;
		unitSupply = new UnitSupply(50, 9);
	}

	public static void setPrice(int price) {
		Artillery.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int cx1 = point.getX() + 3 * tileSize / 4;
		int cy1 = point.getY() + tileSize / 8;

		int cx2 = point.getX() + 7 * tileSize / 8;
		int cy2 = point.getY() + tileSize / 4;

		int cx3 = point.getX() + 3 * tileSize / 4 - 3;
		int cy3 = point.getY() + 7 * tileSize / 20 + 3;
		int cx4 = point.getX() + tileSize / 4 + 5;
		int cy4 = point.getY() + 7 * tileSize / 20 + 3;

		int bodyWidth = 2 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 3 - 1;
		int bodyAlignY = 7 * tileSize / 20 + 3;

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

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);
	}
}