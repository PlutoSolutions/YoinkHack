package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class RotationLock extends Module
{
	private final Setting sYaw = new Setting("Yaw", this, true);
	private final Setting sPitch = new Setting("Pitch", this, false);
	private final Setting mode = new Setting("Mode", this, 0, 4, 30);

	public RotationLock(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(sYaw);
		addSetting(sPitch);
		addSetting(mode);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null || mode.getIntValue() == 0) return;

		int angle = 360 / mode.getIntValue();

		float yaw = mc.player.rotationYaw;
		float pitch = mc.player.rotationPitch;

		yaw = Math.round(yaw / angle) * angle;
		pitch = Math.round(pitch / angle) * angle;

		if (sYaw.getBoolValue()) mc.player.rotationYaw = yaw;
		if (sPitch.getBoolValue()) mc.player.rotationPitch = pitch;

		if (mc.player.isRiding()) Objects.requireNonNull(mc.player.getRidingEntity()).rotationYaw = yaw;
	}
}
