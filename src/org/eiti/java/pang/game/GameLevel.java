package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.Drawable;
import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.Missile;

public class GameLevel implements Drawable {

	private int levelNumber;

	private int windowWidth;
	private int windowHeight;

	private PlayerAvatar playerAvatar;

	private Collection<Ball> balls;

	private Collection<Missile> missiles;
	
	private Collection<ExtraObject> extraObjects;

	public GameLevel(
			int levelNumber,
			GameLevelConfiguration configuration,
			PlayerAvatar playerAvatar,
			Dimension windowSize) {
		
		this.levelNumber = levelNumber;
		this.windowWidth = windowSize.width;
		this.windowHeight = windowSize.height;
		this.playerAvatar = playerAvatar;
		
		balls = new LinkedList<Ball>();
		missiles = new LinkedList<Missile>();
		extraObjects = new LinkedList<ExtraObject>();
		
		configuration.loadObjects(this);
	}
	
	public int getLevelNumber() {
		return levelNumber;
	}
	
	public Collection<Ball> getBalls() {
		return balls;
	}
	
	public Collection<Missile> getMissiles() {
		return missiles;
	}
	
	public Collection<ExtraObject> getExtraObjects() {
		return extraObjects;
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}

	@Override
	public void draw(Graphics g) {
		for(Ball b : balls) {
			b.draw(g);
		}
		for(Missile m : missiles) {
			m.draw(g);
		}
		for(ExtraObject e : extraObjects) {
			e.draw(g);
		}
		playerAvatar.draw(g);
	}

}
