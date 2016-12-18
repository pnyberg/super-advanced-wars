package graphics;

import java.awt.Color;
import java.awt.Graphics;

public class PowerStar {
	public static final int smallSize = 16;
	public static final int bigSize = 24;

	public static void paintNormal(Graphics g, int x, int y, double filled) {
		paint(g, x, y, smallSize, filled);
	}

	public static void paintSuper(Graphics g, int x, int y, double filled) {
		paint(g, x, y, bigSize, filled);
	}

	private static void paint(Graphics g, int x, int y, int size, double filled) {
		int width = size;
		int height = size;

		// top point
		int x1 = x + width / 2;
		int y1 = y;
		// right upper creek
		int x2 = x + 12 * width / 20;
		int y2 = y + 7 * height / 15;
		// right upper point
		int x3 = x + width;
		int y3 = y + 2 * height / 5;
		// right lower creek
		int x4 = x + 13 * width / 20;
		int y4 = y + 13 * height / 20;
		// right lower point
		int x5 = x + 14 * width / 20;
		int y5 = y + height;
		// lower creek
		int x6 = x + width / 2 - 1;
		int y6 = y + 16 * height / 20;
		// left lower point
		int x7 = x + 4 * width / 15 - 1;
		int y7 = y + height;
		// left lower creek
		int x8 = x + 5 * width / 15;
		int y8 = y + 13 * height / 20;
		// left upper point
		int x9 = x;
		int y9 = y + 2 * height / 5;
		// left upper creek
		int x10 = x + 6 * width / 15;
		int y10 = y + 7 * height / 15;
		
		int n = 10;
		int[] cx = {x1, x2, x3, x4, x5, x6, x7, x8, x9, x10};
		int[] cy = {y1, y2, y3, y4, y5, y6, y7, y8, y9, y10};

		g.setColor(Color.white);
		g.fillPolygon(cx, cy, n);

		g.setColor(Color.orange);
		if (filled == 1) {
			g.fillPolygon(cx, cy, n);
		} else if (filled >= 0.75) {
			// right upper limit
			int xd3 = x4+(x3-x4)/3;
			int yd3 = y3+2*(y4-y3)/3;

			// left upper limit
			int xd9 = x9+2*(x8-x9)/3;
			int yd9 = y9+2*(y8-y9)/3;

			int[] dx = {xd3, x4, x5, x6, x7, x8, xd9};
			int[] dy = {yd3, y4, y5, y6, y7, y8, yd9};
			g.fillPolygon(dx, dy, 7);
		} else if (filled >= 0.5) {
			// right upper limit
			int xd4 = x4+(x5-x4)/10;
			int yd4 = y4+(y5-y4)/10;

			// left upper limit
			int xd8 = x7+9*(x8-x7)/10;
			int yd8 = y7+9*(y8-y7)/10;

			int[] dx = {xd4, x5, x6, x7, xd8};
			int[] dy = {yd4, y5, y6, y7, yd8};
			g.fillPolygon(dx, dy, 5);
		} else if (filled >= 0.25) {
			// right point right upper limit
			int xd4 = x4+4*(x5-x4)/10;
			int yd4 = y4+4*(y5-y4)/10;

			// right point left upper limit

			// left point left upper limit
			int xd8 = x7+6*(x8-x7)/10;
			int yd8 = y7+6*(y8-y7)/10;

			// left point right upper limit

			int[] dx = {xd4, x5, x6, x7, xd8, x6};
			int[] dy = {yd4, y5, y6, y7, yd8, y6};
			g.fillPolygon(dx, dy, 5);
		}
		
		g.setColor(Color.black);
		g.drawPolygon(cx, cy, n);
	}
}
