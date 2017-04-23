package se.wiklund.ld38.game;

import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;

public class Tile extends View {
	
	public static final int SIZE = 128;
	
	private TileType type;
	private int upgradeLevel = 1;
	private World world;
	
	public Tile(TileType type, int xPos, int yPos, World world) {
		super(type.getTextures()[0], new Transform(xPos * SIZE, yPos * SIZE, SIZE, SIZE));
		this.type = type;
		this.world = world;
		
		InputEnabledViews.setEnabled(this);
	}
	
	@Override
	public void onMouseButtonDown(int button) {
		world.tileClicked(this);
	}
	
	public TileType getType() {
		return type;
	}
	
	public void setType(TileType type) {
		this.type = type;
		setTexture(type.getTextures()[0]);
	}
	
	public int getUpgradeLevels() {
		return type.getTextures().length;
	}
	
	public boolean isAtMaxLevel() {
		return upgradeLevel >= type.getTextures().length;
	}
	
	public int getUpgradeLevel() {
		return upgradeLevel;
	}
	
	public void upgradeTile() {
		if (isAtMaxLevel()) {
			System.err.println("Tile of type " + type.getName() + " is already at max level!");
			return;
		}
		
		upgradeLevel++;
		setTexture(type.getTextures()[upgradeLevel - 1]);
	}
	
	public enum TileType {
		GRASS(0, 0, 0, 0, "Grass", new Texture[] { new Texture(0xFF56A01F) }),
		ROAD(10, 0, 0, 0, "Road", new Texture[] { new Texture(0xFFBCBCBC) }),
		
		HOUSE(1000, 20, 30000, 350, "House", new Texture[] {
				new Texture("/textures/tiles/house_1.png"),
				new Texture("/textures/tiles/house_2.png"),
				new Texture("/textures/tiles/house_3.png"),
		}),
		POWER_GENERATOR(1000, 0, -100000, 0, "Generator", new Texture[] {
				new Texture("/textures/tiles/wind_turbine.png"),
				new Texture("/textures/tiles/coal_power_plant.png"),
		}),
		WATER_TOWER(1000, 0, 0, -10000, "Water Tower", new Texture[] {
				new Texture("/textures/tiles/water_tower.png"),
		});

		private int cost;
		private int taxIncome;
		private int electricityConsumption;
		private int waterConsumption;
		private String name;
		private Texture[] textures;

		private TileType(int cost, int taxIncome, int electricityConsumption, int waterConsumption, String name, Texture[] textures) {
			this.cost = cost;
			this.taxIncome = taxIncome;
			this.electricityConsumption = electricityConsumption;
			this.waterConsumption = waterConsumption;
			this.name = name;
			this.textures = textures;
		}

		public int getCost() {
			return cost;
		}

		public int getTaxIncome() {
			return taxIncome;
		}

		public int getElectricityConsumption() {
			return electricityConsumption;
		}

		public int getWaterConsumption() {
			return waterConsumption;
		}

		public String getName() {
			return name;
		}

		public Texture[] getTextures() {
			return textures;
		}
	}
}
