package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public class MechImage extends UnitImage {
	private int tileSize;

	public MechImage(int tileSize) {
		this.tileSize = tileSize;
		//private BufferedImage redImg;
		/* try {
		    redImg = ImageIO.read(new File("images/red-mech.png"));
		} catch (IOException e) {
		}*/

	}

	public void paint(Graphics g, int x, int y, Color unitColor) {
		/*if (color == Color.red) {
			g.drawImage(redImg, point.getX(), point.getY(), null);
			return;
		}*/
		int rocketAlignX = tileSize / 20 * 3;
		int rocketAlignY = tileSize / 20 * 4;
		int rocketWidth = tileSize / 4 * 3;
		int rocketHeight = tileSize / 10 * 3;
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

		// rocket
		g.setColor(Color.gray);
		g.fillRect(x + rocketAlignX, y + rocketAlignY, rocketWidth, rocketHeight);

		g.setColor(Color.black);
		g.drawRect(x + rocketAlignX, y + rocketAlignY, rocketWidth, rocketHeight);

		// head
		g.setColor(unitColor);
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