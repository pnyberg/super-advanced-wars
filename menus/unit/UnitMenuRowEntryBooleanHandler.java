package menus.unit;

public class UnitMenuRowEntryBooleanHandler {
	public boolean join; 
	public boolean enter; 
	public boolean fire; 
	public boolean capt; 
	public boolean launch; 
	public boolean dive;
	public boolean emerge; 
	public boolean supply; 
	public boolean wait;

	public UnitMenuRowEntryBooleanHandler() {
		// all booleans are false initially
	}
	
	public void clear() {
		join = false;
		enter = false;
		fire = false;
		capt = false;
		launch = false;
		dive = false;
		emerge = false;
		supply = false;
		wait = false;
	}
	
	public int getNumberOfExistingRows() {
		int num = 0;
		for (boolean menuRowShowing : getAsBooleanArray()) {
			if (menuRowShowing) {
				num++;
			}
		}
		return num;
	}
	
	public boolean[] getAsBooleanArray() {
		return new boolean[] {
			join,
			enter,
			fire,
			capt,
			launch,
			dive,
			emerge,
			supply,
			wait
		};
	}
}