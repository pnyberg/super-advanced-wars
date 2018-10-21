package units.footMoving;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Infantry extends Unit {
	private static int price = 1000;
	private static String typeName = "Infantry";
	//private BufferedImage redImg;

	public Infantry(int x, int y, Color color, int tileSize) {
		super(UnitType.INFANTRY, x, y, color, tileSize);

		movement = 3;
		movementType = MovementType.INFANTRY;
		unitClass = UnitCategory.FOOTMAN;
		unitSupply = new UnitSupply(99, 0);

		/* try {
		    redImg = ImageIO.read(new File("images/red-infantry.png"));
		} catch (IOException e) {
		}*/
	}

	public static void setPrice(int price) {
		Infantry.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
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

		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}

		// head
		g.fillOval(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);
		g.setColor(Color.black);
		g.drawOval(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		// body
		g.drawLine(point.getX() + bodyAlignX, point.getY() + bodyAlignY, point.getX() + bodyAlignX, point.getY() + bodyEndY);

		// arms
		g.drawLine(point.getX() + leftArmAlign, point.getY() + armAlignY, point.getX() + rightArmEnd, point.getY() + armAlignY);

		// legs
		g.drawLine(point.getX() + leftLegAlign, point.getY() + feetLevel, point.getX() + bodyAlignX, point.getY() + bodyEndY);
		g.drawLine(point.getX() + bodyAlignX, point.getY() + bodyEndY, point.getX() + rightLegEnd, point.getY() + feetLevel);
	}
}