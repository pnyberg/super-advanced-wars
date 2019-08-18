package unitUtils;

public class UnitSupply {
	private int maxFuel;
	private int currentFuel;
	private int maxAmmo;
	private int currentAmmo;
	
	public UnitSupply(int maxFuel, int maxAmmo) {
		this.maxFuel = maxFuel;
		currentFuel = maxFuel;
		this.maxAmmo = maxAmmo;
		currentAmmo = maxAmmo;
	}
	
	public void replentish() {
		currentFuel = maxFuel;
		currentAmmo = maxAmmo;
	}

	public void useAmmo() {
		currentAmmo--;
	}

	public void useFuel(int fuel) {
		currentFuel -= fuel;
	} 

	public boolean hasAmmo() {
		return currentAmmo > 0;
	}

	public boolean hasSufficientFuel(int fuelNeeded) {
		return currentFuel >= fuelNeeded;
	}
	
	public int getAmmo() {
		return currentAmmo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public int getFuel() {
		return currentFuel;
	}

	public int getMaxFuel() {
		return maxFuel;
	}
}