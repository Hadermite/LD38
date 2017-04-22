package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.Style;

public class Shop extends View implements UIButtonListener {

	public static final int WIDTH = (int) (Engine.WIDTH * 0.8);
	public static final int HEIGHT = (int) (Engine.HEIGHT * 0.8);
	
	private Game game;
	
	private UIButton btnClose;

	public Shop(Game game) {
		super(new Texture(0xFF777777), new Transform((Engine.WIDTH - WIDTH) / 2, (Engine.HEIGHT - HEIGHT) / 2, WIDTH, HEIGHT));
		this.game = game;
		
		btnClose = new UIButton("X", Style.FONT_TEXT, 64, new Texture(0xFFFF0000),
				new Transform(WIDTH - Style.BUTTON_CLOSE_SIZE, HEIGHT - Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE));
		
		btnClose.addButtonListener(this);
		
		addSubview(btnClose);
	}

	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnClose) {
			game.closeShop();
		}
	}
	
	@Override
	public void onButtonDown(UIButton button, int mouseButton) {}
}
