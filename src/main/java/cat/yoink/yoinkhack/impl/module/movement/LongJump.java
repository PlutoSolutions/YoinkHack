package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.MoveEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 9/6/2020
 */
public class LongJump extends Module
{
	private final Setting speed = new Setting("Speed", this, 1, 50, 100);
	private final Setting packet = new Setting("Packet", this, false);
	private boolean jumped = false;
	private boolean canBoost = false;

	public LongJump(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(speed);
		addSetting(packet);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (jumped)
		{
			if (mc.player.onGround || mc.player.capabilities.isFlying)
			{
				jumped = false;

				mc.player.motionX = 0.0;
				mc.player.motionZ = 0.0;

				if (packet.getBoolValue())
				{
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.onGround));
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, 0.0, mc.player.posZ + mc.player.motionZ, mc.player.onGround));
				}

				return;
			}

			strafe(speed() * (canBoost ? (speed.getIntValue() / 10f) : 1f));
			canBoost = false;
		}
	}

	@SubscribeEvent
	public void onMove(MoveEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (!isMoving() && jumped)
		{
			mc.player.motionX = 0.0;
			mc.player.motionZ = 0.0;
			event.setX(0);
			event.setY(0);
		}
	}

	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event)
	{
		if (event.getEntity() == mc.player)
		{
			if (isMoving())
			{
				jumped = true;
				canBoost = true;
			}
		}
	}

	private boolean isMoving()
	{
		return mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f;
	}

	private double direction()
	{
		float rotationYaw = mc.player.rotationYaw;
		if (mc.player.moveForward < 0f) rotationYaw += 180f;
		float forward = 1f;
		if (mc.player.moveForward < 0f) forward = -0.5f;
		else if (mc.player.moveForward > 0f) forward = 0.5f;
		if (mc.player.moveStrafing > 0f) rotationYaw -= 90f * forward;
		if (mc.player.moveStrafing < 0f) rotationYaw += 90f * forward;
		return Math.toRadians(rotationYaw);
	}

	private void strafe(float speed)
	{
		if (!isMoving()) return;
		double yaw = direction();
		mc.player.motionX = -Math.sin(yaw) * speed;
		mc.player.motionZ = Math.cos(yaw) * speed;
	}

	private float speed()
	{
		return (float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
	}
}
