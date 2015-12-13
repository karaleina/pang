
package org.eiti.java.pang.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.Heart;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.SuperWeapon;

public class ExtraObjectsCreator {
	
	private Map<ExtraObjectType, Double> probabilities;
	
	private Random randomGenerator = new Random();
	
	public ExtraObjectsCreator(Map<ExtraObjectType, Double> probabilities) {
		this.probabilities = probabilities;
	}
	
	public List<ExtraObject> tryToCreateExtraObjects(GameLevel level) {
		List<ExtraObject> createdObjects = new ArrayList<ExtraObject>();
		for(ExtraObjectType type : probabilities.keySet()) {
			if(randomGenerator.nextDouble() < probabilities.get(type)) {
				createdObjects.add(createObject(type, level));
			}
		}
		return createdObjects;
	}
	
	private ExtraObject createObject(ExtraObjectType type, GameLevel level) {
		switch(type) {
		case heart:
			return new Heart(
				calculateExtraObjectPosition(level, Heart.getWidth(), Heart.getHeight()),
				Game.GAME_WORLD_SIZE);
		case superWeapon:
			return new SuperWeapon(
				calculateExtraObjectPosition(level, SuperWeapon.getWidth(), SuperWeapon.getHeight()),
				Game.GAME_WORLD_SIZE);
		default:
			throw new RuntimeException("Unexpected extra object type!");
		}
	}
	
	private Point2D calculateExtraObjectPosition(GameLevel level, int objectWidth, int objectHeight) {
		// choose random position at "floor level" that does not intersect with player avatar
		PlayerAvatar avatar = level.getPlayerAvatar();
		int avatarXPosition =  avatar.getRectangularShape().getIntX();
		
		int rangePivot = avatarXPosition - objectWidth;
		int rangeWidth = level.getGameWorldSize().width - 2 * objectWidth - PlayerAvatar.getWidth();
		int randomPosition = Math.abs(randomGenerator.nextInt()) % rangeWidth;
		
		if(randomPosition < rangePivot) {
			return new Point2D.Double(
				randomPosition,
				level.getGameWorldSize().height - objectHeight);
		} else {
			return new Point2D.Double(
				objectWidth + PlayerAvatar.getWidth() + randomPosition,
				level.getGameWorldSize().height - objectHeight);
		}
	}

}

