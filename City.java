import java.awt.Color;
import java.awt.Graphics;

public class City extends Building {
	public void paint(Graphics g) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		g.setColor(Color.white);
		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		g.drawLine(paintX, paintY, paintX + tileSize, paintY + tileSize);
		g.drawLine(paintX, paintY + tileSize, paintX + tileSize, paintY);
	}
}