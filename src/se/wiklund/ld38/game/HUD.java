package se.wiklund.ld38.game;

import se.wiklund.haderengine.Engine;
import se.wiklund.haderengine.View;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UILabel;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.Style;
import se.wiklund.ld38.Textures;
import se.wiklund.ld38.util.UnitFormatter;

public class HUD extends View implements UIButtonListener {

	public static final int WIDTH = Engine.WIDTH;
	public static final int HEIGHT = Engine.HEIGHT - (World.HEIGHT * Tile.SIZE);
	private static final int CONTENT_HEIGHT = (int) (HEIGHT * 0.9);
	private static final int PADDING = 32;

	private Game game;

	private UILabel lblMoney;
	private UILabel lblIncome;
	private View vwElectricity, vwWater, vwPopulation;
	private UILabel lblElectricity, lblWater, lblPopulation;

	private UIButton btnShop;

	public HUD(Game game) {
		super(Textures.TEX_WHITE, new Transform(0, Engine.HEIGHT - HEIGHT, WIDTH, HEIGHT));
		this.game = game;

		int y = HEIGHT - HEIGHT + (HEIGHT - CONTENT_HEIGHT);
		lblMoney = new UILabel("", Style.FONT_TITLE, CONTENT_HEIGHT, PADDING, y, false);

		lblIncome = new UILabel("", Style.FONT_TEXT_GREEN, CONTENT_HEIGHT, 0, y, false);

		vwElectricity = new View(Textures.TEX_ELECTRICITY, new Transform(0, y, 36, 36));
		lblElectricity = new UILabel("", Style.FONT_TEXT, CONTENT_HEIGHT, 0, y, false);

		vwWater = new View(Textures.TEX_WATER, new Transform(0, y, 36, 36));
		lblWater = new UILabel("", Style.FONT_TEXT, CONTENT_HEIGHT, 0, y, false);

		vwPopulation = new View(Textures.TEX_POPULATION, new Transform(0, y, 36, 36));
		lblPopulation = new UILabel("", Style.FONT_TEXT, CONTENT_HEIGHT, 0, y, false);

		btnShop = new UIButton("", Style.FONT_TEXT, CONTENT_HEIGHT, Textures.TEX_SHOP, new Transform(
				WIDTH - PADDING - CONTENT_HEIGHT, (HEIGHT - CONTENT_HEIGHT) / 2, CONTENT_HEIGHT, CONTENT_HEIGHT));

		btnShop.addButtonListener(this);

		addSubview(lblMoney);
		addSubview(lblIncome);
		addSubview(btnShop);
		addSubview(vwElectricity);
		addSubview(lblElectricity);
		addSubview(vwWater);
		addSubview(lblWater);
		addSubview(vwPopulation);
		addSubview(lblPopulation);

		updateHud();
	}

	public void updateHud() {
		lblMoney.setText("$" + game.getWorld().getMoney());

		int elTotal = game.getWorld().getElectricityProductionTotal();
		int el = game.getWorld().getElectricityProduction();
		String elStr = UnitFormatter.formatEnergy(el) + " / " + UnitFormatter.formatEnergy(elTotal);
		lblElectricity.setText(elStr);

		int waterTotal = game.getWorld().getWaterProductionTotal();
		int water = game.getWorld().getWaterProduction();
		String waterStr = water + " L / " + waterTotal + " L";
		lblWater.setText(waterStr);

		lblIncome.setText("+$" + game.getWorld().getIncome() + "/day");

		lblPopulation.setText(game.getWorld().getPopulation() + " / " + Game.POPULATION_GOAL);

		int x = lblMoney.getTransform().getWidth() + PADDING * 3;
		lblIncome.getTransform().setX(x);

		x += lblIncome.getTransform().getWidth() + PADDING * 2;
		vwElectricity.getTransform().setX(x);

		x += vwElectricity.getTransform().getWidth();
		lblElectricity.getTransform().setX(x);

		x += lblElectricity.getTransform().getWidth() + PADDING * 2;
		vwWater.getTransform().setX(x);

		x += vwWater.getTransform().getWidth();
		lblWater.getTransform().setX(x);

		x += lblWater.getTransform().getWidth() + PADDING * 2;
		vwPopulation.getTransform().setX(x);

		x += vwPopulation.getTransform().getWidth();
		lblPopulation.getTransform().setX(x);
	}

	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnShop) {
			game.openShop();
		}
	}

	@Override
	public void onButtonDown(UIButton button, int mouseButton) {
	}
}
