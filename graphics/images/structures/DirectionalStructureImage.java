/**
 * TODO:
 *  - make the DirectionalStructureImage-class an abstract class? 
 */
package graphics.images.structures;

import java.awt.Color;
import java.awt.Graphics;

import gameObjects.Direction;

public class DirectionalStructureImage extends StructureImage {
	private Direction direction;
	private int tileSize;
	
	public DirectionalStructureImage(Direction direction, int tileSize) {
		this.direction = direction;
		this.tileSize = tileSize;
	}

	public void paint(Graphics g, int x, int y, Color cannonColor) {
		g.setColor(cannonColor);
		if (direction == Direction.NORTH) {
			paintNorthMiniCannon(g, x, y, cannonColor);
		} else if (direction == Direction.EAST) {
			paintEastMiniCannon(g, x, y, cannonColor);
		} else if (direction == Direction.SOUTH) {
			paintSouthMiniCannon(g, x, y);
		} else if (direction == Direction.WEST) {
			paintWestMiniCannon(g, x, y, cannonColor);
		}
	}
	
	private void paintNorthMiniCannon(Graphics g, int x, int y, Color cannonColor) {
		g.fillOval(x + 3*tileSize/10, y + tileSize/20, 2 * tileSize / 5, 2 * tileSize / 5);
		g.setColor(Color.black);
		g.drawOval(x + 3*tileSize/10, y + tileSize/20, 2 * tileSize / 5, 2 * tileSize / 5);
		g.setColor(cannonColor);
		g.fillOval(x + tileSize/8, y + tileSize/8, 3 * tileSize / 4, 3 * tileSize / 4);
		g.setColor(Color.black);
		g.drawOval(x + tileSize/8, y + tileSize/8, 3 * tileSize / 4, 3 * tileSize / 4);
	}

	private void paintEastMiniCannon(Graphics g, int x, int y, Color cannonColor) {
		g.fillOval(x + tileSize/2, y + tileSize/5, tileSize / 5, 2 * tileSize / 5);
		g.setColor(Color.black);
		g.drawOval(x + tileSize/2, y + tileSize/5, tileSize / 5, 2 * tileSize / 5);
		g.setColor(cannonColor);
		g.fillOval(x + tileSize/8, y + tileSize/16, tileSize / 2, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(x + tileSize/8, y + tileSize/16, tileSize / 2, 7 * tileSize / 8);
	}

	private void paintSouthMiniCannon(Graphics g, int x, int y) {
		g.fillOval(x + tileSize/8, y + tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(x + tileSize/8, y + tileSize/16, 3 * tileSize / 4, 7 * tileSize / 8);
		g.drawOval(x + 3*tileSize/10, y + tileSize/5, 2 * tileSize / 5, 2 * tileSize / 5);
		g.fillOval(x + 2*tileSize/5, y + 3*tileSize/10, tileSize / 5, tileSize / 5);
	}

	private void paintWestMiniCannon(Graphics g, int x, int y, Color cannonColor) {
		g.fillOval(x + 3*tileSize/10, y + tileSize/5, tileSize / 5, 2 * tileSize / 5);
		g.setColor(Color.black);
		g.drawOval(x + 3*tileSize/10, y + tileSize/5, tileSize / 5, 2 * tileSize / 5);
		g.setColor(cannonColor);
		g.fillOval(x + 3*tileSize/8, y + tileSize/16, tileSize / 2, 7 * tileSize / 8);
		g.setColor(Color.black);
		g.drawOval(x + 3*tileSize/8, y + tileSize/16, tileSize / 2, 7 * tileSize / 8);
	}
}