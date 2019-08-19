package gameObjects;

public class GameProperties {
	public final int fuelMaintenancePerTurn;
	private MapDimension mapDim;
	private ChosenObject chosenObject;
	
	public GameProperties(int fuelMaintenancePerTurn, MapDimension mapDim, ChosenObject chosenObject) {
		this.fuelMaintenancePerTurn = fuelMaintenancePerTurn;
		this.mapDim = mapDim;
		this.chosenObject = chosenObject;
	}
	
	public ChosenObject getChosenObject() {
		return chosenObject;
	}
	
	public MapDimension getMapDim() {
		return mapDim;
	}
}