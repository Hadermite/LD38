package se.wiklund.ld38;

import se.wiklund.haderengine.Engine;
import se.wiklund.ld38.game.Game;

public class Main {
	
	public static Engine engine;
	
	public static void main(String[] args) {
		System.out.println("Starting...");
		engine = new Engine("LD38", false, true);
		System.out.println("Engine initialized!");
		engine.setState(new Game());
		System.out.println("State set!");
		engine.start();
	}
}
