package area.buildings;

import java.awt.Color;
import java.awt.Graphics;

// === @TODO ====
// * possibly add feature to remove units to be built
// * define which units can be built in factory?
public class Airport extends Building {
	public Airport(int x, int y) {
		super(x, y);		
	}

	public void paint(Graphics g, int tileSize) {
		int paintX = x * tileSize;
		int paintY = y * tileSize;

		if (owner == null) {
			g.setColor(Color.white);
		} else {
			g.setColor(owner.getColor());
		}
		g.fillRect(paintX, paintY, tileSize, tileSize);
		g.setColor(Color.black);
		g.drawRect(paintX, paintY, tileSize, tileSize);

		int baseX = paintX + tileSize / 4;
		int baseY = paintY + tileSize / 4;
		int baseDiameter = tileSize / 2;

		int towerX = paintX + tileSize / 8;
		int towerY = paintY + tileSize / 8;
		int towerHeight = tileSize / 2;
		int towerWidth = tileSize / 8;

		g.drawOval(baseX, baseY, baseDiameter, baseDiameter);
		g.drawRect(towerX, towerY, towerWidth, towerHeight);
	}
}