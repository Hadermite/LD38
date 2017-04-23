package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.ld38.Win;

public class Game extends State {
	
	public static final int POPULATION_GOAL = 1000;
	
	private Engine engine;
	private World world = new World(this);
	private HUD hud = new HUD(this);
	private Shop shop;
	private UpgradeView upgradeView;
	private ErrorView errorView;
	private boolean shouldOpenShop, shouldCloseShop;
	private boolean shouldOpenUpgradeView, shouldCloseUpgradeView;
	private boolean shouldShowError, shouldCloseError;
	private Tile upgradeViewTile;
	private String[] errorMessages;
	private long startTime;
	
	public Game(Engine engine) {
		this.engine = engine;
		addSubview(world);
		addSubview(hud);
		
		startTime = System.currentTimeMillis();
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
			removeSubview(shop);
			shop = null;
			InputEnabledViews.loadState("shopOpen");
		}
		
		if (shouldOpenUpgradeView) {
			shouldOpenUpgradeView = false;
			openUpgradeViewNow(upgradeViewTile);
		}
		
		if (shouldCloseUpgradeView) {
			shouldCloseUpgradeView = false;
			closeUpgradeViewNow();
		}
		
		if (shouldShowError) {
			shouldShowError = false;
			showErrorNow(errorMessages);
		}
		
		if (shouldCloseError) {
			shouldCloseError = false;
			closeErrorNow();
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
	
	public void showError(String message) {
		errorMessages = new String[] { message };
		shouldShowError = true;
	}
	
	public void showError(String[] messages) {
		errorMessages = messages;
		shouldShowError = true;
	}
	
	public void closeError() {
		shouldCloseError = true;
	}
	
	public void win() {
		long timeTaken = System.currentTimeMillis() - startTime;
		float seconds = timeTaken / 1000f;
		engine.setState(new Win(seconds, engine));
	}
	
	private void showErrorNow(String[] messages) {
		if (errorView != null) {
			closeErrorNow();
		}
		
		InputEnabledViews.saveState("error_view");
		errorView = new ErrorView(messages, this);
		addSubview(errorView);
	}
	
	private void closeErrorNow() {
		if (errorView == null) {
			System.err.println("Tried to remove ErrorView when not present!");
			return;
		}
		removeSubview(errorView);
		errorView = null;
		InputEnabledViews.loadState("error_view");
	}
	
	public World getWorld() {
		return world;
	}
	
	public HUD getHud() {
		return hud;
	}
}
