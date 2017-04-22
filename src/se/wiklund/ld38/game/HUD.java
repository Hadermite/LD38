package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.input.InputEnabledViews;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UILabel;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.Style;

public class HUD extends View implements UIButtonListener {

	public static final int WIDTH = Engine.WIDTH;
	public static final int HEIGHT = Engine.HEIGHT - (World.HEIGHT * Tile.SIZE);
	private static final int CONTENT_HEIGHT = (int) (HEIGHT * 0.8);
	private static final int PADDING = 32;

	private Game game;
	
	private UILabel lblMoney;

	private UIButton btnShop;

	public HUD(Game game) {
		super(new Texture(0xFFFFFFFF), new Transform(0, Engine.HEIGHT - HEIGHT, WIDTH, HEIGHT));
		this.game = game;

		lblMoney = new UILabel("$0", Style.FONT_TEXT, CONTENT_HEIGHT, PADDING, (HEIGHT - CONTENT_HEIGHT) / 2, false);

		btnShop = new UIButton("", Style.FONT_TEXT, CONTENT_HEIGHT, new Texture(0xFF00FF00),
				new Transform(WIDTH - PADDING - CONTENT_HEIGHT, (HEIGHT - CONTENT_HEIGHT) / 2, CONTENT_HEIGHT, CONTENT_HEIGHT));

		btnShop.addButtonListener(this);
		
		addSubview(lblMoney);
		addSubview(btnShop);
	}

	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnShop) {
			
		}
	}
	
	@Override
	public void onButtonDown(UIButton button, int mouseButton) {}
}
