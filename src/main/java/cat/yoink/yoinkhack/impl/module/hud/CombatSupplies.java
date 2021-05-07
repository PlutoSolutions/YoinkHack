package cat.yoink.yoinkhack.impl.module.hud;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class CombatSupplies extends Module
{
	public CombatSupplies(String name, Category category, String description)
	{
		super(name, category, description);

		Setting render = new Setting("Render", this, "Horizontal", new ArrayList<>(Arrays.asList("Horizontal", "Vertical")));
		Setting background = new Setting("Background", this, true);
		Setting border = new Setting("Border", this, false);

		addSetting(render);
		addSetting(background);
		addSetting(border);
	}
}
