package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ISPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class NoRotate extends Module
{
	public NoRotate(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SubscribeEvent
	public void onPacket(PacketEvent.Receive event)
	{
		if (mc.player == null || mc.world == null) return;

		if (event.getPacket() instanceof SPacketPlayerPosLook)
		{
			ISPacketPlayerPosLook packet = (ISPacketPlayerPosLook) event.getPacket();

			packet.setYaw(mc.player.rotationYaw);
			packet.setPitch(mc.player.rotationPitch);
		}
	}

}
