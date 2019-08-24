/**
 * @TODO
 * - possibly add feature to remove units to be built
 * - define which units can be built in port?
 */

package map.buildings;

import graphics.images.buildings.PortImage;

public class Port extends Building {
	public Port(int x, int y, int tileSize) {
		super(x, y, tileSize);		
		buildingImage = new PortImage(tileSize);
	}

	public boolean isBuildableBuilding() {
		return true;
	}
}