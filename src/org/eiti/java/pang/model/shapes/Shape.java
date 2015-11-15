package org.eiti.java.pang.model.shapes;

import java.awt.Point;

import org.eiti.java.pang.model.Collidable;

public abstract class Shape implements Collidable {
	
	private Point position;
	
	protected Shape(Point position) {
		this.position = position;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void moveBy(int dx, int dy) {
		position.setLocation(position.getX() + dx, position.getY() + dy);
	}

}
