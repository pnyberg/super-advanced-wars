package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.Direction;
import gameObjects.MapDimension;
import point.Point;

public class RouteArrowPathPainter {
	private MapDimension mapDimension;
	
	public RouteArrowPathPainter(MapDimension mapDimension) {
		this.mapDimension = mapDimension;
	}

	public void paint(Graphics g, ArrayList<Point> arrowPoints) {
		if (arrowPoints.size() < 2) {
			return;
		}
		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x1 = arrowPoints.get(i - 1).getX() + mapDimension.tileSize / 2;
			int y1 = arrowPoints.get(i - 1).getY() + mapDimension.tileSize / 2;
			int x2 = arrowPoints.get(i).getX() + mapDimension.tileSize / 2;
			int y2 = arrowPoints.get(i).getY() + mapDimension.tileSize / 2;

			g.setColor(Color.red);
			g.drawLine(x1, y1, x2, y2);
		}

		int size = arrowPoints.size();
		int xLast = arrowPoints.get(size - 1).getX();
		int yLast = arrowPoints.get(size - 1).getY();
		int xLastPos = xLast + mapDimension.tileSize / 2;
		int yLastPos = yLast + mapDimension.tileSize / 2;
		int xNext = arrowPoints.get(size - 2).getX();
		int yNext = arrowPoints.get(size - 2).getY();
		if (xNext == xLast && yNext > yLast) {
			paintArrowHead(g, xLastPos, yLastPos, Direction.NORTH);
		} else if (xNext < xLast && yNext == yLast) {
			paintArrowHead(g, xLastPos, yLastPos, Direction.EAST);
		} else if (xNext == xLast && yNext < yLast) {
			paintArrowHead(g, xLastPos, yLastPos, Direction.SOUTH);
		} else if (xNext > xLast && yNext == yLast) {
			paintArrowHead(g, xLastPos, yLastPos, Direction.WEST);
		}
	}
	
	private void paintArrowHead(Graphics g, int x, int y, Direction direction) {
		if (direction == Direction.SOUTH) {
			g.drawLine(x - 3, y - 3, x, y);
			g.drawLine(x + 3, y - 3, x, y);
		} else if (direction == Direction.NORTH) {
			g.drawLine(x - 3, y + 3, x, y);
			g.drawLine(x + 3, y + 3, x, y);
		} else if (direction == Direction.EAST) {
			g.drawLine(x - 3, y - 3, x, y);
			g.drawLine(x - 3, y + 3, x, y);
		} else if (direction == Direction.WEST) {
			g.drawLine(x + 3, y - 3, x, y);
			g.drawLine(x + 3, y + 3, x, y);
		}
	}
}
