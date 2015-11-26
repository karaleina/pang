package org.eiti.java.pang.config;

import java.awt.Dimension;
import java.util.Map;

import org.eiti.java.pang.game.GameLevel;
import org.eiti.java.pang.model.ExtraObjectType;

public interface GameLevelConfiguration {
	
	public Dimension getGameWorldSize();
	
	public void loadObjects(GameLevel level);

	public int getTimeForLevel();
	
	public Map<ExtraObjectType, Double> getExtraObjectsProbabilities();

}
