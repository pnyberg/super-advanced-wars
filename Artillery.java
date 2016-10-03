import java.awt.Color;
import java.awt.Graphics;

public class Artillery extends IndirectUnit {
	public Artillery(int x, int y, Color color) {
		super(x, y, color);

		movement = 5;
		movementType = Unit.BAND;
		minimumRange = 2;
		maximumRange = 3;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		int cx1 = x * tileSize + 3 * tileSize / 4;
		int cy1 = y * tileSize + tileSize / 8;

		int cx2 = x * tileSize + 7 * tileSize / 8;
		int cy2 = y * tileSize + tileSize / 4;

		int cx3 = x * tileSize + 3 * tileSize / 4 - 3;
		int cy3 = y * tileSize + 7 * tileSize / 20 + 3;
		int cx4 = x * tileSize + tileSize / 4 + 5;
		int cy4 = y * tileSize + 7 * tileSize / 20 + 3;

		int bodyWidth = 2 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 3 - 1;
		int bodyAlignY = 7 * tileSize / 20 + 3;

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(color);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);

		// body
		g.setColor(color);
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);
	}
}