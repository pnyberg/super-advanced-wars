package map.buildings;

import graphics.images.buildings.CityImage;

public class City extends Building {
	public City(int x, int y, int tileSize) {
		super(x, y, tileSize);
		buildingImage = new CityImage(tileSize);
	}
}