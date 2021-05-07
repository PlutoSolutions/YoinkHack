package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.PlaceUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class EChestPlace extends Module
{
	private final Setting smart = new Setting("Smart", this, false);
	private int ticksOn;

	public EChestPlace(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(smart);
	}

	@Override
	public void onEnable()
	{
		mc.player.setSneaking(true);
		ticksOn = 0;
	}

	@Override
	public void onDisable()
	{
		mc.player.setSneaking(false);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		List<BlockPos> blocks = new ArrayList<>(Arrays.asList(
				(new BlockPos(mc.player.getPositionVector())).add(0, 0, 1),
				(new BlockPos(mc.player.getPositionVector())).add(1, 0, 0),
				(new BlockPos(mc.player.getPositionVector())).add(0, 0, -1),
				(new BlockPos(mc.player.getPositionVector())).add(1, 0, 1),
				(new BlockPos(mc.player.getPositionVector())).add(-1, 0, -1),
				(new BlockPos(mc.player.getPositionVector())).add(-1, 0, 1),
				(new BlockPos(mc.player.getPositionVector())).add(1, 0, -1),
				(new BlockPos(mc.player.getPositionVector())).add(-1, 1, 0),
				(new BlockPos(mc.player.getPositionVector())).add(1, 1, 0),
				(new BlockPos(mc.player.getPositionVector())).add(0, 1, -1),
				(new BlockPos(mc.player.getPositionVector())).add(0, 1, 1)
		));

		for (Object bP : new ArrayList<>(blocks))
		{
			BlockPos block = (BlockPos) bP;
			blocks.add(0, block.down());
		}
		int slot = getSlot();

		if (slot != -1)
		{
			ticksOn++;
			int i = 0;
			int hand = mc.player.inventory.currentItem;

			for (BlockPos blockPos : blocks)
			{

				if (PlaceUtil.placeBlock(blockPos, slot, true, false))
				{
					i++;
				}
				if (i >= 1)
				{
					break;
				}
			}
			mc.player.inventory.currentItem = hand;
			if (smart.getBoolValue())
			{
				if (ticksOn > Math.round(getObsidianNeededCount() / 8f)) disable();
			}
			else
			{
				if (ticksOn > 10) disable();
			}
		}
		else
		{
			disable();
		}

	}

	public int getSlot()
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

			if (block instanceof BlockEnderChest)
			{
				slot = i;
				break;
			}
		}
		return slot;
	}


	private int getObsidianNeededCount()
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

		if (slot == -1) return 64;
		return 64 - mc.player.inventory.mainInventory.get(slot).getCount();
	}
}
