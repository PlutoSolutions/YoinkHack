package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ISPacketEntityVelocity;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ISPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Velocity extends Module
{

	private final Setting horizontal = new Setting("Horizontal", this, 0, 0, 100);
	private final Setting vertical = new Setting("Vertical", this, 0, 0, 100);

	public Velocity(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(horizontal);
		addSetting(vertical);
	}

	@SubscribeEvent
	public void onPacket(PacketEvent.Receive event)
	{
		if (mc.player == null || mc.world == null) return;

		if (event.getPacket() instanceof SPacketEntityVelocity)
		{
			ISPacketEntityVelocity velocity = ((ISPacketEntityVelocity) event.getPacket());
			if (velocity.getEntityID() == mc.player.getEntityId())
			{
				velocity.setMotionX(velocity.getMotionX() * (horizontal.getIntValue() / 1000));
				velocity.setMotionY(velocity.getMotionY() * (vertical.getIntValue() / 1000));
				velocity.setMotionZ(velocity.getMotionZ() * (horizontal.getIntValue() / 1000));
			}
		}
		else if (event.getPacket() instanceof SPacketExplosion)
		{
			ISPacketExplosion explosion = ((ISPacketExplosion) event.getPacket());

			explosion.setMotionX(explosion.getMotionX() * (horizontal.getIntValue() / 1000f));
			explosion.setMotionY(explosion.getMotionY() * (vertical.getIntValue() / 1000f));
			explosion.setMotionZ(explosion.getMotionZ() * (horizontal.getIntValue() / 1000f));
		}

	}

}
