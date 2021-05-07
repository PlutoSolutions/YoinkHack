package cat.yoink.yoinkhack.impl.module.combat;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Reach extends Module
{
	public Reach(String name, Category category, String description)
	{
		super(name, category, description);

		Setting distance = new Setting("Distance", this, 3, 5, 10);

		addSetting(distance);
	}
}
