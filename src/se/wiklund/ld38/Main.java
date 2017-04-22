package se.wiklund.ld38;

import se.wiklund.haderengine.Engine;
import se.wiklund.ld38.game.Game;

public class Main {
	
	public static Engine engine;
	
	public static void main(String[] args) {
		engine = new Engine("LD38", false, true);
		engine.setState(new Game());
		engine.start();
	}
}
