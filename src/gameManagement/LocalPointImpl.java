package gameManagement;

import sharedObjects.gameObjects.interfaces.Point;

public class LocalPointImpl implements Point{
	

	private static final long serialVersionUID = -7665122984374293365L;
	private double x;
	private double y;
	
	public LocalPointImpl(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

}
