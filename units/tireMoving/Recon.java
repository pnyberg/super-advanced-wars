package units.tireMoving;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;

public class Recon extends Unit {
	private static int price = 4000;
	private static String typeName = "Recon";
	//private BufferedImage redImg;

	public Recon(int x, int y, Color color, int tileSize) {
		super(x, y, color, tileSize);

		movement = 8;
		movementType = MovementType.TIRE;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(80, 0);

		/* try {
		    redImg = ImageIO.read(new File("images/red-recon.png"));
		} catch (IOException e) {
		}*/
	}

	public static void setPrice(int price) {
		Recon.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}

	protected void paintUnit(Graphics g, int tileSize) {
		/*if (color == Color.red) {
			g.drawImage(redImg, point.getX(), point.getY(), null);
			return;
		}*/
		int headSize = tileSize / 4 + 2;
		int headAlignX = 2 * tileSize / 5;
		int headAlignY = tileSize / 10 + 1;

		int cannonWidth = headSize / 4;
		int cannonHeight = headSize / 2;
		int cannonAlignX = headAlignX + headSize;
		int cannonAlignY = headAlignY + headSize / 4;

		int bodyWidth = 2 * tileSize / 5 + 1;
		int bodyHeight = tileSize / 4 + 3;
		int bodyAlignX = tileSize / 3 - 1;
		int bodyAlignY = headSize + headAlignY;

		// head
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		g.setColor(Color.black);
		g.drawRect(point.getX() + headAlignX, point.getY() + headAlignY, headSize, headSize);

		// cannon
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + cannonAlignX, point.getY() + cannonAlignY, cannonWidth, cannonHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + cannonAlignX, point.getY() + cannonAlignY, cannonWidth, cannonHeight);

		// body
		if (active) {
			g.setColor(color);
		} else {
			g.setColor(restingColor);
		}
		g.fillRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);

		g.setColor(Color.black);
		g.drawRect(point.getX() + bodyAlignX, point.getY() + bodyAlignY, bodyWidth, bodyHeight);
	}
}