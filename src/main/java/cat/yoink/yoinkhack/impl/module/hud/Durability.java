package cat.yoink.yoinkhack.impl.module.hud;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Durability extends Module
{
	public Durability(String name, Category category, String description)
	{
		super(name, category, description);

		Setting background = new Setting("Background", this, true);
		Setting border = new Setting("Border", this, false);

		addSetting(background);
		addSetting(border);
	}
}
