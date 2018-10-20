/**
 * @TODO
 *  - possibly add feature to remove units to be built
 *  - define which units can be built in factory?
 */
package map.buildings;

import graphics.images.FactoryImage;

public class Factory extends Building {
	public Factory(int x, int y, int tileSize) {
		super(x, y, tileSize);
		buildingImage = new FactoryImage(tileSize);
	}
}