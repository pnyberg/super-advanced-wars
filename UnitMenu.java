import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class UnitMenu {
	private boolean visible, join, enter, fire, capt, launch, dive, emerge, supply, wait;
	private int x, y, menuIndex, numberOfRows, tileSize;

	private ArrayList<Unit> cargo;

	public UnitMenu(int tileSize) {
		x = 0;
		y = 0;
		menuIndex = 0;
		this.tileSize = tileSize;
		visible = false;
		numberOfRows = 0;

		join = false;
		enter = false;
		fire = false;
		capt = false;
		launch = false;
		dive = false;
		emerge = false;
		supply = false;
		wait = false;

		cargo = new ArrayList<Unit>();
	}

	public void openMenu(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}

	public void closeMenu() {
		visible = false;
		menuIndex = 0;		

		join = false;
		enter = false;
		fire = false;
		capt = false;
		launch = false;
		dive = false;
		emerge = false;
		supply = false;
		wait = false;

		cargo.clear();
	}

	public void moveArrowUp() {
		updateNumberOfRows();

		if (menuIndex > 0) {
			menuIndex--;
		}
	}

	public void moveArrowDown() {
		updateNumberOfRows();

		if (menuIndex < (numberOfRows - 1)) {
			menuIndex++;
		}
	}

	public void unitMayJoin() {
		join = true;
	}

	public void unitMayEnter() {
		enter = true;
	}

	public void containedCargo(ArrayList<Unit> containedCargo) {
		for (int i = 0 ; i < containedCargo.size() ; i++) {
			cargo.add(containedCargo.get(i));
		}
	}

	public void unitMayFire() {
		fire = true;
	}

	public void unitMayCapt() {
		capt = true;
	}

	public void unitMayLaunch() {
		launch = true;
	}

	public void unitMayDive() {
		dive = true;
	}

	public void unitMayEmerge() {
		emerge = true;
	}

	public void unitMaySupply() {
		supply = true;
	}

	public void unitMayWait() {
		wait = true;
	}

	private void updateNumberOfRows() {
		numberOfRows = 0;

		if (join) { numberOfRows++; }
		if (capt) { numberOfRows++; }
		if (dive) { numberOfRows++; }
		if (emerge) { numberOfRows++; }
		if (fire) { numberOfRows++; }
		if (launch) { numberOfRows++; }
		if (supply) { numberOfRows++; }
		if (enter) { numberOfRows++; }
		if (wait) { numberOfRows++; }

		if (cargo.size() > 0) {
			numberOfRows += cargo.size();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void paint(Graphics g) {
		int menuRowHeight = 15;
		int menuWidth = (tileSize * 5 / 3);
		int menuHeight = 10 + numberOfRows * menuRowHeight;
		int xAlign = 4;
		int yAlign = 3;

		int arrowWidth = tileSize / 2;

		int menuX = x * tileSize + tileSize / 2;
		int menuY = y * tileSize + tileSize / 2;

		g.setColor(Color.white);
		g.fillRect(menuX, menuY, menuWidth, menuHeight);
		g.setColor(Color.black);
		g.fillRect(menuX, menuY, menuWidth, 2); 
		g.fillRect(menuX, menuY, 2, menuHeight); 
		g.fillRect(menuX + menuWidth - 2, menuY, 2, menuHeight); 
		g.fillRect(menuX, menuY + menuHeight - 2, menuWidth, 2); 

		int rowHelpIndex = 1;

		if (join) {
			g.drawString("Join", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (enter) {
			g.drawString("Enter", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		for (int i = 0 ; i < cargo.size() ; i++) {
			g.drawString("Unit" + (i+1), menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (fire) {
			g.drawString("Fire", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (capt) {
			g.drawString("Capt", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (launch) {
			g.drawString("Launch", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (dive) {
			g.drawString("Dive", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (emerge) {
			g.drawString("Emerge", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (supply) {
			g.drawString("Supply", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		if (wait) {
			g.drawString("Wait", menuX + xAlign, menuY + yAlign + menuRowHeight * rowHelpIndex);
			rowHelpIndex++;
		}

		// printing arrow
		int ax1 = menuX + 1 - arrowWidth;
		int ax2 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax3 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax4 = menuX + 1;
		int ax5 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax6 = menuX + 1 - (2 * arrowWidth) / 5;
		int ax7 = menuX + 1 - arrowWidth;

		int ay1 = menuY + yAlign + 3 + menuRowHeight * menuIndex;
		int ay2 = menuY + yAlign + 3 + menuRowHeight * menuIndex;
		int ay3 = menuY + 1 + menuRowHeight * menuIndex;
		int ay4 = menuY + 2 + yAlign + menuRowHeight / 2 + menuRowHeight * menuIndex;
		int ay5 = menuY + 3 + yAlign * 2 + menuRowHeight * (menuIndex + 1);
		int ay6 = menuY + 1 + yAlign + menuRowHeight * (menuIndex + 1);
		int ay7 = menuY + 1 + yAlign + menuRowHeight * (menuIndex + 1);

		int[] ax = {ax1, ax2, ax3, ax4, ax5, ax6, ax7};
		int[] ay = {ay1, ay2, ay3, ay4, ay5, ay6, ay7};

		g.setColor(Color.orange);
		g.fillPolygon(ax, ay, 7);

		g.setColor(Color.black);
		g.drawPolygon(ax, ay, 7);
	}
}