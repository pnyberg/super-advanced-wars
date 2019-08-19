package point;

public class Point {
	private int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isSamePosition(Point p) {
		return isSamePosition(p.getX(), p.getY());
	}

	public boolean isSamePosition(int x, int y) {
		return this.x == x && this.y == y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}