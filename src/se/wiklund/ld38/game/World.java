package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.ld38.game.Tile.TileType;

public class World extends View {

	public static final int WIDTH = Engine.WIDTH / Tile.SIZE;
	public static final int HEIGHT = Engine.HEIGHT / Tile.SIZE;

	private Tile[] tiles = new Tile[WIDTH * HEIGHT];

	public World() {
		super(new Texture(0xFFFFFFFF), new Transform(0, 0, WIDTH * Tile.SIZE, HEIGHT * Tile.SIZE));
		// Generate an empty world
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				Tile tile = new Tile(TileType.GRASS, x, y);
				tiles[x + y * WIDTH] = tile;
				addSubview(tile);
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
}
