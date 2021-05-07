package cat.yoink.yoinkhack.api.util;

import cat.yoink.yoinkhack.impl.event.PacketEvent;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class TPSUtil
{
	public static TPSUtil INSTANCE;

	private final float[] tickRates = new float[20];
	private int nextIndex = 0;
	private long timeLastTimeUpdate;

	public TPSUtil()
	{
		MinecraftForge.EVENT_BUS.register(this);
		reset();
	}

	@SubscribeEvent
	public void packetEvent(PacketEvent.Send event)
	{
		if (event.getPacket() instanceof SPacketTimeUpdate)
		{
			INSTANCE.onTimeUpdate();
		}
	}

	public void reset()
	{
		this.nextIndex = 0;
		this.timeLastTimeUpdate = -1L;
		Arrays.fill(this.tickRates, 0.0F);
	}

	public float getTickRate()
	{
		float numTicks = 0.0F;
		float sumTickRates = 0.0F;
		for (float tickRate : this.tickRates)
		{
			if (tickRate > 0.0F)
			{
				sumTickRates += tickRate;
				numTicks += 1.0F;
			}
		}
		return MathHelper.clamp(sumTickRates / numTicks, 0.0F, 20.0F);
	}

	public void onTimeUpdate()
	{
		if (this.timeLastTimeUpdate != -1L)
		{
			float timeElapsed = (float) (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0F;
			this.tickRates[(this.nextIndex % this.tickRates.length)] = MathHelper.clamp(20.0F / timeElapsed, 0.0F, 20.0F);
			this.nextIndex += 1;
		}
		this.timeLastTimeUpdate = System.currentTimeMillis();
	}
}