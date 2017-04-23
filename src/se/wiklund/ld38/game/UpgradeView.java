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
import se.wiklund.ld38.util.UnitFormatter;

public class UpgradeView extends View implements UIButtonListener {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;

	private Tile tile;
	private Game game;
	private int cost;
	private UIButton btnClose, btnBuy;
	private boolean canBuy = false;
	private int elIncrease, waterIncrease;

	public UpgradeView(Tile tile, Game game) {
		super(Textures.TEX_GRAY,
				new Transform((Engine.WIDTH - WIDTH) / 2, (Engine.HEIGHT - HEIGHT) / 2, WIDTH, HEIGHT));
		this.tile = tile;
		this.game = game;

		cost = tile.getType().getCost() * (tile.getUpgradeLevel() + 1);

		btnClose = new UIButton("X", Style.FONT_TEXT, 64, Textures.TEX_RED,
				new Transform(WIDTH - Style.BUTTON_CLOSE_SIZE, HEIGHT - Style.BUTTON_CLOSE_SIZE,
						Style.BUTTON_CLOSE_SIZE, Style.BUTTON_CLOSE_SIZE));
		btnClose.addButtonListener(this);

		int y = HEIGHT - 32;
		UILabel lblTitle = new UILabel("Upgrade Building", Style.FONT_TITLE, 64, WIDTH / 2, y, true);

		if (tile.isAtMaxLevel()) {
			UILabel lblInfo = new UILabel("Max level reached!", Style.FONT_TEXT_GREEN, 64, WIDTH / 2, HEIGHT / 2, true);
			addSubview(lblTitle);
			addSubview(lblInfo);
			addSubview(btnClose);
			return;
		}

		y -= lblTitle.getTransform().getHeight() + 50;
		View vwElectricity = new View(Textures.TEX_ELECTRICITY, new Transform(10, y, 36, 36));
		String el1 = UnitFormatter
				.formatEnergy(tile.getType().getElectricityConsumption() * (tile.getUpgradeLevel() + 1) * -1) + "/day ";
		String el2 = "(Now: "
				+ UnitFormatter.formatEnergy(tile.getType().getElectricityConsumption() * tile.getUpgradeLevel() * -1)
				+ "/day)";
		UILabel lblElectricity = new UILabel(el1 + el2, Style.FONT_TEXT, 40, 46, y, false);

		y -= vwElectricity.getTransform().getHeight() + 15;
		View vwWater = new View(Textures.TEX_WATER, new Transform(10, y, 36, 36));
		String water1 = tile.getType().getWaterConsumption() * (tile.getUpgradeLevel() + 1) * -1 + " L/day ";
		String water2 = "(Now: " + tile.getType().getWaterConsumption() * tile.getUpgradeLevel() * -1 + " L/day)";
		UILabel lblWater = new UILabel(water1 + water2, Style.FONT_TEXT, 40, 46, y, false);

		y -= vwWater.getTransform().getHeight() + 75;
		UILabel lblCost = new UILabel("Upgrade Cost: $" + cost, Style.FONT_TEXT, 42, 10, y, false);

		btnBuy = new UIButton("Upgrade", Style.FONT_BUTTON, 64, Textures.TEX_RED, new Transform(0, 0, WIDTH, 96));
		btnBuy.addButtonListener(this);

		addSubview(lblTitle);
		addSubview(vwElectricity);
		addSubview(lblElectricity);
		addSubview(vwWater);
		addSubview(lblWater);
		addSubview(lblCost);
		addSubview(btnBuy);
		addSubview(btnClose);

		int el = tile.getType().getElectricityConsumption();
		int water = tile.getType().getWaterConsumption();
		elIncrease = el * (tile.getUpgradeLevel() + 1) - el * tile.getUpgradeLevel();
		waterIncrease = water * (tile.getUpgradeLevel() + 1) - water * tile.getUpgradeLevel();
	}

	@Override
	public void update(float delta) {
		if (btnBuy == null)
			return;
		if (!canBuy) {
			if (canBuy()) {
				canBuy = true;
				btnBuy.setTexture(Textures.TEX_GREEN);
			}
		} else {
			if (!canBuy()) {
				canBuy = false;
				btnBuy.setTexture(Textures.TEX_RED);
			}
		}
	}

	private boolean canBuy() {
		return game.getWorld().getMoney() >= cost && game.getWorld().getElectricityProduction() >= elIncrease
				&& game.getWorld().getWaterProduction() >= waterIncrease;
	}

	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnClose) {
			game.closeUpgradeView();
		}

		if (btnBuy == null)
			return;
		if (button == btnBuy) {
			if (!canBuy)
				return;
			tile.upgradeTile();
			game.getWorld().applyUpgrade(tile);
			game.closeUpgradeView();
		}
	}

	@Override
	public void onButtonDown(UIButton button, int mouseButton) {
	}
}
