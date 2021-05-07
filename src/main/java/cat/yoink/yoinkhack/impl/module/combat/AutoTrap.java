package cat.yoink.yoinkhack.impl.module.combat;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.PlaceUtil;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class AutoTrap extends Module
{
	private final Setting disable = new Setting("Disable", this, true);
	private final Setting range = new Setting("Range", this, 0, 5, 10);
	private final Setting speed = new Setting("Speed", this, 1, 3, 30);
	private final ArrayList<BlockPos> renderBlocks = new ArrayList<>();
	private int ticksOn;

	public AutoTrap(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(disable);
		addSetting(speed);
		addSetting(range);
	}

	@Override
	public void onEnable()
	{
		ticksOn = 0;
	}


	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		EntityPlayer closestPlayer = getClosestPlayer();

		if (closestPlayer == null)
		{
			if (disable.getBoolValue()) disable();
			renderBlocks.clear();
			return;
		}

		List<BlockPos> poses = new ArrayList<>(Arrays.asList(
				(new BlockPos(closestPlayer.getPositionVector())).add(0, -1, -1),
				(new BlockPos(closestPlayer.getPositionVector())).add(1, -1, 0),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, -1, 1),
				(new BlockPos(closestPlayer.getPositionVector())).add(-1, -1, 0),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 0, -1),
				(new BlockPos(closestPlayer.getPositionVector())).add(1, 0, 0),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 0, 1),
				(new BlockPos(closestPlayer.getPositionVector())).add(-1, 0, 0),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 1, -1),
				(new BlockPos(closestPlayer.getPositionVector())).add(1, 1, 0),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 1, 1),
				(new BlockPos(closestPlayer.getPositionVector())).add(-1, 1, 0),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 2, -1),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 2, 1),
				(new BlockPos(closestPlayer.getPositionVector())).add(0, 2, 0)
		));


		renderBlocks.clear();

		for (Object object : new ArrayList<>(poses))
		{
			BlockPos blockPos = (BlockPos) object;
			poses.add(0, blockPos.down());

			if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) renderBlocks.add(blockPos);
		}


		int slot = getObsidianSlot();

		if (slot != -1)
		{
			if (disable.getBoolValue()) ticksOn++;
			int i = 0;
			int hand = mc.player.inventory.currentItem;
			for (BlockPos blockPos : poses)
			{
				if (PlaceUtil.placeBlock(blockPos, slot, true, false)) i++;

				int BPT = Math.round(speed.getIntValue() / 10f) + 1;
				if (i >= BPT) break;

			}

			mc.player.inventory.currentItem = hand;

			if (ticksOn > 30)
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

	public EntityPlayer getClosestPlayer()
	{

		if (mc.world.getLoadedEntityList().size() == 0) return null;

		float r = 100000;
		EntityPlayer closestPlayer = null;

		for (Entity entity : mc.world.getLoadedEntityList())
		{
			if (entity instanceof EntityPlayer && entity != mc.player)
			{
				float distance = mc.player.getDistance(entity);

				if (distance < r)
				{
					r = distance;
					closestPlayer = (EntityPlayer) entity;
				}
			}
		}

		if (r > range.getIntValue()) return null;

		return closestPlayer;

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

		for (BlockPos blockPos : renderBlocks)
		{
			RenderUtil.drawBoxFromBlockpos(blockPos, 0.30f, 0.30f, 0.30f, 0.30f);
		}
	}
}
