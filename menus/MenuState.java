package menus;

public class MenuState {
	protected int x;
	protected int y;
	protected int menuIndex;
	protected boolean visible;

	public MenuState() {
		x = 0;
		y = 0;
		menuIndex = 0;
		visible = false;
	}
	
	public void incrementMenuIndexIfPossible(int numRows) {
		if (menuIndex < (numRows - 1)) {
			menuIndex++;
		}
	}
	
	public void decrementMenuIndexIfPossible() {
		if (menuIndex > 0) {
			menuIndex--;
		}
	}
	
	public void resetMenuIndex() {
		menuIndex = 0;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getMenuIndex() {
		return menuIndex;
	}

	public boolean isVisible() {
		return visible;
	}
}
