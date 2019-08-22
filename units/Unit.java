package units;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import graphics.images.units.UnitImage;
import point.Point;
import unitUtils.AttackType;
import unitUtils.MovementType;
import unitUtils.UnitCategory;
import unitUtils.UnitContainer;
import unitUtils.UnitHealth;
import unitUtils.UnitSupply;
import unitUtils.UnitType;

public abstract class Unit {
	protected static int price;
	protected static String typeName;

	protected Point position;
	protected UnitHealth unitHealth;
	protected boolean hidden;
	protected boolean attacking;
	protected boolean active;
	protected boolean capting;
	protected UnitSupply unitSupply;
	protected UnitContainer unitContainer;
	protected UnitType unitType;
	protected UnitCategory unitClass;

	protected int movement;
	protected MovementType movementType;

	protected int firingIndex;
	protected ArrayList<Point> possibleFiringLocationList;
	protected AttackType attackType;
	
	protected Color color;
	protected Color restingColor;
	protected UnitImage unitImage;

	public Unit(UnitType unitType, int x, int y, Color color, int tileSize) {
		this.unitType = unitType;
		position = new Point(x, y);
		this.color = color;
		restingColor = color.darker();

		unitHealth = new UnitHealth(tileSize);
		hidden = false;
		attacking = false;
		active = false;
		capting = false;

		firingIndex = -1;
		possibleFiringLocationList = new ArrayList<Point>();
		attackType = AttackType.DIRECT_ATTACK;
	}

	public void moveTo(int x, int y) {
		if (position.getX() != x || position.getY() != y) {
			capting = false;
		}
		position = new Point(x, y);
	}

	public void regulateActive(boolean active) {
		this.active = active;
	}

	public void regulateHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void regulateAttack(boolean attacking) {
		this.attacking = attacking;
	}

	public void regulateCapting(boolean capting) {
		this.capting = capting;
	}

	public void addFiringLocation(Point p) {
		possibleFiringLocationList.add(p);
	}

	public void clearFiringLocations() {
		possibleFiringLocationList.clear();
	}

	public Point getNextFiringLocation() {
		if (possibleFiringLocationList.isEmpty()) {
			return null;
		}
		firingIndex = (firingIndex + 1) % possibleFiringLocationList.size();
		return possibleFiringLocationList.get(firingIndex);
	}

	public Point getPreviousFiringLocation() {
		if (possibleFiringLocationList.isEmpty()) {
			return null;
		}
		firingIndex = (firingIndex + possibleFiringLocationList.size() - 1) % possibleFiringLocationList.size();
		return possibleFiringLocationList.get(firingIndex);
	}

	public Point getPosition() {
		return position;
	}

	public UnitHealth getUnitHealth() {
		return unitHealth;
	}
	
	public boolean isActive() {
		return active;
	}

	public boolean isHidden() {
		return hidden;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public boolean isCapting() {
		return capting;
	}
	
	public boolean hasUnitContainer() {
		return unitContainer != null;
	}

	public UnitSupply getUnitSupply() {
		return unitSupply;
	}
	
	public UnitContainer getUnitContainer() {
		return unitContainer;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getMovement() {
		return movement;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public AttackType getAttackType() {
		return attackType;
	}
	
	public UnitType getUnitType() {
		return unitType;
	}

	public UnitCategory getUnitClass() {
		return unitClass;
	}
	
	public UnitImage getUnitImage() {
		return unitImage;
	}

	public void paint(Graphics g, int tileSize) {
		paintUnit(g, tileSize);
		unitHealth.paintHP(g, position.getX(), position.getY());
		if (capting) {
			paintCaptFlag(g, position.getX(), position.getY(), tileSize);
		}
	}
	
	public void paintCaptFlag(Graphics g, int unitX, int unitY, int tileSize) {
		g.setColor(Color.white);
		g.fillRect(unitX + tileSize / 10, unitY + 3 * tileSize / 5, tileSize / 3, tileSize / 3);
		g.setColor(Color.black);
		g.drawRect(unitX + tileSize / 10, unitY + 3 * tileSize / 5, tileSize / 3, tileSize / 3);

		g.fillRect(unitX + tileSize / 4, unitY + 17 * tileSize / 25, tileSize / 8, tileSize / 12);
		g.drawLine(unitX + tileSize / 4, unitY + 11 * tileSize / 15, unitX + tileSize / 4, unitY + 12 * tileSize / 15);
		g.fillRect(unitX + tileSize / 5, unitY + 12 * tileSize / 15, tileSize / 6, tileSize / 12);
	}

	protected void paintUnit(Graphics g, int tileSize) {
		Color unitColor = null;
		if (active) {
			unitColor = color;
		} else {
			unitColor = restingColor;
		}
		unitImage.paint(g, position.getX(), position.getY(), unitColor);
	}
}