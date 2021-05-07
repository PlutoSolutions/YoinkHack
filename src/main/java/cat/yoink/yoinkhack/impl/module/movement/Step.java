package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.MoveEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Step extends Module
{
	private final Setting mode = new Setting("Mode", this, "Vanilla", new ArrayList<>(Arrays.asList("Vanilla", "Packet")));
	private final Setting height = new Setting("Height", this, 1, 2, 2);

	private final double[] oneblockPositions = {0.42D, 0.75D};
	private final double[] twoblockPositions = {0.4D, 0.75D, 0.5D, 0.41D, 0.83D, 1.16D, 1.41D, 1.57D, 1.58D, 1.42D};
	private int packets;

	public Step(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(mode);
		addSetting(height);
	}

	@SubscribeEvent
	public void onMove(MoveEvent event)
	{
		if (mc.player == null || mc.world == null || mc.player.isDead) return;

		mc.player.stepHeight = 0.5f;
		if (mode.getEnumValue().equals("Packet"))
		{
			packet();
		}
		else
		{
			if (mc.player.collidedHorizontally && mc.player.onGround)
			{
				mc.player.stepHeight = 0.5f;
			}
			else
			{
				mc.player.stepHeight = height.getIntValue();
			}
		}
	}

	private void packet()
	{

		double[] selectedPositions;
		if (mode.getIntValue() == 1)
		{
			selectedPositions = oneblockPositions;
		}
		else
		{
			selectedPositions = twoblockPositions;
		}

		if (mc.player.collidedHorizontally && mc.player.onGround)
		{
			this.packets++;
		}

		final AxisAlignedBB bb = mc.player.getEntityBoundingBox();

		for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX + 1.0D); x++)
		{
			for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ + 1.0D); z++)
			{
				final Block block = mc.world.getBlockState(new BlockPos(x, bb.maxY + 1, z)).getBlock();
				if (!(block instanceof BlockAir))
				{
					return;
				}
			}
		}

		if (mc.player.onGround && !mc.player.isInsideOfMaterial(Material.WATER) && !mc.player.isInsideOfMaterial(Material.LAVA) && mc.player.collidedVertically && mc.player.fallDistance == 0 && !mc.gameSettings.keyBindJump.isPressed() && mc.player.collidedHorizontally && !mc.player.isOnLadder() && this.packets > selectedPositions.length - 2)
		{
			for (double position : selectedPositions)
			{
				mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + position, mc.player.posZ, true));
			}
			mc.player.setPosition(mc.player.posX, mc.player.posY + selectedPositions[selectedPositions.length - 1], mc.player.posZ);
			this.packets = 0;
		}
	}

	@Override
	public void onDisable()
	{
		mc.player.stepHeight = (float) 0.5;
	}
}