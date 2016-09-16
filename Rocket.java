import java.awt.Color;
import java.awt.Graphics;

public class Rocket extends IndirectUnit {
	public Rocket(int x, int y, Color color) {
		super(x, y, color);

		movement = 5;
		movementType = Unit.TIRE;
		minimumRange = 3;
		maximumRange = 5;
	}

	public void paint(Graphics g, int tileSize) {
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
		g.setColor(color);
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		// cannon
		int[] cannonX = {cx1, cx2, cx3, cx4};
		int[] cannonY = {cy1, cy2, cy3, cy4};
		int npoints = 4;

		g.setColor(color);
		g.fillPolygon(cannonX, cannonY, npoints);

		g.setColor(Color.black);
		g.drawPolygon(cannonX, cannonY, npoints);
	}
}