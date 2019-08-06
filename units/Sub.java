/**
 * TODO: Add flag to show submerged (like APC). 
 * Didn't have the APC-code on laptop when in Denmark, hence no implementation.
 */
package units;

import java.awt.Color;
import java.awt.Graphics;

public class Sub extends Unit {
	private static int price = 20000; // right?
	private static String typeName = "Sub";

	public Sub(int x, int y, Color color) {
		super(x, y, color);

		movement = 5;
		movementType = Unit.SHIP; // right?
		unitClass = Unit.SUB; // right?

		maxFuel = 99; // right?
		maxAmmo = 9; // right?
		replentish();
	}

	public static void setPrice(int price) {
		Sub.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
	
	public void submerge() {
		hidden = true;
	}

	public void emerge() {
		hidden = false;
	}

	public boolean isSubmerged() {
		return isHidden();
	}

	// TODO
	protected void paintUnit(Graphics g, int tileSize) {
		int bodyAlignX = x * tileSize + tileSize / 8;
		int bodyAlignY = y * tileSize + tileSize / 2;
		int bodyWidth = 3 * tileSize / 4;
		int bodyHeight = tileSize / 3;

		int headWidth = tileSize / 4;
		int headHeight = tileSize / 4;
		int headAlignX = (tileSize - headWidth) / 2;
		int headAlignY = 3 * tileSize / 5 - headHeight;

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillOval(bodyAlignX, bodyAlignY, bodyWidth, bodyHeight);
		g.setColor(Color.black);
		g.drawOval(bodyAlignX, bodyAlignY, bodyWidth, bodyHeight);

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(x * tileSize + headAlignX, y * tileSize + headAlignY, headWidth, headHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + headAlignX, y * tileSize + headAlignY, headWidth, headHeight);
	}
}