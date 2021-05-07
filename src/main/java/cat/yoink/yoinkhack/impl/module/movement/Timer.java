package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.mixin.mixins.accessor.IMinecraft;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ITimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Timer extends Module
{
	public final Setting speed = new Setting("Speed", this, 1, 10, 100);

	public Timer(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(speed);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f / (speed.getIntValue() / 10f));
	}

	@Override
	public void onDisable()
	{
		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}
}
