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
public class ChatModifier extends Module
{
	private final Setting blue = new Setting("Blue", this, false);
	private final Setting green = new Setting("Green", this, false);
	private final Setting blueWhite = new Setting("Blue-White", this, false);
	private final Setting wheelchair = new Setting("Wheelchair", this, true);

	private final ArrayList<String> prefixes = new ArrayList<>(Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\"));

	public ChatModifier(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(blue);
		addSetting(green);
		addSetting(blueWhite);
		addSetting(wheelchair);
	}


	@SubscribeEvent
	public void onChatSend(ClientChatEvent event)
	{
		if (mc.player == null || mc.world == null || event.getMessage().startsWith(Client.INSTANCE.commandManager.getPrefix()))
		{
			return;
		}

		for (String prefix : prefixes)
		{
			if (event.getMessage().startsWith(prefix)) return;
		}


		String msg = event.getMessage();

		if (wheelchair.getBoolValue())
		{
			msg = String.format("\u267F%s\u267F", msg);
		}

		if (blue.getBoolValue())
		{
			msg = String.format("`%s", msg);
		}
		if (green.getBoolValue())
		{
			msg = String.format("> %s", msg);
		}
		if (blueWhite.getBoolValue())
		{
			StringBuilder a = new StringBuilder();
			boolean b = true;
			String[] split = msg.split("");
			for (String spl : split)
			{
				if (b)
				{
					a.append("`").append(spl);
					b = false;
				}
				else
				{
					a.append("~").append(spl);
					b = true;
				}
			}
			msg = a.toString();
		}
		event.setMessage(msg);
	}
}
