package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class DiscordRPC extends Module
{
	public DiscordRPC(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@Override
	public void onEnable()
	{
		Client.INSTANCE.rpc.setDetails("Version 3.4-beta2");
		Client.INSTANCE.rpc.setState("yoinkhack best hack");
	}

	@Override
	public void onDisable()
	{
		enable();
	}
}