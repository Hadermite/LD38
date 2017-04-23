package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.ld38.game.Tile.TileType;

public class World extends View {

	public static final int WIDTH = Engine.WIDTH / Tile.SIZE;
	public static final int HEIGHT = Engine.HEIGHT / Tile.SIZE;

	private static final float DAY_TIME = 1f;

	private Tile[] tiles = new Tile[WIDTH * HEIGHT];

	private Game game;
	private TileType itemInHand;
	private int money = 5000;
	private int income = 0;
	private int electricityProductionTotal = 0;
	private int waterProductionTotal = 0;
	private int electricityProduction = 0;
	private int waterProduction = 0;
	private boolean hasPlacedTile = false;
	private float dayTimer = 0;
	
	public World(Game game) {
		super(new Texture(0xFFFFFFFF), new Transform(0, 0, WIDTH * Tile.SIZE, HEIGHT * Tile.SIZE));
		this.game = game;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile(TileType.GRASS, x, y, this);
				tiles[x + y * WIDTH] = tile;
				addSubview(tile);
			}
		}
		
		InputEnabledViews.setEnabled(this);
	}

	@Override
	public void update(float delta) {
		hasPlacedTile = false;
		dayTimer += delta;
		if (dayTimer >= DAY_TIME) {
			dayTimer -= DAY_TIME;
			money += income;
			game.getHud().updateHud();
		}
	}

	public Tile getTile(int xPos, int yPos) {
		if (xPos < 0 || xPos >= WIDTH || yPos < 0 || yPos >= HEIGHT)
			return null;
		return tiles[xPos + yPos * WIDTH];
	}

	public Tile getTile(float x, float y) {
		return getTile((int) (x / Tile.SIZE), (int) (y / Tile.SIZE));
	}

	public void setItemInHand(TileType type) {
		this.itemInHand = type;
	}

	public void tileClicked(Tile tile) {
		if (hasPlacedTile)
			return;
		if (tile.getType() != TileType.GRASS) {
			game.openUpgradeView(tile);
			return;
		}
		if (itemInHand == null)
			return;
		if (money < itemInHand.getCost() || electricityProduction < itemInHand.getElectricityConsumption()
				|| waterProduction < itemInHand.getWaterConsumption())
			return;
		int elCons = itemInHand.getElectricityConsumption();
		int waterCons = itemInHand.getWaterConsumption();
		if (elCons < 0) {
			electricityProductionTotal += elCons * -1;
		}
		if (waterCons < 0) {
			waterProductionTotal += waterCons * -1;
		}

		electricityProduction -= itemInHand.getElectricityConsumption();
		waterProduction -= itemInHand.getWaterConsumption();
		money -= itemInHand.getCost();
		income += itemInHand.getTaxIncome();
		tile.setType(itemInHand);
		hasPlacedTile = true;
		game.getHud().updateHud();
	}

	public int getMoney() {
		return money;
	}

	public int getIncome() {
		return income;
	}

	public TileType getItemInHand() {
		return itemInHand;
	}

	public int getElectricityProductionTotal() {
		return electricityProductionTotal;
	}

	public int getWaterProductionTotal() {
		return waterProductionTotal;
	}

	public int getElectricityProduction() {
		return electricityProduction;
	}

	public int getWaterProduction() {
		return waterProduction;
	}
}
