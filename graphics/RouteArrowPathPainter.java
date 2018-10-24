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
		int xNext = arrowPoints.get(size - 2).getX();
		int yNext = arrowPoints.get(size - 2).getY();
		int xLast = arrowPoints.get(size - 1).getX();
		int yLast = arrowPoints.get(size - 1).getY();
		int xLastPos = xLast + mapDimension.tileSize / 2;
		int yLastPos = yLast + mapDimension.tileSize / 2;

		if (xNext == xLast) {
			if (yNext < yLast) {
				g.drawLine(xLastPos - 3, yLastPos - 3, xLastPos, yLastPos);
				g.drawLine(xLastPos + 3, yLastPos - 3, xLastPos, yLastPos);
			} else {
				g.drawLine(xLastPos - 3, yLastPos + 3, xLastPos, yLastPos);
				g.drawLine(xLastPos + 3, yLastPos + 3, xLastPos, yLastPos);
			}
		} else {
			if (xNext < xLast) {
				g.drawLine(xLastPos - 3, yLastPos - 3, xLastPos, yLastPos);
				g.drawLine(xLastPos - 3, yLastPos + 3, xLastPos, yLastPos);
			} else {
				g.drawLine(xLastPos + 3, yLastPos - 3, xLastPos, yLastPos);
				g.drawLine(xLastPos + 3, yLastPos + 3, xLastPos, yLastPos);
			}
		}
	}
}
