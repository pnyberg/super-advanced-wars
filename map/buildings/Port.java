/**
 * @TODO
 * - possibly add feature to remove units to be built
 * - define which units can be built in factory?
 */

package map.buildings;

import java.awt.Color;
import java.awt.Graphics;

public class Port extends Building {
	public Port(int x, int y) {
		super(x, y);		
	}

	public void paint(Graphics g, int tileSize) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		if (owner == null) {
			g.setColor(Color.white);
		} else {
			g.setColor(owner.getColor());
		}

		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		int overCircleX = paintX + 3 * tileSize / 8;
		int overCircleY = paintY + tileSize / 10;
		int bodyX = paintX + 7 * tileSize / 16;
		int bodyY = paintY + 7 * tileSize / 20;

		// head and body
		g.drawOval(overCircleX, overCircleY, tileSize / 4, tileSize / 4);
		g.drawRect(bodyX, bodyY, tileSize / 8, tileSize / 3);

		// the bend
		int ox1 = paintX + tileSize / 4;
		int ox2 = paintX + 4 * tileSize / 16;
		int ox3 = bodyX;
		int ox4 = bodyX + tileSize / 8;
		int ox5 = paintX + 12 * tileSize / 16;
		int ox6 = paintX + 3 * tileSize / 4;
		//--
		int ox7 = paintX + 7 * tileSize / 8;
		int ox8 = paintX + 11 * tileSize / 16;
		int ox9 = ox4;
		int ox10 = ox3;
		int ox11 = paintX + 5 * tileSize / 16;
		int ox12 = paintX + tileSize / 8;

		int oy1 = paintY + 11 * tileSize / 20;
		int oy2 = paintY + 7 * tileSize / 12;
		int oy3 = bodyY + tileSize / 3;
		int oy4 = oy3;
		int oy5 = oy2;
		int oy6 = oy1;
		//--
		int oy7 = oy1;
		int oy8 = oy2 + tileSize / 7;
		int oy9 = oy3 + tileSize / 8;
		int oy10 = oy9;
		int oy11 = oy8;
		int oy12 = oy1;

		int[] bendX = {ox1, ox2, ox3, ox4, ox5, ox6, ox7, ox8, ox9, ox10, ox11, ox12};
		int[] bendY = {oy1, oy2, oy3, oy4, oy5, oy6, oy7, oy8, oy9, oy10, oy11, oy12};

		g.drawPolygon(bendX, bendY, 12);
	}
}