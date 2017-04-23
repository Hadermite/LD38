package se.wiklund.ld38.game;

import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;

public class Game extends State {
	
	private World world = new World(this);
	private HUD hud = new HUD(this);
	private Shop shop;
	private UpgradeView upgradeView;
	private boolean shouldOpenShop, shouldCloseShop;
	private boolean shouldOpenUpgradeView, shouldCloseUpgradeView;
	private Tile upgradeViewTile;
	
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
		
		if (shouldOpenUpgradeView) {
			shouldOpenUpgradeView = false;
			openUpgradeViewNow(upgradeViewTile);
		}
		
		if (shouldCloseUpgradeView) {
			shouldCloseUpgradeView = false;
			closeUpgradeViewNow();
		}
	}
	
	public void openShop() {
		shouldOpenShop = true;
	}
	
	public void closeShop() {
		shouldCloseShop = true;
	}
	
	public void openUpgradeView(Tile tile) {
		upgradeViewTile = tile;
		shouldOpenUpgradeView = true;
	}

	public void closeUpgradeView() {
		shouldCloseUpgradeView = true;
	}
	
	private void openUpgradeViewNow(Tile tile) {
		if (upgradeView != null) {
			closeUpgradeViewNow();
		}

		InputEnabledViews.saveState("upgrade_view");
		upgradeView = new UpgradeView(tile, this);
		addSubview(upgradeView);
	}
	
	private void closeUpgradeViewNow() {
		if (upgradeView == null) {
			System.err.println("Tried to remove UpgradeView when not present!");
			return;
		}

		removeSubview(upgradeView);
		upgradeView = null;
		InputEnabledViews.loadState("upgrade_view");
	}
	
	public World getWorld() {
		return world;
	}
	
	public HUD getHud() {
		return hud;
	}
}
