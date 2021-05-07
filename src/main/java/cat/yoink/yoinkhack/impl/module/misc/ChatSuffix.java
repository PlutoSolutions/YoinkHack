package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class ChatSuffix extends Module
{

	private final Setting blue = new Setting("Blue", this, false);

	private final ArrayList<String> prefixes = new ArrayList<>(Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\"));

	public ChatSuffix(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(blue);
	}


	@SubscribeEvent
	public void onMessage(ClientChatEvent event)
	{
		if (mc.player == null || mc.world == null || event.getMessage().startsWith(Client.INSTANCE.commandManager.getPrefix()))
		{
			return;
		}

		for (String prefix : prefixes)
		{
			if (event.getMessage().startsWith(prefix)) return;
		}

		String msg;

		if (blue.getBoolValue())
		{
			msg = String.format("%s `\uFF5C \u028F\u1D0F\u026A\u0274\u1D0B\u029C\u1D00\u1D04\u1D0B", event.getMessage());
		}
		else
		{
			msg = String.format("%s \uFF5C \u028F\u1D0F\u026A\u0274\u1D0B\u029C\u1D00\u1D04\u1D0B", event.getMessage());
		}

		event.setMessage(msg);

	}
}
