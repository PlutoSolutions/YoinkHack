package cat.yoink.yoinkhack.impl.module.hud;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class HUDEditor extends Module
{
	public HUDEditor(String name, Category category, boolean drawn, String description)
	{
		super(name, category, drawn, description);
	}

	@Override
	public void onEnable()
	{
		mc.displayGuiScreen(Client.INSTANCE.hudEditor);
	}
}
