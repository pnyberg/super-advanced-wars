package graphics.images.units;

import java.awt.Color;
import java.awt.Graphics;

public abstract class UnitImage {
	public abstract void paint(Graphics g, int x, int y, Color unitColor);
}