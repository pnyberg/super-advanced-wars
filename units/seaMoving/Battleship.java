package units.seaMoving;

import java.awt.Color;
import java.awt.Graphics;

import units.IndirectUnit;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Battleship extends IndirectUnit {
	private static int price = 28000;
	private static String typeName = "Battleship";

	public Battleship(int x, int y, Color color) {
		super(x, y, color);

		movement = 5;
		movementType = MovementType.SHIP;
		unitClass = UnitCategory.BOAT;
		minimumRange = 2;
		maximumRange = 6;
		unitSupply = new UnitSupply(99, 9);
	}

	public static void setPrice(int price) {
		Battleship.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int cx1 = point.getX() * tileSize + tileSize / 8;
		int cy1 = point.getY() * tileSize + 3 * tileSize / 5;
		int cx2 = point.getX() * tileSize + 7 * tileSize / 8;
		int cy2 = point.getY() * tileSize + 3 * tileSize / 5;
		int cx3 = point.getX() * tileSize + 3 * tileSize / 4;
		int cy3 = point.getY() * tileSize + 5 * tileSize / 6;
		int cx4 = point.getX() * tileSize + tileSize / 4;
		int cy4 = point.getY() * tileSize + 5 * tileSize / 6;

		int headWidth = tileSize / 4;
		int headHeight = tileSize / 4;
		int headAlignX = (tileSize - headWidth) / 2;
		int headAlignY = 3 * tileSize / 5 - headHeight;

		int cannonWidth = headWidth / 4;
		int cannonHeight = headHeight / 2;
		int cannonAlignX = headAlignX + headWidth;
		int cannonAlignY = headAlignY + headHeight / 4;

		// body
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

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() * tileSize + headAlignX, point.getY() * tileSize + headAlignY, headWidth, headHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() * tileSize + headAlignX, point.getY() * tileSize + headAlignY, headWidth, headHeight);

		// cannon
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() * tileSize + cannonAlignX, point.getY() * tileSize + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() * tileSize + cannonAlignX, point.getY() * tileSize + cannonAlignY, cannonWidth, cannonHeight);
	}
}