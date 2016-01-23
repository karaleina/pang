
package org.eiti.java.pang.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eiti.java.pang.global.GlobalConstants;
import org.eiti.java.pang.global.ImageLoader;
import org.eiti.java.pang.model.ExtraObject;
import org.eiti.java.pang.model.ExtraObjectType;
import org.eiti.java.pang.model.Heart;
import org.eiti.java.pang.model.PlayerAvatar;
import org.eiti.java.pang.model.weapons.SuperWeapon;

public class ExtraObjectsCreator {
	
	private Map<ExtraObjectType, Double> probabilitiesPerSecond;
	
	private Random randomGenerator = new Random();
	
	public ExtraObjectsCreator(Map<ExtraObjectType, Double> probabilities) {
		this.probabilitiesPerSecond = probabilities;
	}
	
	public List<ExtraObject> tryToCreateExtraObjects(GameLevel level) {
		List<ExtraObject> createdObjects = new ArrayList<ExtraObject>();
		for(ExtraObjectType type : probabilitiesPerSecond.keySet()) {
			if(randomGenerator.nextDouble() < probabilitiesPerSecond.get(type)) {
				createdObjects.add(createObject(type, level));
			}
		}
		return createdObjects;
	}
	
	private ExtraObject createObject(ExtraObjectType type, GameLevel level) {
		switch(type) {
		case heart:
			return new Heart(
				calculateExtraObjectPosition(
						level,
						ImageLoader.getInstance().heartWidth,
						ImageLoader.getInstance().heartHeight),
				ImageLoader.getInstance().heartWidth,
				ImageLoader.getInstance().heartHeight,
				GlobalConstants.GAME_WORLD_SIZE);
		case superWeapon:
			return new SuperWeapon(
				calculateExtraObjectPosition(
						level,
						ImageLoader.getInstance().superWeaponWidth,
						ImageLoader.getInstance().superWeaponHeight),
				ImageLoader.getInstance().superWeaponWidth,
				ImageLoader.getInstance().superWeaponHeight,
				GlobalConstants.GAME_WORLD_SIZE);
		default:
			throw new RuntimeException("Unexpected extra object type!");
		}
	}
	
	private Point2D calculateExtraObjectPosition(GameLevel level, int objectWidth, int objectHeight) {
		// choose random position at "floor level" that does not intersect with player avatar
		int avatarXPosition =  PlayerAvatar.getInstance().getRectangularShape().getIntX();
		
		int rangePivot = avatarXPosition - objectWidth;
		int rangeWidth = level.getGameWorldSize().width - 2 * objectWidth - PlayerAvatar.getInstance().getWidth();
		int randomPosition = Math.abs(randomGenerator.nextInt()) % rangeWidth;
		
		if(randomPosition < rangePivot) {
			return new Point2D.Double(
				randomPosition,
				level.getGameWorldSize().height - objectHeight);
		} else {
			return new Point2D.Double(
				objectWidth + PlayerAvatar.getInstance().getWidth() + randomPosition,
				level.getGameWorldSize().height - objectHeight);
		}
	}

}

