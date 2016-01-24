package org.eiti.java.pang.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * This class remembers which keys are pressed. It is also thread-safe.
 */
public class KeyboardMonitor {	
	
	/**
	 * It is a set of currently pressed keys.
	 */
	private static Set<Integer> pressedKeys = new HashSet<>();
	
	/**
	 * This method enables to add keys to the set. 
	 */
	public static synchronized void setKeyPressed(int keyCode) {
		pressedKeys.add(keyCode);
	}
	
	/**
	 * This method enables to remove keys from the set.
	 */
	public static synchronized void setKeyReleased(int keyCode) {
		pressedKeys.remove(keyCode);
	}
	
	/**
	 * This method enables to check if key defined by a keyCode is pressed.
	 */
	public static synchronized boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

}
