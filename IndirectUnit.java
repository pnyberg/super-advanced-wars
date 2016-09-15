import java.awt.Color;
import java.awt.Graphics;

public abstract class IndirectUnit extends Unit {
	protected int minimumRange, maximumRange;

	public IndirectUnit(int x, int y, Color color) {
		super(x, y, color);

		attackType = Unit.INDIRECT_ATTACK;
	}

	public int getMinRange() {
		return minimumRange;
	}

	public int getMaxRange() {
		return maximumRange;
	}

	public abstract void paint(Graphics g, int tileSize);
}