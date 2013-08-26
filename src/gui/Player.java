package gui;

import utilities.VirtualPoint;

public class Player extends VirtualPoint {
	private VirtualPoint finish;
	
	Player() {
		super();
		finish = new VirtualPoint();
	}

	Player(VirtualPoint start, VirtualPoint finish) {
		super(start);
		this.finish = finish;
	}
	
	public void setPosition(VirtualPoint start) {
		setX(start.getX());
		setY(start.getY());
		setHeight(start.getHeight());
		setWidth(start.getWidth());
	}
	
	public VirtualPoint getFinish() {
		return finish;
	}
	
	public void setFinish(VirtualPoint finish) {
		this.finish = finish;
	}

	public boolean atFinish() {
		if (this.distanceTo(finish) == 0)
			return true;
		else
			return false;
	}
}