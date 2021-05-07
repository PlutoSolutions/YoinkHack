package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class NoFall extends Module
{
	private final Setting mode = new Setting("Mode", this, "Packet", new ArrayList<>(Arrays.asList("Packet", "Bypass")));
	private long last = 0;

	public NoFall(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(mode);
	}

	@SubscribeEvent
	public void onPacket(PacketEvent.Send event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Packet") && event.getPacket() instanceof CPacketPlayer)
		{
			((ICPacketPlayer) event.getPacket()).setOnGround(true);
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Bypass") && mc.player.fallDistance >= 5 && System.currentTimeMillis() - last > 100)
		{
			Vec3d posVec = mc.player.getPositionVector();
			RayTraceResult result = mc.world.rayTraceBlocks(posVec, posVec.add(0, -13.33f, 0), true, true, false);
			if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK)
			{
				mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.onGround));
				mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, -1000.0, mc.player.posZ + mc.player.motionZ, mc.player.onGround));
				mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1000, mc.player.posZ, mc.player.onGround));

				last = System.currentTimeMillis();
			}
		}
	}
}