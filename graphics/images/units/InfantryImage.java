package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class InfantryImage extends UnitImage {
	private int tileSize;
	
	public InfantryImage(int tileSize) {
		this.tileSize = tileSize;

		/* try {
	    redImg = ImageIO.read(new File("images/red-infantry.png"));
		} catch (IOException e) {
		}*/
	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		/* if (color == Color.red) {
			g.drawImage(redImg, point.getX(), point.getY(), null);
			return;
		}*/
		int headSize = tileSize / 2 - 4;
		int headAlignX = tileSize / 4 + 2;
		int headAlignY = tileSize / 10;
		int bodyAlignX = tileSize / 2;
		int bodyAlignY = headSize + headAlignY;
		int bodyEndY = 3 * tileSize / 4;
		int feetLevel = tileSize - 2;
		int armAlignY = bodyAlignY + 2;
		int leftArmAlign = tileSize / 4;
		int rightArmEnd = 3 * tileSize / 4;
		int leftLegAlign = tileSize / 4 + 1;
		int rightLegEnd = 3 * tileSize / 4 - 1;

		g.setColor(unitColor);

		// head
		g.fillOval(x + headAlignX, y + headAlignY, headSize, headSize);
		g.setColor(Color.black);
		g.drawOval(x + headAlignX, y + headAlignY, headSize, headSize);

		// body
		g.drawLine(x + bodyAlignX, y + bodyAlignY, x + bodyAlignX, y + bodyEndY);

		// arms
		g.drawLine(x + leftArmAlign, y + armAlignY, x + rightArmEnd, y + armAlignY);

		// legs
		g.drawLine(x + leftLegAlign, y + feetLevel, x + bodyAlignX, y + bodyEndY);
		g.drawLine(x + bodyAlignX, y + bodyEndY, x + rightLegEnd, y + feetLevel);
	}
}