package se.wiklund.ld38.game;

import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;

public class Tile extends View {
	
	public static final int SIZE = 64;
	
	private TileType type;

	public Tile(TileType type, int xPos, int yPos) {
		super(type.getTexture(), new Transform(xPos * SIZE, yPos * SIZE, SIZE, SIZE));
		this.type = type;
	}
	
	public TileType getType() {
		return type;
	}
	
	public void setType(TileType type) {
		this.type = type;
	}
	
	public enum TileType {
		
		GRASS(0, 0, 0, "Grass", new Texture(0xFF0c9b00));
		
		private int cost;
		private int electricityConsumption;
		private int waterConsumption;
		private String name;
		private Texture texture;
		
		private TileType(int cost, int electricityConsumption, int waterConsumption, String name, Texture texture) {
			this.cost = cost;
			this.electricityConsumption = electricityConsumption;
			this.waterConsumption = waterConsumption;
			this.name = name;
			this.texture = texture;
		}

		public int getCost() {
			return cost;
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

		public Texture getTexture() {
			return texture;
		}
	}
}
