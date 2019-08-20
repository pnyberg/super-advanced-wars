package gameObjects;

public class GameProperties {
	public final int fuelMaintenancePerTurn = 5;
	public final int initialMoneyPerBuilding = 1000;

	private MapDimension mapDimension;
	private ChosenObject chosenObject;
	
	public GameProperties(MapDimension mapDimension, ChosenObject chosenObject) {
		this.mapDimension = mapDimension;
		this.chosenObject = chosenObject;
	}
	
	public ChosenObject getChosenObject() {
		return chosenObject;
	}
	
	public int getTileSize() {
		return mapDimension.tileSize;
	}
	
	// TODO: remove when possible
	public MapDimension getMapDim() {
		return mapDimension;
	}
}