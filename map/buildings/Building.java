package map.buildings;

import java.awt.Color;
import java.awt.Graphics;

import graphics.images.buildings.BuildingImage;
import hero.*;

public abstract class Building {
	private static int income;
	private static int maxCaptingValue = 20;
	protected Hero owner;
	protected int x;
	protected int y;
	protected int tileSize;
	protected int captingValue;
	protected BuildingImage buildingImage;

	// static since all buildings should generate the same amount of cash
	public static void init(int income) {
		Building.income = income;
	}

	public Building(int x, int y, int tileSize) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		this.captingValue = maxCaptingValue;
	}

	public void setOwnership(Hero hero) {
		owner = hero;
		resetCapting();
	}

	public void capt(int captingValue) {
		this.captingValue -= captingValue;
		if (this.captingValue < 0) {
			this.captingValue = 0;
		}
	}
	
	public void resetCapting() {
		captingValue = maxCaptingValue;
	}
	
	public abstract boolean isBuildableBuilding();

	public boolean captingIsActive() {
		return 0 < captingValue && captingValue < maxCaptingValue;
	}

	public boolean isCapted() {
		return captingValue == 0;
	}

	public static int getIncome() {
		return income;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getCaptingValue() {
		return captingValue;
	}
	
	public Hero getOwner() {
		return owner;
	}
	
	public BuildingImage getBuildingImage() {
		return buildingImage;
	}
	
	public void paint(Graphics g) {
		Color buildingColor = null;
		if (owner == null) {
			buildingColor = Color.white;
		} else {
			buildingColor = owner.getColor();
		}
		buildingImage.paint(g, x, y, buildingColor);
	}
}