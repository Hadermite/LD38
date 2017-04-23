package se.wiklund.ld38.game;

import se.wiklund.haderengine.View;
import se.wiklund.haderengine.maths.Transform;
import se.wiklund.haderengine.ui.UIButton;
import se.wiklund.haderengine.ui.UILabel;
import se.wiklund.haderengine.ui.listener.UIButtonListener;
import se.wiklund.ld38.Style;
import se.wiklund.ld38.Textures;
import se.wiklund.ld38.game.Tile.TileType;
import se.wiklund.ld38.util.UnitFormatter;

public class ShopItem extends View implements UIButtonListener {
	
	public static final int MARGIN = 64;
	public static final int WIDTH = (Shop.WIDTH - (MARGIN * (Shop.X_COUNT + 1))) / Shop.X_COUNT;
	public static final int HEIGHT = (Shop.HEIGHT - (MARGIN * (Shop.Y_COUNT + 1))) / Shop.Y_COUNT;
	
	private Game game;
	private TileType type;
	private UIButton btnBuy;
	
	public ShopItem(TileType type, int xPos, int yPos, Game game) {
		super(Textures.TEX_WHITE, new Transform(xPos * (WIDTH + MARGIN) + MARGIN, yPos * (HEIGHT + MARGIN) + MARGIN, WIDTH, HEIGHT));
		this.game = game;
		this.type = type;
		
		UILabel lblName = new UILabel(type.getName(), Style.FONT_TITLE, 56, WIDTH / 2, HEIGHT - 56 / 2, true);
		UILabel lblPrice = new UILabel("Price: $" + type.getCost(), Style.FONT_TEXT, 36, 10, HEIGHT - 100, false);
		
		View vwElectricity = new View(Textures.TEX_ELECTRICITY, new Transform(10, HEIGHT - 150, 36, 36));
		UILabel lblElectricity = new UILabel(UnitFormatter.formatEnergy(type.getElectricityConsumption() * -1) + "/day", Style.FONT_TEXT, 40, 46, HEIGHT - 150, false);
		
		View vwWater = new View(Textures.TEX_WATER, new Transform(10, HEIGHT - 200, 36, 36));
		UILabel lblWater = new UILabel(type.getWaterConsumption() * -1 + " L/day", Style.FONT_TEXT, 40, 46, HEIGHT - 200, false);
		
		btnBuy = new UIButton("Buy", Style.FONT_TITLE, 42, Textures.TEX_GREEN, new Transform(0, 0, WIDTH, 96));
		
		btnBuy.addButtonListener(this);
		
		addSubview(lblName);
		addSubview(lblPrice);
		
		addSubview(vwElectricity);
		addSubview(lblElectricity);
		
		addSubview(vwWater);
		addSubview(lblWater);
		
		addSubview(btnBuy);
	}
	
	@Override
	public void onButtonUp(UIButton button, int mouseButton) {
		if (button == btnBuy) {
			game.getWorld().setItemInHand(type);
			game.closeShop();
		}
	}

	@Override
	public void onButtonDown(UIButton button, int mouseButton) {}
}
