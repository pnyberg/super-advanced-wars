import java.awt.Color;
import java.awt.Graphics;

public class Tank extends Unit {
	public Tank(int x, int y, Color color) {
		super(x, y, color);

		movement = 6;
		movementType = Unit.BAND;
	}

	public void paint(Graphics g, int tileSize) {
		int headSize = tileSize / 4 + 2;
		int headAlignX = 3 * tileSize / 5 - 2;
		int headAlignY = tileSize / 10 + 1;

		int cannonWidth = headSize / 4;
		int cannonHeight = headSize / 2;
		int cannonAlignX = headAlignX + headSize;
		int cannonAlignY = headAlignY + headSize / 4;

		int bodyWidth = 3 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 4 - 1;
		int bodyAlignY = headSize + headAlignY;

		// head
		g.setColor(color);
		g.fillRect(x * tileSize + headAlignX, y * tileSize + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + headAlignX, y * tileSize + headAlignY, headSize, headSize);

		// cannon
		g.setColor(color);
		g.fillRect(x * tileSize + cannonAlignX, y * tileSize + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + cannonAlignX, y * tileSize + cannonAlignY, cannonWidth, cannonHeight);

		// body
		g.setColor(color);
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);
	}
}