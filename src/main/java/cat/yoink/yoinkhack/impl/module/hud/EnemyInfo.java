package cat.yoink.yoinkhack.impl.module.hud;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class EnemyInfo extends Module
{
	public EnemyInfo(String name, Category category, String description)
	{
		super(name, category, description);

		Setting friends = new Setting("Friends", this, false);
		Setting background = new Setting("Background", this, true);
		Setting border = new Setting("Border", this, false);

		addSetting(friends);
		addSetting(background);
		addSetting(border);
	}
}
