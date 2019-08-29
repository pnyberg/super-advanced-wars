package units.tireMoving;

import java.awt.Color;

import graphics.images.units.ReconImage;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitSupply;
import unitUtils.UnitType;
import units.Unit;

public class Recon extends Unit {
	private static int price = 4000;
	private static String typeName = "Recon";
	//private BufferedImage redImg;

	public Recon(int x, int y, Color color, int tileSize) {
		super(UnitType.RECON, x, y, color, tileSize);

		movementSteps = 8;
		movementType = MovementType.TIRE;
		unitClass = UnitCategory.VEHICLE;
		unitSupply = new UnitSupply(80, 0);

		/* try {
		    redImg = ImageIO.read(new File("images/red-recon.png"));
		} catch (IOException e) {
		}*/
		
		unitImage = new ReconImage(tileSize);
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
}