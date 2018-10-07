package gameObjects;

public class GameProp {
	public final int fuelMaintenancePerTurn;
	private MapDim mapDim;
	private ChosenObject chosenObject;
	
	public GameProp(int fuelMaintenancePerTurn, MapDim mapDim, ChosenObject chosenObject) {
		this.fuelMaintenancePerTurn = fuelMaintenancePerTurn;
		this.mapDim = mapDim;
		this.chosenObject = chosenObject;
	}
	
	public ChosenObject getChosenObject() {
		return chosenObject;
	}
	
	public MapDim getMapDim() {
		return mapDim;
	}
}