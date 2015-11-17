package org.eiti.java.pang.game;

import java.awt.Dimension;

public interface GameLevelConfiguration {
	
	public Dimension getGameWorldSize();
	
	public void loadObjects(GameLevel level);

}
