package org.eiti.java.pang.game;

import java.awt.Dimension;
import java.util.Map;

import org.eiti.java.pang.model.ExtraObjectType;

public interface GameLevelConfiguration {
	
	public Dimension getGameWorldSize();
	
	public void loadObjects(GameLevel level);

	public int getTimeForLevel();
	
	public Map<ExtraObjectType, Double> getExtraObjectsProbabilities();

}
