import java.awt.Color;
import java.awt.Graphics;

public class IndirectUnit extends Unit {
	protected int minimumRange, maximumRange;

	public IndirectUnit(int x, int y, Color color, int minimumRange, int maximumRange) {
		super(x, y, color);

		this.minimumRange = minimumRange;
		this.maximumRange = maximumRange;

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