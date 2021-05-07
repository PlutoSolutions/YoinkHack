package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.MoveEvent;
import cat.yoink.yoinkhack.impl.event.WalkEvent;
import cat.yoink.yoinkhack.mixin.mixins.accessor.IMinecraft;
import cat.yoink.yoinkhack.mixin.mixins.accessor.ITimer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Speed extends Module
{
	private final Setting speed = new Setting("Speed", this, 1, 18, 50);
	private final Setting jump = new Setting("Jump", this, true);
	private final Setting useTimer = new Setting("UseTimer", this, false);
	private final Setting timerSpeed = new Setting("TimerSpeed", this, 0, 11, 20);

	private int stage;
	private double moveSpeed;
	private double lastDist;
	private int cooldownHops;

	public Speed(String name, Category category, String description)
	{
		super(name, category, description);
		addSetting(speed);
		addSetting(jump);
		addSetting(useTimer);
		addSetting(timerSpeed);
	}

	public void onEnable()
	{
		moveSpeed = getBaseMoveSpeed();
	}

	@Override
	public void onDisable()
	{
		moveSpeed = 0.0;
		stage = 2;

		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (useTimer.getBoolValue()) ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f / ((timerSpeed.getIntValue() + 100) / 100f));
		else ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}

	@SubscribeEvent
	public void onUpdateWalkingPlayer(WalkEvent event)
	{
		lastDist = Math.sqrt((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX) + (mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ));
	}

	@SubscribeEvent
	public void onMove(MoveEvent event)
	{
		float moveForward = mc.player.movementInput.moveForward;
		float moveStrafe = mc.player.movementInput.moveStrafe;
		float rotationYaw = mc.player.rotationYaw;
		if (stage == 1 && (mc.player.moveForward != 0.0 || mc.player.moveStrafing != 0.0))
		{
			stage = 2;
			moveSpeed = getMultiplier() * getBaseMoveSpeed() - 0.01;
		}
		else if (stage == 2)
		{
			stage = 3;
			if (mc.player.moveForward != 0.0 || mc.player.moveStrafing != 0.0)
			{
				if (jump.getBoolValue()) event.setY(mc.player.motionY = 0.4);
				if (cooldownHops > 0)
				{
					--cooldownHops;
				}
				moveSpeed *= speed.getIntValue() / 10f;
			}
		}
		else if (stage == 3)
		{
			stage = 4;
			final double difference = 0.66 * (lastDist - getBaseMoveSpeed());
			moveSpeed = lastDist - difference;
		}
		else
		{
			if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, mc.player.motionY, 0.0)).size() > 0 || mc.player.collidedVertically)
			{
				stage = 1;
			}
			moveSpeed = lastDist - lastDist / 159.0;
		}
		moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
		if (moveForward == 0.0f && moveStrafe == 0.0f)
		{
			event.setX(0.0);
			event.setZ(0.0);
			moveSpeed = 0.0;
		}
		else if (moveForward != 0.0f)
		{
			if (moveStrafe >= 1.0f)
			{
				rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
				moveStrafe = 0.0f;
			}
			else if (moveStrafe <= -1.0f)
			{
				rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
				moveStrafe = 0.0f;
			}
			if (moveForward > 0.0f)
			{
				moveForward = 1.0f;
			}
			else if (moveForward < 0.0f)
			{
				moveForward = -1.0f;
			}
		}
		final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
		final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
		if (cooldownHops == 0)
		{
			event.setX(moveForward * moveSpeed * motionX + moveStrafe * moveSpeed * motionZ);
			event.setZ(moveForward * moveSpeed * motionZ - moveStrafe * moveSpeed * motionX);
		}
		if (moveForward == 0.0f && moveStrafe == 0.0f)
		{
			event.setX(0.0);
			event.setZ(0.0);
		}
	}

	private double getBaseMoveSpeed()
	{
		double baseSpeed = 0.272;
		if (mc.player.isPotionActive(MobEffects.SPEED))
		{
			final int amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * amplifier;
		}
		return baseSpeed;
	}

	private float getMultiplier()
	{
		float baseSpeed = 118;
		return baseSpeed / 100.0f;
	}


	public static double round(final double value, final int places)
	{
		if (places < 0)
		{
			throw new IllegalArgumentException();
		}
		final BigDecimal bigDecimal = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}
}