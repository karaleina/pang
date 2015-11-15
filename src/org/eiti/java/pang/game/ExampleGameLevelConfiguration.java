package org.eiti.java.pang.game;

import java.awt.Point;

import org.eiti.java.pang.model.Ball;
import org.eiti.java.pang.model.Heart;
import org.eiti.java.pang.model.weapons.Missile;
import org.eiti.java.pang.model.weapons.SuperMissile;
import org.eiti.java.pang.model.weapons.SuperWeapon;

public class ExampleGameLevelConfiguration implements GameLevelConfiguration {
	
	@Override
	public void loadObjects(GameLevel level) {
		
		int windowHeight = level.getWindowHeight();
		
		Ball ball = new Ball(new Point(100, 30), 8);
		level.getBalls().add(ball);
		
		Heart heart = new Heart(new Point(180, windowHeight - Heart.HEART_HEIGHT));
		level.getExtraObjects().add(heart);
		
		SuperWeapon weapon = new SuperWeapon(new Point(40, windowHeight - SuperWeapon.SUPER_WEAPON_HEIGHT));
		level.getExtraObjects().add(weapon);
		
		Missile missile = new SuperMissile(new Point(60, 100));
		level.getMissiles().add(missile);
		
	}

}
