package se.wiklund.ld38.game;

import se.wiklund.haderengine.State;

public class Game extends State {
	
	private World world = new World();
	private HUD hud = new HUD(this);
	
	public Game() {
		addSubview(world);
		addSubview(hud);
	}
	
	public World getWorld() {
		return world;
	}
	
	public HUD getHud() {
		return hud;
	}
}
