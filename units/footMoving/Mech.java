package units.footMoving;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.images.units.InfantryImage;
import graphics.images.units.MechImage;
import units.MovementType;
import units.Unit;
import units.UnitCategory;
import units.UnitSupply;
import units.UnitType;

public class Mech extends Unit {
	private static int price = 3000;
	private static String typeName = "Mech";

	public Mech(int x, int y, Color color, int tileSize) {
		super(UnitType.MECH, x, y, color, tileSize);

		movement = 2;
		movementType = MovementType.MECH;
		unitClass = UnitCategory.FOOTMAN;
		unitSupply = new UnitSupply(70, 3);
		
		unitImage = new MechImage(tileSize);
	}

	public static void setPrice(int price) {
		Mech.price = price;
	}

	public static int getPrice() {
		return price;
	}

	public static String getTypeName() {
		return typeName;
	}
}