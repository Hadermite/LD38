package se.wiklund.ld38;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.State;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UILabel;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.game.Game;

public class Win extends State implements UIButtonListener {

	private Engine engine;
	private UIButton btnAgain, btnExit;
	private boolean playAgain, exitGame;

	public Win(float time, Engine engine) {
		this.engine = engine;
		View background = new View(Textures.TEX_WHITE, new Transform(0, 0, Engine.WIDTH, Engine.HEIGHT));
		String timeString = String.format("%.2f", time);

		UILabel lblTitle = new UILabel("You Did It!", Style.FONT_TITLE, 64, Engine.WIDTH / 2, 800, true);
		UILabel lblTime = new UILabel("It took " + timeString + " seconds!", Style.FONT_TEXT, 46, Engine.WIDTH / 2,
				700, true);

		btnAgain = new UIButton("Play Again", Style.FONT_BUTTON, 64, Textures.TEX_GRAY,
				new Transform((Engine.WIDTH - 512) / 2, 350, 512, 96));
		btnExit = new UIButton("Exit Game", Style.FONT_BUTTON, 64, Textures.TEX_GRAY,
				new Transform((Engine.WIDTH - 512) / 2, 200, 512, 96));
		
		btnAgain.addButtonListener(this);
		btnExit.addButtonListener(this);
		
		addSubview(background);
		addSubview(lblTitle);
		addSubview(lblTime);
		addSubview(btnAgain);
		addSubview(btnExit);
	}

	@Override
	public void update(float delta) {
		if (playAgain) {
			engine.setState(new Game(engine));
		}
		if (exitGame) {
			engine.exit();
		}
	}

	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnAgain) {
			playAgain = true;
		}
		if (button == btnExit) {
			exitGame = true;
		}
	}

	@Override
	public void onButtonDown(UIButton button, int mouseButton) {
	}
}
