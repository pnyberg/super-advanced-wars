import java.awt.Color;
import java.awt.Graphics;

// === @TODO ====
// * possibly add feature to remove units to be built
// * define which units can be built in factory?
public class Factory extends Building {
	public Factory(int x, int y) {
		super(x, y);		
	}

	public void paint(Graphics g, int tileSize) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		g.setColor(Color.white);
		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		int firstX = paintX + tileSize / 10;
		int secondX = paintX + 4 * tileSize / 10;
		int thirdX = paintX + 6 * tileSize / 10;
		int fourthX = paintX + 9 * tileSize / 10;

		int overY = paintY + 4 * tileSize / 10;
		int underY = paintY + 7 * tileSize / 10;

		g.drawLine(firstX, overY + 2, secondX, overY - 2);
		g.drawLine(secondX, overY - 2, thirdX, overY + 2);
		g.drawLine(thirdX, overY + 2, fourthX, overY - 2);

		g.drawLine(firstX, underY + 2, secondX, underY - 2);
		g.drawLine(secondX, underY - 2, thirdX, underY + 2);
		g.drawLine(thirdX, underY + 2, fourthX, underY - 2);
	}
}