package menus.building;

public class BuildingItem {
	private String name;
	private int price;

	public BuildingItem(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}
}