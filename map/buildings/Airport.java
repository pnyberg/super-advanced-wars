/**
 * @TODO
 *  - possibly add feature to remove units to be built
 *  - define which units can be built in airport?
 */
package map.buildings;

import graphics.images.buildings.AirportImage;

public class Airport extends Building {
	public Airport(int x, int y, int tileSize) {
		super(x, y, tileSize);
		buildingImage = new AirportImage(tileSize);
	}
}