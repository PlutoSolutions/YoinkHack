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
public class Arraylist extends Module
{
	public Arraylist(String name, Category category, boolean drawn, String description)
	{
		super(name, category, drawn, description);

		Setting mode = new Setting("Mode", this, "Box", new ArrayList<>(Arrays.asList("Box", "Rainbow", "New")));
		Setting place = new Setting("Place", this, "Top-left", new ArrayList<>(Arrays.asList("Top-Left", "Top-Right", "Bot-Left", "Bot-Right")));
// TODO: 9/14/2020 add the setting
		addSetting(mode);
		addSetting(place);
	}
}
