package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.Style;
import se.wiklund.ld38.Textures;
import se.wiklund.ld38.game.Tile.TileType;

public class Shop extends View implements UIButtonListener {

	public static final int WIDTH = (int) (Engine.WIDTH * 0.8);
	public static final int HEIGHT = (int) (Engine.HEIGHT * 0.8);
	public static final int X_COUNT = 4;
	public static final int Y_COUNT = 2;

	private Game game;

	private UIButton btnClose;

	public Shop(Game game) {
		super(Textures.TEX_GRAY, new Transform((Engine.WIDTH - WIDTH) / 2, (Engine.HEIGHT - HEIGHT) / 2, WIDTH, HEIGHT));
		this.game = game;

		btnClose = new UIButton("X", Style.FONT_TEXT, 64, new Texture(0xFFFF0000),
				new Transform(WIDTH - Style.BUTTON_CLOSE_SIZE, HEIGHT - Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE));

		btnClose.addButtonListener(this);

		addSubview(btnClose);

		TileType[] tileTypes = TileType.values();
		
		int index = 0;
		for (int y = 0; y < Y_COUNT; y++) {
			for (int x = 0; x < X_COUNT; x++) {
				int yPos = Y_COUNT - y - 1;
				if (index >= tileTypes.length)
					break;
				ShopItem item = new ShopItem(tileTypes[index], x, yPos, game);
				addSubview(item);
				index++;
			}
		}
	}

	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnClose) {
			game.closeShop();
		}
	}

	@Override
	public void onButtonDown(UIButton button, int mouseButton) {
	}
}
