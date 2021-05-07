package cat.yoink.yoinkhack.impl.module.hud;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Watermark extends Module
{
	public Watermark(String name, Category category, String description)
	{
		super(name, category, description);

		Setting red = new Setting("Red", this, 0, 140, 255);
		Setting green = new Setting("Green", this, 0, 0, 255);
		Setting blue = new Setting("Blue", this, 0, 0, 255);
		Setting rainbow = new Setting("Rainbow", this, false);

		addSetting(red);
		addSetting(green);
		addSetting(blue);
		addSetting(rainbow);
	}
}
