package se.wiklund.ld38;

import se.wiklund.haderengine.Engine;

public class Main {
	
	public static Engine engine;
	
	public static void main(String[] args) {
		engine = new Engine("Density", true, true);
		engine.setState(new SplashScreen(engine));
		engine.start();
	}
}
