package gameObjects;

public class GameProperties {
	public final int fuelMaintenancePerTurn;
	private DimensionObject mapDim;
	private ChosenObject chosenObject;
	
	public GameProperties(int fuelMaintenancePerTurn, DimensionObject mapDim, ChosenObject chosenObject) {
		this.fuelMaintenancePerTurn = fuelMaintenancePerTurn;
		this.mapDim = mapDim;
		this.chosenObject = chosenObject;
	}
	
	public ChosenObject getChosenObject() {
		return chosenObject;
	}
	
	public DimensionObject getMapDim() {
		return mapDim;
	}
}