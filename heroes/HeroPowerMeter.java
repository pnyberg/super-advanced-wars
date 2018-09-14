package heroes;

public class HeroPowerMeter {
	private double starPower;
	private int powerAmount, superPowerAmount;

	public HeroPowerMeter(int powerAmount, int superPowerAmount) {
		starPower = 0;
		this.powerAmount = powerAmount;
		this.superPowerAmount  = superPowerAmount;
	}

	public double getStarPower() {
		return starPower;
	}

	public int getRequiredPower() {
		return powerAmount;
	}

	public int getRequiredSuperPower() {
		return superPowerAmount;
	}
	
	public boolean powerUsable() {
		return starPower >= powerAmount;
	}

	public boolean superPowerUsable() {
		return starPower >= superPowerAmount;
	}
}
