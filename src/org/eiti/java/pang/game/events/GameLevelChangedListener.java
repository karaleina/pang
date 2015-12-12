package org.eiti.java.pang.game.events;

import org.eiti.java.pang.game.GameLevel;

public interface GameLevelChangedListener {
	public void onGameLevelChanged(GameLevel newLevel);
}
