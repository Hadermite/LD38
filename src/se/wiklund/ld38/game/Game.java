package se.wiklund.ld38.game;

import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;

public class Game extends State {
	
	private World world = new World();
	private HUD hud = new HUD(this);
	private Shop shop;
	private boolean shouldOpenShop, shouldCloseShop;
	
	public Game() {
		addSubview(world);
		addSubview(hud);
	}
	
	@Override
	public void update(float delta) {
		if (shouldOpenShop) {
			shouldOpenShop = false;
			if (shop != null) {
				System.err.println("Shop is already showing!");
				return;
			}
			InputEnabledViews.saveState("shopOpen");
			shop = new Shop(this);
			addSubview(shop);
		}
		
		if (shouldCloseShop) {
			shouldCloseShop = false;
			if (shop == null) {
				System.err.println("Shop is already closed!");
				return;
			}
			InputEnabledViews.loadState("shopOpen");
			removeSubview(shop);
			shop = null;
		}
	}
	
	public World getWorld() {
		return world;
	}
	
	public HUD getHud() {
		return hud;
	}
	
	public void openShop() {
		shouldOpenShop = true;
	}
	
	public void closeShop() {
		shouldCloseShop = true;
	}
}
