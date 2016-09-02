import java.awt.Color;
import java.awt.Graphics;

public class Infantry {
	private int tileSize = 20;

	private int x, y;
	private Color color;

	private int movement;
	private int movementType;

	public Infantry(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;

		movement = 3;
		movementType = 1; // infantry
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x * tileSize + 7, y * tileSize + 2, 6, 6);
		g.setColor(Color.black);
		g.drawOval(x * tileSize + 7, y * tileSize + 2, 6, 6);
		g.drawLine(x * tileSize + 10, y * tileSize + 8, x * tileSize + 10, y * tileSize + 16);

		g.drawLine(x * tileSize + 5, y * tileSize + 10, x * tileSize + 15, y * tileSize + 10);
		g.drawLine(x * tileSize + 6, y * tileSize + 19, x * tileSize + 10, y * tileSize + 16);
		g.drawLine(x * tileSize + 10, y * tileSize + 16, x * tileSize + 14, y * tileSize + 19);
	}
}