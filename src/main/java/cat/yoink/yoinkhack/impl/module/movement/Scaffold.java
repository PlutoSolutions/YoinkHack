package cat.yoink.yoinkhack.impl.module.movement;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.util.PlaceUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Scaffold extends Module
{
	public Scaffold(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);

		if (PlaceUtil.canPlaceBlock(pos))
		{
			PlaceUtil.placeBlock(pos, getBlockSlot(), true, true);
		}
	}

	private int getBlockSlot()
	{
		int slot = 0;

		for (int i = 0; i < 9; i++)
		{
			ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack.getItem() instanceof ItemBlock)
			{
				return slot;
			}
		}
		return slot;
	}
}
