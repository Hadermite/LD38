package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.input.Cursor;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.ld38.Textures;
import se.wiklund.ld38.Textures;
import se.wiklund.ld38.game.Tile.TileType;

public class World extends View {

	public static final int WIDTH = Engine.WIDTH / Tile.SIZE;
	public static final int HEIGHT = Engine.HEIGHT / Tile.SIZE;
	
	private static final float DAY_TIME = 1f;

	private Tile[] tiles = new Tile[WIDTH * HEIGHT];

	private Game game;
	private TileType tileTypeInHand;
	private Tile tileInHand;
	private int money = 5000;
	private int income = 0;
	private int population = 0;
	private int electricityProductionTotal = 0;
	private int waterProductionTotal = 0;
	private int electricityProduction = 0;
	private int waterProduction = 0;
	private boolean hasPlacedTile = false;
	private float dayTimer = 0;

	public World(Game game) {
		super(Textures.TEX_WHITE, new Transform(0, 0, WIDTH * Tile.SIZE, HEIGHT * Tile.SIZE));
		this.game = game;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile(TileType.GRASS, x, y, this);
				tiles[x + y * WIDTH] = tile;
				addSubview(tile);
			}
		}

		tiles[0 + 3 * WIDTH].setType(TileType.ROAD);
		tiles[1 + 3 * WIDTH].setType(TileType.ROAD);
		tiles[2 + 3 * WIDTH].setType(TileType.ROAD);

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

		if (tileInHand != null) {
			Transform cursor = Cursor.getTransform();
			Tile tile = getTile(cursor.getX(), cursor.getY());
			if (tile != null) {
				tileInHand.getTransform().setPosition(tile.getTransform().getX(), tile.getTransform().getY());
			}
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
		this.tileTypeInHand = type;
		if (tileInHand != null) {
			removeSubview(tileInHand);
		}
		tileInHand = new Tile(type, 0, 0, this);
		addSubview(tileInHand);
	}

	public void tileClicked(Tile tile) {
		if (hasPlacedTile)
			return;
		if (tile.getType() != TileType.GRASS) {
			game.openUpgradeView(tile);
			return;
		}
		if (tileTypeInHand == null)
			return;
		if (money < tileTypeInHand.getCost()) {
			game.showError("You don't have enough money!");
			return;
		}
		if (electricityProduction < tileTypeInHand.getElectricityConsumption()) {
			game.showError("Not enough electricity production!");
			return;
		}
		if (waterProduction < tileTypeInHand.getWaterConsumption()) {
			game.showError("Not enough water production!");
			return;
		}
		
		int pop = tileTypeInHand.getPopulation();
		int elCons = tileTypeInHand.getElectricityConsumption();
		int waterCons = tileTypeInHand.getWaterConsumption();
		if (pop > 0) {
			population += pop;
			
			if (population >= Game.POPULATION_GOAL) {
				game.win();
			}
		}
		if (elCons < 0) {
			electricityProductionTotal += elCons * -1;
		}
		if (waterCons < 0) {
			waterProductionTotal += waterCons * -1;
		}

		if (tileTypeInHand == TileType.HOUSE) {
			Tile tileUnder = getTile(tile.getxPos(), tile.getyPos() - 1);
			if (tileUnder == null || tileUnder.getType() != TileType.ROAD) {
				game.showError(new String[] { "You need to place the house beside a road", "with the entrance facing the road!", });
				return;
			}
		}
		if (tileTypeInHand == TileType.ROAD) {
			Tile tileUnder = getTile(tile.getxPos(), tile.getyPos() - 1);
			Tile tileOver = getTile(tile.getxPos(), tile.getyPos() + 1);
			Tile tileLeft = getTile(tile.getxPos() - 1, tile.getyPos());
			Tile tileRight = getTile(tile.getxPos() + 1, tile.getyPos());
			
			boolean hasAdjacentRoad = false;
			
			if (tileUnder != null && tileUnder.getType() == TileType.ROAD) hasAdjacentRoad = true;
			if (tileOver != null && tileOver.getType() == TileType.ROAD) hasAdjacentRoad = true;
			if (tileLeft != null && tileLeft.getType() == TileType.ROAD) hasAdjacentRoad = true;
			if (tileRight != null && tileRight.getType() == TileType.ROAD) hasAdjacentRoad = true;
			
			if (!hasAdjacentRoad) {
				game.showError("You need to place it next to another road!");
				return;
			}
		}

		electricityProduction -= tileTypeInHand.getElectricityConsumption();
		waterProduction -= tileTypeInHand.getWaterConsumption();
		money -= tileTypeInHand.getCost();
		income += tileTypeInHand.getTaxIncome();
		tile.setType(tileTypeInHand);
		hasPlacedTile = true;
		game.getHud().updateHud();
	}
	
	public void applyUpgrade(Tile tile) {
		int oldLevel = tile.getUpgradeLevel() - 1;
		int newLevel = tile.getUpgradeLevel();
		TileType type = tile.getType();
		int cost = type.getCost() * newLevel;
		int popIncrease = (type.getPopulation() * newLevel) - (type.getPopulation() * oldLevel);
		int elIncrease = (type.getElectricityConsumption() * newLevel) - (type.getElectricityConsumption() * oldLevel);
		int waterIncrease = (type.getWaterConsumption() * newLevel) - (type.getWaterConsumption() * oldLevel);
		
		if (elIncrease < 0) {
			electricityProductionTotal -= elIncrease;
		}
		if (waterIncrease < 0) {
			waterProductionTotal -= waterIncrease;
		}
		
		money -= cost;
		population += popIncrease;
		electricityProduction -= elIncrease;
		waterProduction -= waterIncrease;
		
		if (population >= Game.POPULATION_GOAL) {
			game.win();
		}
		
		game.getHud().updateHud();
	}

	public int getMoney() {
		return money;
	}
	
	public int getPopulation() {
		return population;
	}

	public int getIncome() {
		return income;
	}

	public TileType getItemInHand() {
		return tileTypeInHand;
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
