package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.graphics.Texture;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UILabel;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.Style;
import se.wiklund.ld38.Textures;

public class ErrorView extends View implements UIButtonListener {

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 500;
	
	private Game game;
	private UIButton btnClose;
	
	public ErrorView(String[] messages, Game game) {
		super(Textures.TEX_GRAY, new Transform((Engine.WIDTH - WIDTH) / 2, (Engine.HEIGHT - HEIGHT) / 2, WIDTH, HEIGHT));
		this.game = game;
		
		btnClose = new UIButton("X", Style.FONT_TEXT, 64, Textures.TEX_RED,
				new Transform(WIDTH - Style.BUTTON_CLOSE_SIZE, HEIGHT - Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE));
		btnClose.addButtonListener(this);
		
		UILabel lblTitle = new UILabel("Error!", Style.FONT_TITLE, 64, WIDTH / 2, HEIGHT - 32, true);
		
		addSubview(lblTitle);
		
		for (int i = 0; i < messages.length; i++) {
			UILabel lblMessage = new UILabel(messages[i], Style.FONT_TEXT, 40, WIDTH / 2, HEIGHT / 2 - ((i - messages.length / 2) * 45), true);
			addSubview(lblMessage);
		}
		
		addSubview(btnClose);
	}
	
	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnClose) {
			game.closeError();
		}
	}

	@Override
	public void onButtonDown(UIButton button, int mouseButton) {
	}
}
