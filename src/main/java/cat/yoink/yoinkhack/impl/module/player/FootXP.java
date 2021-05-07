package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ICPacketPlayer;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class FootXP extends Module
{
	public FootXP(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SubscribeEvent
	public void onPacket(PacketEvent.Send event)
	{
		if (mc.player == null || mc.world == null) return;

		if (event.getPacket() instanceof CPacketPlayer && mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle)
		{
			CPacketPlayer packet = (CPacketPlayer) event.getPacket();
			((ICPacketPlayer) packet).setPitch(90.0f);
		}
	}
}
