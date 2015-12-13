package org.eiti.java.pang.utils;

import java.util.HashSet;
import java.util.Set;

public class KeyboardMonitor {
	
	private static Set<Integer> pressedKeys = new HashSet<>();
	
	public static synchronized void setKeyPressed(int keyCode) {
		pressedKeys.add(keyCode);
	}
	
	public static synchronized void setKeyReleased(int keyCode) {
		pressedKeys.remove(keyCode);
	}
	
	public static synchronized boolean isKeyPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}

}
