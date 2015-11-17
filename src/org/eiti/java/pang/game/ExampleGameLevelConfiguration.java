package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.awt.Point;

import org.eiti.java.pang.gui.ImageLoader;
import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.Heart;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.SuperMissile;
import org.eiti.java.pang.model.weapons.SuperWeapon;

// TODO parsowanie pliku konfiguracyjnego
public class ExampleGameLevelConfiguration implements GameLevelConfiguration {
	
	@Override
	public void loadObjects(GameLevel level) {
		
		int windowHeight = level.getGameWorldSize().height;
		
		Ball ball = new Ball(new Point(100, 30), 8);
		level.getBalls().add(ball);
		
		Heart heart = new Heart(new Point(180, windowHeight - ImageLoader.heartImage.getHeight()));
		level.getExtraObjects().add(heart);
		
		SuperWeapon weapon = new SuperWeapon(new Point(40, windowHeight - ImageLoader.superWeaponImage.getHeight()));
		level.getExtraObjects().add(weapon);
		
		Missile missile = new SuperMissile(new Point(60, 100));
		level.getMissiles().add(missile);
		
	}

	@Override
	public Dimension getGameWorldSize() {
		return new Dimension(800, 450);
	}

}
