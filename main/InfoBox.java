package main;

import java.awt.Color;
import java.awt.Graphics;

public class InfoBox {
	private int x;
	private int y;
	private int width;
	private int height;
	private int tileSize;
	
	public InfoBox(int x, int y, int width, int height, int tileSize) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
	}
}