package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Derp extends Module
{

	private final Random random = new Random();

	public Derp(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mc.player.ticksExisted % 7 == 0)
		{
			float yaw = mc.player.cameraYaw + random.nextFloat() * 360f - 180f;
			float pitch = random.nextFloat() * 180f - 90f;

			mc.player.connection.sendPacket(new CPacketPlayer.Rotation(yaw, pitch, mc.player.onGround));
		}
	}
}
