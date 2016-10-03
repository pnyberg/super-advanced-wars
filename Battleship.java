import java.awt.Color;
import java.awt.Graphics;

public class Battleship extends IndirectUnit {
	public Battleship(int x, int y, Color color) {
		super(x, y, color);

		movement = 5;
		movementType = Unit.SHIP;
		minimumRange = 2;
		maximumRange = 6;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int cx1 = x * tileSize + tileSize / 8;
		int cy1 = y * tileSize + 3 * tileSize / 5;
		int cx2 = x * tileSize + 7 * tileSize / 8;
		int cy2 = y * tileSize + 3 * tileSize / 5;
		int cx3 = x * tileSize + 3 * tileSize / 4;
		int cy3 = y * tileSize + 5 * tileSize / 6;
		int cx4 = x * tileSize + tileSize / 4;
		int cy4 = y * tileSize + 5 * tileSize / 6;

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

		g.setColor(color);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);

		// head
		g.setColor(color);
		g.fillRect(x * tileSize + headAlignX, y * tileSize + headAlignY, headWidth, headHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + headAlignX, y * tileSize + headAlignY, headWidth, headHeight);

		// cannon
		g.setColor(color);
		g.fillRect(x * tileSize + cannonAlignX, y * tileSize + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + cannonAlignX, y * tileSize + cannonAlignY, cannonWidth, cannonHeight);
	}
}