import java.awt.Color;
import java.awt.Graphics;

public class APC extends Unit {
	public APC(int x, int y, Color color) {
		super(x, y, color);

		movement = 6;
		movementType = Unit.BAND;
		attackType = Unit.NONE;
	}

	public void paint(Graphics g, int tileSize) {
		int bodyWidth = 3 * tileSize / 5 + 1;
		int bodyHeight = 2 * tileSize / 4 - 3;
		int bodyAlignX = tileSize / 4 - 1;
		int bodyAlignY = tileSize / 5 + 4;

		// body
		g.setColor(color);
		g.fillRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(x * tileSize + bodyAlignX, y * tileSize + bodyAlignY, bodyWidth, bodyHeight);
	}
}