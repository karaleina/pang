package org.eiti.java.pang.model;

import org.eiti.java.pang.model.shapes.Rectangle;
import org.eiti.java.pang.model.shapes.Sphere;

public interface Collidable {
	
	public boolean collidesWith(Collidable c);
	
	public boolean collidesWith(Rectangle r);
	
	public boolean collidesWith(Sphere s);

}
