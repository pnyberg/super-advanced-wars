package menus.unit;

public class UnitMenuRowEntryBooleanHandler {
	private boolean join; 
	private boolean enter; 
	private boolean fire; 
	private boolean capt; 
	private boolean launch; 
	private boolean dive;
	private boolean emerge; 
	private boolean supply; 
	private boolean wait;

	public UnitMenuRowEntryBooleanHandler() {
		clear();
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
		return
			  (join ? 1 : 0)
			+ (enter ? 1 : 0)
			+ (fire ? 1 : 0)
			+ (capt ? 1 : 0)
			+ (launch ? 1 : 0)
			+ (dive ? 1 : 0)
			+ (emerge ? 1 : 0)
			+ (supply ? 1 : 0)
			+ (wait ? 1 : 0);
	}
	
	/***
	 * Mutators 
	 ***/
	public void allowJoin() {
		join = true;
	}
	
	public void allowEnter() {
		enter = true;
	}
	
	public void allowFire() {
		fire = true;
	}
	
	public void allowCapt() {
		capt = true;
	}
	
	public void allowLaunch() {
		launch = true;
	}
	
	public void allowDive() {
		dive = true;
	}
	
	public void allowEmerge() {
		emerge = true;
	}
	
	public void allowSupply() {
		supply = true;
	}
	
	public void allowWait() {
		wait = true;
	}
	
	/***
	 * Getters
	 ***/
	public boolean mayJoin() {
		return join;
	}
	
	public boolean mayEnter() {
		return enter;
	}
	
	public boolean mayFire() {
		return fire;
	}
	
	public boolean mayCapt() {
		return capt;
	}
	
	public boolean mayLaunch() {
		return launch;
	}
	
	public boolean mayDive() {
		return dive;
	}
	
	public boolean mayEmerge() {
		return emerge;
	}
	
	public boolean maySupply() {
		return supply;
	}
	
	public boolean mayWait() {
		return wait;
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