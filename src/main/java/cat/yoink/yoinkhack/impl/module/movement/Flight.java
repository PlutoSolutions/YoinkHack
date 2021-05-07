package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import cat.yoink.yoinkhack.impl.event.WalkEvent;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Flight extends Module
{
	private final Setting mode = new Setting("Mode", this, "Vanilla", new ArrayList<>(Arrays.asList("Vanilla", "Packet")));
	private final Setting speed = new Setting("Speed", this, 0, 5, 50);
	private final Setting antiKick = new Setting("AntiKick", this, true);
	private final List<CPacketPlayer> packets = new ArrayList<>();
	private int teleportId;

	public Flight(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(mode);
		addSetting(speed);
		addSetting(antiKick);
	}

	@Override
	public void onEnable()
	{
		if (mode.getEnumValue().equals("Packet"))
		{
			teleportId = 0;
			packets.clear();
			CPacketPlayer bounds = new CPacketPlayer.Position(mc.player.posX, 0, mc.player.posZ, mc.player.onGround);
			packets.add(bounds);
			mc.player.connection.sendPacket(bounds);

		}
	}

	@SubscribeEvent
	public void onWalkingUpdate(WalkEvent event)
	{
		if (mc.player == null || mc.world == null || mc.player.isDead) return;

		if (mode.getEnumValue().equals("Vanilla"))
		{
			mc.player.setVelocity(0, 0, 0);
			mc.player.jumpMovementFactor = speed.getIntValue() / 10f;

			if (antiKick.getBoolValue() && mc.player.ticksExisted % 4 == 0) mc.player.motionY = -0.04f;
			double[] dir = directionSpeed(speed.getIntValue() / 10f);
			if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0)
			{
				mc.player.motionX = dir[0];
				mc.player.motionZ = dir[1];
			}
			else
			{
				mc.player.motionX = 0;
				mc.player.motionZ = 0;
			}

			if (mc.gameSettings.keyBindJump.isKeyDown())
			{
				if (antiKick.getBoolValue())
				{
					mc.player.motionY = mc.player.ticksExisted % 20 == 0 ? -0.04f : speed.getIntValue() / 10f;
				}
				else
				{
					mc.player.motionY += speed.getIntValue() / 10f;
				}
			}

			if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.motionY -= speed.getIntValue() / 10f;
		}

		if (mode.getEnumValue().equals("Packet"))
		{
			if (teleportId <= 0)
			{
				CPacketPlayer bounds = new CPacketPlayer.Position(mc.player.posX, 0, mc.player.posZ, mc.player.onGround);
				packets.add(bounds);
				mc.player.connection.sendPacket(bounds);
				return;
			}

			mc.player.setVelocity(0, 0, 0);

			if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().expand(-0.0625d, 0, -0.0625d)).isEmpty())
			{
				double ySpeed;

				if (mc.gameSettings.keyBindJump.isKeyDown())
				{
					if (antiKick.getBoolValue())
					{
						ySpeed = mc.player.ticksExisted % 20 == 0 ? -0.04f : 0.062f;
					}
					else
					{
						ySpeed = 0.062f;
					}
				}
				else if (mc.gameSettings.keyBindSneak.isKeyDown())
				{
					ySpeed = -0.062d;
				}
				else
				{
					ySpeed = mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().expand(-0.0625d, -0.0625d, -0.0625d)).isEmpty() ? (mc.player.ticksExisted % 4 == 0) ? (antiKick.getBoolValue() ? -0.04f : 0.0f) : 0.0f : 0.0f;
				}

				double[] directionalSpeed = directionSpeed(speed.getIntValue() / 10f);

				if (mc.gameSettings.keyBindJump.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown() || mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown())
				{
					if (directionalSpeed[0] != 0.0d || ySpeed != 0.0d || directionalSpeed[1] != 0.0d)
					{
						if (mc.player.movementInput.jump && (mc.player.moveStrafing != 0 || mc.player.moveForward != 0))
						{
							mc.player.setVelocity(0, 0, 0);
							move(0, 0, 0);
							for (int i = 0; i <= 3; i++)
							{
								mc.player.setVelocity(0, ySpeed * i, 0);
								move(0, ySpeed * i, 0);
							}
						}
						else
						{
							if (mc.player.movementInput.jump)
							{
								mc.player.setVelocity(0, 0, 0);
								move(0, 0, 0);
								for (int i = 0; i <= 3; i++)
								{
									mc.player.setVelocity(0, ySpeed * i, 0);
									move(0, ySpeed * i, 0);
								}
							}
							else
							{
								for (int i = 0; i <= 2; i++)
								{
									mc.player.setVelocity(directionalSpeed[0] * i, ySpeed * i, directionalSpeed[1] * i);
									move(directionalSpeed[0] * i, ySpeed * i, directionalSpeed[1] * i);
								}
							}
						}
					}
				}
				else
				{
					if (antiKick.getBoolValue() && mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().expand(-0.0625d, -0.0625d, -0.0625d)).isEmpty())
					{
						mc.player.setVelocity(0, (mc.player.ticksExisted % 2 == 0) ? 0.04f : -0.04f, 0);
						move(0, (mc.player.ticksExisted % 2 == 0) ? 0.04f : -0.04f, 0);
					}
				}
			}
		}

	}

	private void move(double x, double y, double z)
	{
		CPacketPlayer pos = new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z, mc.player.onGround);
		packets.add(pos);
		mc.player.connection.sendPacket(pos);

		CPacketPlayer bounds = new CPacketPlayer.Position(mc.player.posX + x, 0, mc.player.posZ + z, mc.player.onGround);
		packets.add(bounds);
		mc.player.connection.sendPacket(bounds);

		teleportId++;
		mc.player.connection.sendPacket(new CPacketConfirmTeleport(teleportId - 1));
		mc.player.connection.sendPacket(new CPacketConfirmTeleport(teleportId));
		mc.player.connection.sendPacket(new CPacketConfirmTeleport(teleportId + 1));
	}

	@SubscribeEvent
	public void sendPacket(PacketEvent.Send event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Packet"))
		{
			if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Position))
			{
				event.setCanceled(true);
				if (event.getPacket() instanceof CPacketPlayer)
				{
					CPacketPlayer packet = (CPacketPlayer) event.getPacket();
					if (packets.contains(packet))
					{
						packets.remove(packet);
						return;
					}
					event.setCanceled(true);
				}
			}
			else
			{
				if (event.getPacket() instanceof CPacketPlayer)
				{
					CPacketPlayer packet = (CPacketPlayer) event.getPacket();
					if (packets.contains(packet))
					{
						packets.remove(packet);
						return;
					}
					event.setCanceled(true);
				}
			}
		}

	}

	@SubscribeEvent
	public void receivePacket(PacketEvent.Receive event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Packet") && event.getPacket() instanceof SPacketPlayerPosLook)
		{
			SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
			if (mc.player.isEntityAlive() && mc.world.isBlockLoaded(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)) && !(mc.currentScreen instanceof GuiDownloadTerrain))
			{
				if (teleportId <= 0)
				{
					teleportId = packet.getTeleportId();
				}
				else
				{
					event.setCanceled(true);
				}
			}
		}
	}


	private double[] directionSpeed(double speed)
	{
		float forward = mc.player.movementInput.moveForward;
		float side = mc.player.movementInput.moveStrafe;
		float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

		if (forward != 0)
		{
			if (side > 0)
			{
				yaw += (forward > 0 ? -45 : 45);
			}
			else if (side < 0) yaw += (forward > 0 ? 45 : -45);

			side = 0;

			if (forward > 0)
			{
				forward = 1;
			}
			else if (forward < 0) forward = -1;
		}

		double sin = Math.sin(Math.toRadians(yaw + 90));
		double cos = Math.cos(Math.toRadians(yaw + 90));
		double posX = (forward * speed * cos + side * speed * sin);
		double posZ = (forward * speed * sin - side * speed * cos);
		return new double[]{posX, posZ};
	}

	@Override
	public void onDisable()
	{
		mc.player.motionX = 0;
		mc.player.motionY = 0;
		mc.player.motionZ = 0;
	}
}
