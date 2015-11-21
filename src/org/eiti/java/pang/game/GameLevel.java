package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.Drawable;
import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.Missile;

public class GameLevel implements Drawable {

	private int levelNumber;

	private Dimension gameWorldSize;

	private PlayerAvatar playerAvatar;

	private Collection<Ball> balls;

	private Collection<Missile> missiles;
	
	private Collection<ExtraObject> extraObjects;

	public GameLevel(
			int levelNumber,
			GameLevelConfiguration configuration,
			PlayerAvatar playerAvatar) {
		
		this.levelNumber = levelNumber;
		this.gameWorldSize = configuration.getGameWorldSize();
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
	
	public Dimension getGameWorldSize() {
		return gameWorldSize;
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
		
		drawLives(g);
	}

	private void drawLives(Graphics g) {
		final int heartSize = 25;
		final int offset = 5;
		for(int i = 0; i < playerAvatar.getLives(); i++){
			g.drawImage(
				ImageLoader.heartImage,
				gameWorldSize.width - (i + 1) * (heartSize + offset),
				offset,
				heartSize,
				heartSize,
				null);
			
		}
	}
}
