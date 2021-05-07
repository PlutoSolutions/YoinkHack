package cat.yoink.yoinkhack.impl.module.combat;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class AutoLog extends Module
{
	private final Setting health = new Setting("Health", this, 1, 10, 20);

	public AutoLog(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(health);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mc.player.getHealth() <= health.getIntValue())
		{
			disconnect();
			disable();
		}
	}

	private void disconnect()
	{
		mc.world.sendQuittingDisconnectingPacket();
		mc.loadWorld(null);
		mc.displayGuiScreen(new GuiMainMenu());
	}
}
