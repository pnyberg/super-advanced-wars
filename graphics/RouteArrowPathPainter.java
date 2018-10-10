package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.MapDim;
import point.Point;

public class RouteArrowPathPainter {
	private MapDim mapDimension;
	
	public RouteArrowPathPainter(MapDim mapDimension) {
		this.mapDimension = mapDimension;
	}

	public void paint(Graphics g, ArrayList<Point> arrowPoints) {
		int tileSize = mapDimension.tileSize;

		if (arrowPoints.size() < 2) {
			return;
		}

		for (int i = 1 ; i < arrowPoints.size() ; i++) {
			int x1 = arrowPoints.get(i - 1).getX() + tileSize / 2;
			int y1 = arrowPoints.get(i - 1).getY() + tileSize / 2;
			int x2 = arrowPoints.get(i).getX() + tileSize / 2;
			int y2 = arrowPoints.get(i).getY() + tileSize / 2;

			g.setColor(Color.red);
			g.drawLine(x1, y1, x2, y2);
		}

		int size = arrowPoints.size();

		int xNext = arrowPoints.get(size - 2).getX();
		int yNext = arrowPoints.get(size - 2).getY();
		int xLast = arrowPoints.get(size - 1).getX();
		int yLast = arrowPoints.get(size - 1).getY();

		if (xNext == xLast) {
			if (yNext < yLast) {
				g.drawLine(xLast - 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			} else {
				g.drawLine(xLast - 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			}
		} else {
			if (xNext < xLast) {
				g.drawLine(xLast - 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast - 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			} else {
				g.drawLine(xLast + 3 + tileSize / 2, yLast - 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
				g.drawLine(xLast + 3 + tileSize / 2, yLast + 3 + tileSize / 2, xLast + tileSize / 2, yLast + tileSize / 2);
			}
		}
	}
}
