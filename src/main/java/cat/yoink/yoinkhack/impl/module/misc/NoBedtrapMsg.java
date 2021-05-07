package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class NoBedtrapMsg extends Module
{

	public NoBedtrapMsg(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (event.getMessage().getUnformattedComponentText().contains("thought they were jj20051") || event.getMessage().getUnformattedComponentText().contains("burned to death") || event.getMessage().getUnformattedComponentText().contains("went up in flames") || event.getMessage().getUnformattedComponentText().contains("swim in lava"))
		{
			event.setCanceled(true);
		}
	}
}
