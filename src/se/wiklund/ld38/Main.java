package se.wiklund.ld38;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.ld38.game.Game;

public class Main {
	
	public static Engine engine;
	
	public static void main(String[] args) {
		engine = new Engine("LD38", false, true);
		State state = null;
		if (args.length > 0 && args[0].equalsIgnoreCase("-noSplash")) {
			state = new Game();
		} else {
			state = new SplashScreen(engine);
		}
		engine.setState(state);
		engine.start();
	}
}
