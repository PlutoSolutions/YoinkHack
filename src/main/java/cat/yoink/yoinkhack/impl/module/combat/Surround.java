package cat.yoink.yoinkhack.impl.module.combat;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.PlaceUtil;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Surround extends Module
{
	private final Setting speed = new Setting("Speed", this, 1, 3, 30);
	private final Setting antiFacePlace = new Setting("AntiFacePlace", this, false);
	private final Setting center = new Setting("Center", this, false);
	private final Setting disable = new Setting("Disable", this, true);
	private final Setting jumpDisable = new Setting("JumpDisable", this, false);

	private final ArrayList<BlockPos> renderBlocks = new ArrayList<>();
	private int ticksOn;
	private Vec3d playerPos;

	public Surround(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(speed);
		addSetting(antiFacePlace);
		addSetting(center);
		addSetting(disable);
		addSetting(jumpDisable);
	}

	@Override
	public void onEnable()
	{
		ticksOn = 0;

		BlockPos centerPos = mc.player.getPosition();
		playerPos = mc.player.getPositionVector();
		double y = centerPos.getY();
		double x = centerPos.getX();
		double z = centerPos.getZ();

		final Vec3d plusPlus = new Vec3d(x + 0.5, y, z + 0.5);
		final Vec3d plusMinus = new Vec3d(x + 0.5, y, z - 0.5);
		final Vec3d minusMinus = new Vec3d(x - 0.5, y, z - 0.5);
		final Vec3d minusPlus = new Vec3d(x - 0.5, y, z + 0.5);

		if (center.getBoolValue())
		{
			if (getDst(plusPlus) < getDst(plusMinus) && getDst(plusPlus) < getDst(minusMinus) && getDst(plusPlus) < getDst(minusPlus))
			{
				x = centerPos.getX() + 0.5;
				z = centerPos.getZ() + 0.5;
				center(x, y, z);
			}
			if (getDst(plusMinus) < getDst(plusPlus) && getDst(plusMinus) < getDst(minusMinus) && getDst(plusMinus) < getDst(minusPlus))
			{
				x = centerPos.getX() + 0.5;
				z = centerPos.getZ() - 0.5;
				center(x, y, z);
			}
			if (getDst(minusMinus) < getDst(plusPlus) && getDst(minusMinus) < getDst(plusMinus) && getDst(minusMinus) < getDst(minusPlus))
			{
				x = centerPos.getX() - 0.5;
				z = centerPos.getZ() - 0.5;
				center(x, y, z);
			}
			if (getDst(minusPlus) < getDst(plusPlus) && getDst(minusPlus) < getDst(plusMinus) && getDst(minusPlus) < getDst(minusMinus))
			{
				x = centerPos.getX() - 0.5;
				z = centerPos.getZ() + 0.5;
				center(x, y, z);
			}
		}
	}

	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event)
	{
		if (event.getEntity().equals(mc.player) && jumpDisable.getBoolValue())
		{
			disable();
		}
	}


	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		List<BlockPos> blocks = new ArrayList<>(Arrays.asList(
				(new BlockPos(mc.player.getPositionVector())).add(0, 0, 1),
				(new BlockPos(mc.player.getPositionVector())).add(1, 0, 0),
				(new BlockPos(mc.player.getPositionVector())).add(0, 0, -1),
				(new BlockPos(mc.player.getPositionVector())).add(-1, 0, 0)
		));

		renderBlocks.clear();

		for (Object bP : new ArrayList<>(blocks))
		{
			BlockPos block = (BlockPos) bP;

			blocks.add(0, block.down());

			if (antiFacePlace.getBoolValue()) blocks.add(block.up());

			if (mc.world.getBlockState(block).getBlock().equals(Blocks.AIR)) renderBlocks.add(block);

		}


		int slot = getObsidianSlot();

		if (slot != -1)
		{

			if (disable.getBoolValue()) ticksOn++;

			int i = 0;

			int hand = mc.player.inventory.currentItem;

			for (BlockPos blockPos : blocks)
			{

				if (PlaceUtil.placeBlock(blockPos, slot, true, false))
				{
					i++;
				}

				int BPT = Math.round(speed.getIntValue() / 10f) + 1;

				if (i >= BPT)
				{
					break;
				}

			}

			mc.player.inventory.currentItem = hand;

			if (ticksOn > 8)
			{

				if (disable.getBoolValue()) disable();
				renderBlocks.clear();

			}

		}
		else
		{

			if (disable.getBoolValue()) disable();
			renderBlocks.clear();

		}

	}

	public int getObsidianSlot()
	{

		int slot = -1;

		for (int i = 0; i < 9; i++)
		{

			ItemStack stack = mc.player.inventory.getStackInSlot(i);

			if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock))
			{
				continue;
			}

			Block block = ((ItemBlock) stack.getItem()).getBlock();

			if (block instanceof BlockObsidian)
			{

				slot = i;
				break;

			}

		}

		return slot;

	}


	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		for (BlockPos renderBlock : renderBlocks)
		{
			RenderUtil.drawBoxFromBlockpos(renderBlock, 0.50f, 0.00f, 0.00f, 0.30f);
		}
	}

	private void center(double x, double y, double z)
	{
		mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y, z, true));
		mc.player.setPosition(x, y, z);
	}

	private double getDst(Vec3d vec)
	{
		return playerPos.distanceTo(vec);
	}
}
