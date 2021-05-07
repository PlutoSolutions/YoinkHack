package cat.yoink.yoinkhack.impl.module.hud;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class ClickGUI extends Module
{
	private final Setting mode = new Setting("Mode", this, "New", new ArrayList<>(Arrays.asList("Old", "Round", "New")));

	public ClickGUI(String name, Category category, boolean drawn, String description)
	{
		super(name, category, drawn, description);

		Setting color = new Setting("Color", this, "Red", new ArrayList<>(Arrays.asList("Red", "Green", "Blue")));
		Setting tooltips = new Setting("ToolTips", this, true);

		addSetting(mode);
		addSetting(color);
		addSetting(tooltips);

		setBind(Keyboard.KEY_RSHIFT);
	}

	@Override
	public void onEnable()
	{
		switch (mode.getEnumValue())
		{
			case "New":
				mc.displayGuiScreen(Client.INSTANCE.clickGUI);
				break;
			case "Round":
				mc.displayGuiScreen(Client.INSTANCE.clickGUINew);
				break;
			case "Old":
				mc.displayGuiScreen(Client.INSTANCE.clickGUIOld);
				break;
			default:
				break;
		}
	}
}
