package graphics.images.buildings;

import java.awt.Color;
import java.awt.Graphics;

public abstract class BuildingImage {
	public abstract void paint(Graphics g, int x, int y, Color buildingColor);
}
